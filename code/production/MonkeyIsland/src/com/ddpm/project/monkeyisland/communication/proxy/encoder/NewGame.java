/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.proxy.encoder;

import com.ddpm.project.monkeyisland.communication.protocole.ProcessOut;
import com.ddpm.project.monkeyisland.communication.proxy.ProxyClient;
import com.ddpm.project.monkeyisland.controller.Brain;

public class NewGame extends AbstractEncoder {

    /**
     * Main constructor of the new game process encoder
     * @param proxyClient : the client proxy
     * @param brain : the brain of the system
     */
    public NewGame(ProxyClient proxyClient, Brain brain) {
        super(proxyClient, brain);
    }
    
    /**
     * Process called to encode message
     * @param objects : some objects in the method
     * @return the encoded message
     */
    @Override
    public String encodeMessage(Object... objects) {
        return ProcessOut.PROXY_CLIENT_NEW_GAME.process;
    }
    
    /**
     * Process called to send the broadcast that the new game begin
     */
    public void sendBroadcast() {
        this.proxyClient.sendBroadcast(ProcessOut.PROXY_CLIENT_NEW_GAME.process);
    }
}
