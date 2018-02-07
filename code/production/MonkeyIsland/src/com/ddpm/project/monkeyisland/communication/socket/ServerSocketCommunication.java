/**
 * @Author : Guillaume Muret
 * @Collab : François de Broch d'Hotelans
 */

package com.ddpm.project.monkeyisland.communication.socket;

import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.utils.LogUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerSocketCommunication extends AbstractSocket {

    /**
     * the buffer used to read a message on the socket
     */
    private BufferedReader bufferedReader;

    /**
     * the buffer used to write a message on the socket
     */
    private BufferedWriter bufferedWriter;
    
    /**
     * The writing thread
     */
    private ServerSocketCommunication.Write writingThread;

    /**
     * The socket used for the connexion
     */
    private Socket mySocket;

    /**
     * Main constructor of the player socket
     *
     * @param mySocket : socket of the player
     */
    public ServerSocketCommunication(Socket mySocket) {
        this.mySocket = mySocket;
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(this.mySocket.getInputStream(), AbstractSocket.ENCODAGE));
            bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(this.mySocket.getOutputStream(), AbstractSocket.ENCODAGE));
            stateSocket = StateSocket.CONNECTED;
        } catch (SocketTimeoutException ste) {
            // appears when the socket reach timeout limit
            LogUtils.error("SocketTimeoutException : not found");
        } catch (IOException e) {
            // appears when the socket is already connected
            LogUtils.error("SocketError debug");
            e.printStackTrace();
        }
    }
    
    /**
     * Process called to write a message on the socket
     * @param message : the message to send
     */
    public void writeMessage(String message) {
        if (writingThread != null) {
            while (writingThread.getState() != Thread.State.TERMINATED) {
                LogUtils.useless();
            }
            writingThread = new ServerSocketCommunication.Write(message);
            writingThread.start();
        } else {
            writingThread = new ServerSocketCommunication.Write(message);
            writingThread.start();
        }
    }

    /**
     * read a message on the socket and return the message
     *
     * @return the message
     * @throws IOException : exception of Input Output
     * @throws NullPointerException : the null pointer exception
     */
    public SocketMessage readMessage() throws IOException, NullPointerException {
        String message = bufferedReader.readLine();
        if (message != null) {
            return new SocketMessage(this.mySocket.getPort(), message);
        } else {
            return null;
        }
    }

    /**
     * Write on the BufferWriter to send a message to the raspberry
     */
    private class Write extends Thread {

        /**
         * The message to send
         */
        private String messageToSend;
    
        /**
         * Constructor of the Thread to write a message
         * @param messageToSend : the message to send
         */
        public Write(String messageToSend) {
            this.messageToSend = messageToSend;
        }

        /**
         * Called when the client is executed
         */
        @Override
        public synchronized void run() {
            if (mySocket != null) {
                if (!mySocket.isClosed()) {
                    try {
                        LogUtils.debug("message => " + messageToSend);
                        bufferedWriter.write(messageToSend, 0, messageToSend.length());
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        stateSocket = StateSocket.ERROR;
                        Brain.getInstance().notifyRemovePirate(mySocket.getPort());
                        LogUtils.error("WRITE ERROR");
                    }
                } else {
                    stateSocket = StateSocket.ERROR;
                }
            }
        }
    }

    /**
     * Getter of the socket state
     *
     * @return the state's socket
     */
    public AbstractSocket.StateSocket getStateSocket() {
        return stateSocket;
    }
}
