/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.proxy.encoder;

import com.ddpm.project.monkeyisland.communication.protocole.ProcessOut;
import com.ddpm.project.monkeyisland.communication.proxy.ProxyClient;
import com.ddpm.project.monkeyisland.controller.Brain;

public class PirateRemoved extends AbstractEncoder {
    
    /**
     * Main constructor of the pirate removed process encoder
     * @param proxyClient : the client proxy
     * @param brain : the brain of the system
     */
    public PirateRemoved(ProxyClient proxyClient, Brain brain) {
        super(proxyClient, brain);
    }
    
    /**
     * Process called to encode message
     * @param objects : some objects in the method
     * @return the encoded message
     */
    @Override
    public String encodeMessage(Object... objects) {
        return ProcessOut.PROXY_CLIENT_SUPPRESS_PIRATE.process
               + (Integer)objects[0];
    }
}
