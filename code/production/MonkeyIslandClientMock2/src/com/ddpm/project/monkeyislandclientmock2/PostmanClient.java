package com.ddpm.project.monkeyislandclientmock2;

import java.io.*;
import java.net.*;

public class PostmanClient {

    /**
     * State of socket's postmanClient
     */
    public static final int CONNEXION = 0;
    public static final int CONNECTED = 1;
    public static final int ERROR = 2;
    public static final int KILL = 3;

    /**
     * Time waiting for a socket connection
     */
    public final static int TW_CONNECTION_SOCKET = 1000;

    /**
     * State of the socket
     */
    public int stateSocket;

    /**
     * Port use for the communication
     */
    public static final int SERVER_PORT = 13579;

    /**
     * Queue of the communication
     */
    public static final int QUEUE_WAIT = 100;

    /**
     * Address use for the communication
     */
    public static final String IP_ADDRESS = "127.0.0.1";

    /**
     * type of encodage.
     */
    private final static String ENCODAGE = "UTF-8";

    /**
     * The socket used for the connexion
     */
    private Socket mySocket;

    /**
     * the buffer used to read a message on the socket
     */
    private BufferedReader bufferedReader;

    /**
     * the buffer used to write a message on the socket
     */
    private BufferedWriter bufferedWriter;

    private Communication communication;

    /**
     * Builder of the Postman
     */
    public PostmanClient(Communication communication) {
        stateSocket = CONNEXION;
        PostmanClient.SetUpConnexion setUpConnexion = new PostmanClient.SetUpConnexion();
        setUpConnexion.start();
        this.communication = communication;
    }

    /**
     * write the message on the socket
     * @param message
     */
    public void writeMessage(String message) {
        new PostmanClient.Write(message).start();
    }

    /**
     * read a message on the socket and return the message
     * @return the message
     * @throws IOException
     */
    public String readMessage() throws IOException, NullPointerException {
        return bufferedReader.readLine();
    }

    /**
     * process to connect the application to the raspberry by the socket
     * @return mySocket
     */
    private class SetUpConnexion extends Thread {

        @Override
        public void run() {
            super.run();
            LogUtils.d("Creation du socket");

            stateSocket = ERROR; // default error : connection not available / has failed

            // Set up the socket
            try {

                mySocket = new Socket();
                mySocket.connect(new InetSocketAddress(InetAddress.getByName(IP_ADDRESS), SERVER_PORT), TW_CONNECTION_SOCKET);

                bufferedReader = new BufferedReader(new InputStreamReader(mySocket.getInputStream(), ENCODAGE));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(mySocket.getOutputStream(), ENCODAGE));
                stateSocket = CONNECTED;

                LogUtils.d("Socket Created");
                communication.launchReadThread();

            } catch (SocketTimeoutException ste) {

                // appears when the socket reach timeout limit
                LogUtils.e("SocketTimeoutException : not found");

            } catch (ConnectException ce) {

                // appears when the network is not available (wifi may be disabled)
                LogUtils.e("ConnectException : not found / Wifi interface disabled");

            } catch (IOException e) {

                // appears when the socket is already connected
                LogUtils.e("SocketError debug");
                e.printStackTrace();
            }
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

        public Write(String messageToSend) {
            this.messageToSend = messageToSend;
        }

        /**
         * Called when the client is executed
         */
        @Override
        public void run() {
            if (mySocket != null) {
                if (!mySocket.isClosed()) {
                    try {
                        LogUtils.d("ECRITURE...");
                        bufferedWriter.write(messageToSend, 0, messageToSend.length());
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        LogUtils.d("FIN ECRITURE");

                    } catch (IOException e) {
                        e.printStackTrace();
                        stateSocket = ERROR;
                        LogUtils.d("ECRITURE ERROR");
                    }
                } else {
                    stateSocket = ERROR;
                }
            }
        }
    }

    /**
     * Process called to close the socket
     */
    public void closeSocket() {
        if (this.mySocket != null) {
            if (this.mySocket.isConnected()) {
                try {
                    this.mySocket.close();
                    this.stateSocket=KILL;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Getter of the socket state
     * @return the state's socket
     */
    public int getStateSocket(){
        return stateSocket;
    }
}
