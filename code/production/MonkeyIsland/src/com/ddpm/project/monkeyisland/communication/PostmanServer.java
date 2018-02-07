/**
 * @Author : Guillaume Muret
 * @Collab : François de Broch d'Hotelans
 */

package com.ddpm.project.monkeyisland.communication;

import com.ddpm.project.monkeyisland.communication.socket.ServerSocketCommunication;
import com.ddpm.project.monkeyisland.communication.socket.ServerSocketConnexion;
import com.ddpm.project.monkeyisland.communication.socket.SocketMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public final class PostmanServer {
    
    /**
     * Port use for the first communication
     */
    public static final int SERVER_PORT = 13579;
    
    /**
     * List of all the socket state observator
     */
    private ArrayList<ISocketState> listSocketStateObservator;
    
    /**
     * Map with KEY = port and VALUE = ServerSocketCommunication
     */
    private HashMap<Integer, ServerSocketCommunication> mapServerSocket;
    
    /**
     * Singleton management
     */
    private static PostmanServer instance;
    
    /**
     * Main constructor of the Postman
     */
    private PostmanServer() {
        listSocketStateObservator = new ArrayList<>();
        mapServerSocket = new HashMap<>();
        new ServerSocketConnexion(this, SERVER_PORT);
    }
    
    /**
     * Getter of the instance Postman
     *
     * @return the instance Postman
     */
    public static PostmanServer getInstance() {
        if (instance == null) {
            instance = new PostmanServer();
        }
        return instance;
    }
    
    /**
     * Process called to write message
     *
     * @param port    : the port of the socket where the message have to be sent
     * @param message : the message to send
     */
    public void writeMessage(int port, String message) {
        if (mapServerSocket != null && mapServerSocket.get(port) != null) {
            mapServerSocket.get(port).writeMessage(message);
        }
    }
    
    /**
     * Process called to broadcast message
     *
     * @param message : the message to broadcast
     */
    public void broadcastMessage(String message) {
        for (Integer key : mapServerSocket.keySet()) {
            mapServerSocket.get(key).writeMessage(message);
        }
    }
    
    /**
     * Process called to broadcast message and except port
     *
     * @param port    : the port of the socket to except
     * @param message : the message to broadcast
     */
    public void broadcastMessageWithPortException(int port, String message) {
        for (Integer key : mapServerSocket.keySet()) {
            if (key != port) {
                mapServerSocket.get(key).writeMessage(message);
            }
        }
    }
    
    /**
     * Read a message on the socket and return the message
     *
     * @param port : the port where the message will be send
     * @return : the socket message
     * @throws IOException          : the input output exception
     * @throws NullPointerException : the null pointer exception
     */
    public SocketMessage readMessage(int port) throws IOException, NullPointerException {
        return mapServerSocket.get(port).readMessage();
    }
    
    /**
     * Process called to get the socket state
     *
     * @param port : the port of the socket
     * @return the state of the socket
     */
    public ServerSocketConnexion.StateSocket getStateSocket(int port) {
        if (mapServerSocket.get(port) != null) {
            return mapServerSocket.get(port).getStateSocket();
        } else {
            return null;
        }
    }
    
    /**
     * Process called to add a socket observator
     *
     * @param iSocketState : the interface of the socket observator
     */
    public void addStateSocketObservator(ISocketState iSocketState) {
        listSocketStateObservator.add(iSocketState);
    }
    
    /**
     * Process called to notify all the observers of the socket state
     *
     * @param port                      : the port
     * @param serverSocketCommunication : the socket
     */
    public void notifyNewSocketConnected(int port, ServerSocketCommunication serverSocketCommunication) {
        // Add socket to map
        mapServerSocket.put(port, serverSocketCommunication);
    
        // Notify every observer of new socket created
        for (int i = 0; i < listSocketStateObservator.size(); i++) {
            listSocketStateObservator.get(i).onSocketConnected(port);
        }
    }
    
    /**
     * Process called to remove the socket communication. Occur on socket error
     * @param port : the port of the socket to remove
     */
    public void removeSocketCommunication(int port) {
        mapServerSocket.remove(port);
    }
}
