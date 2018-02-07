/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication;

public interface ISocketState {
    
    /**
     * Process called when a socket is connected
     * @param port : the port where the socket is connected
     */
    void onSocketConnected(int port);
}
