/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.socket;

public class SocketMessage {
    
    /**
     * The socket port
     */
    private int port;
    
    /**
     * The socket message
     */
    private String message;
    
    /**
     * Main constructor of the socket message
     * @param port : the port of the socket message
     * @param message : the message
     */
    public SocketMessage(int port, String message) {
        this.port = port;
        this.message = message;
    }
    
    /**
     * Getter of the port
     * @return : the port
     */
    public int getPort() {
        return port;
    }
    
    /**
     * Getter of the message
     * @return : the message
     */
    public String getMessage() {
        return message;
    }
}
