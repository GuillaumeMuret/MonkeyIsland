/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication;

import com.ddpm.project.monkeyisland.communication.socket.AbstractSocket;
import com.ddpm.project.monkeyisland.communication.protocole.ProtocolVocabulary;
import com.ddpm.project.monkeyisland.communication.proxy.ProxyClient;
import com.ddpm.project.monkeyisland.communication.socket.SocketMessage;
import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.utils.CommunicationUtils;
import com.ddpm.project.monkeyisland.utils.LogUtils;
import java.io.IOException;
import java.util.HashMap;

public final class Communication implements ISocketState {

    /**
     * The postmanServer who send the messages for the client
     */
    private PostmanServer postmanServer;

    /**
     * The Distributor of the different methode call after a reception
     */
    private Distributor distributor;

    /**
     * Proxy of the Brain (sendMessage the methode to json format)
     */
    private ProxyClient proxyClient;

    /**
     * Map of the read thread : this map contain the KEY = the socket port and the VALUE = the socket
     */
    private HashMap<Integer, ReadThread> mapReadThread;
    
    /**
     * The brain of the system
     */
    private Brain brain;
    
    /**
     * Singleton management
     */
    private static Communication instance;

    /**
     * Builder of the communication
     * @param brain : the brain
     */
    private Communication(Brain brain) {
        this.brain = brain;

        // Map of the read threads
        this.mapReadThread = new HashMap<>();
    
        //The distributor
        this.distributor = Distributor.getInstance(brain);

        //The postmanServer
        this.postmanServer = PostmanServer.getInstance();
        this.postmanServer.addStateSocketObservator(this);

        // the client proxy
        this.proxyClient = ProxyClient.getInstance(this.postmanServer, brain);
    }
    
    /**
     * Getter of the instance Communication
     * @param brain : the brain
     * @return the instance Communication
     */
    public static Communication getInstance (Brain brain) {
        if (instance == null) {
            instance = new Communication(brain);
        }
        return instance;
    }

    /**
     * Process which ask to the postmanServer to read a message
     * @param port : the port of the socket where the message has to be read
     * @return : the socket message
     * @throws IOException : the input output exception
     */
    public SocketMessage readComMessage(int port) throws IOException {
        if (this.getPostmanServer().getStateSocket(port) != null
                && this.getPostmanServer().getStateSocket(port) == AbstractSocket.StateSocket.CONNECTED) {
            return postmanServer.readMessage(port);
        }
        return null;
    }

    /**
     * Getter of the postmanServer
     *
     * @return the postmanServer
     */
    public PostmanServer getPostmanServer() {
        return this.postmanServer;
    }

    /**
     * Process called by the postman when a new socket is connected
     * @param port : the socket port
     */
    @Override
    public void onSocketConnected(int port) {
        this.brain.notifyNewConnection();
        this.mapReadThread.put(port, new ReadThread(port));
        this.mapReadThread.get(port).start();
    }

    /**
     * Reading thread class called to read a message
     */
    private class ReadThread extends Thread {

        /**
         * The received message from the socket
         */
        private SocketMessage receivedMessage;

        /**
         * The port used
         */
        private int port;

        /**
         * Main constructor of the read thread
         * @param port : the port where the postman read
         */
        public ReadThread(int port) {
            this.port = port;
        }

        /**
         * Called to read a message and send this message to the binder man's letter box
         *
         * @throws IOException          : The Input Output exception
         * @throws NullPointerException : The null pointer exception
         */
        private void manageReading() throws IOException, NullPointerException {
            // loop which read the buffer and block the task when nothing is received
            while ((receivedMessage = readComMessage(this.port)) == null) {
                LogUtils.useless();
            }
            LogUtils.debug("MESSAGE RECU > " + receivedMessage.getMessage());

            // Send message to the dispatcher to be interpreted
            if (receivedMessage.getMessage().length() >= ProtocolVocabulary.COMMAND_STRING_SIZE) {
                try {
                    distributor.dispatch(
                            CommunicationUtils.getProcessFromMessage(receivedMessage.getMessage()),
                            CommunicationUtils.getParamsFromMesage(receivedMessage.getMessage()),
                            receivedMessage.getPort()
                    );
                } catch (NullPointerException npe) {
                    LogUtils.error("NullPointerException distributor.dispatch => ", npe);
                }
            }
        }

        /**
         * Process called when a "start" of the thread occur
         */
        @Override
        public void run() {
            LogUtils.debug("Launch Read Thread on port " + this.port);
            try {
                // infinite loop. If a network problem occur : the infinite loop die
                while (true) {
                    // manage the message read
                    manageReading();
                }
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                LogUtils.debug("No socket...");
            } catch (IOException ioe) {
                LogUtils.debug("Socket closed on port => " + this.port);
                postmanServer.removeSocketCommunication(port);
                brain.notifyRemovePirate(this.port);
            }
        }
    }
    
    /**
     * Getter of the proxy client
     * @return : the proxy client
     */
    public ProxyClient getProxyClient() {
        return proxyClient;
    }
}