/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.proxy;

import com.ddpm.project.monkeyisland.communication.PostmanServer;

public abstract class ProxyModel {
    
    /**
     * postmanServer use to send and receive the message
     */
    protected PostmanServer postmanServer;
    
    /**
     * Constructor of all the proxys
     *
     * @param postmanServer : the postmanServer
     */
    public ProxyModel(PostmanServer postmanServer) {
        this.postmanServer = postmanServer;
    }
    
    /**
     * Process called to send a message to a specific client
     * @param port : the port of the client
     * @param message : the message to send
     */
    public void sendMessage(int port, String message) {
        // Tell postman to Send the Message
        this.postmanServer.writeMessage(port, message);
    }
    
    /**
     * Process called to send a message to every client
     * @param message : the specific message
     */
    public void sendBroadcast(String message) {
        // Tell postman to send the broadcast
        this.postmanServer.broadcastMessage(message);
    }
    
    /**
     * Process called to send a broadcast and except the port
     * @param port : the port to except
     * @param message : the message to send
     */
    public void sendBroadcastExceptPort(int port, String message) {
        // Tell postman to send the broadcast with port exception
        this.postmanServer.broadcastMessageWithPortException(port,message);
    }
}