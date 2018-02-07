/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.proxy.encoder;

import com.ddpm.project.monkeyisland.communication.proxy.ProxyClient;
import com.ddpm.project.monkeyisland.controller.Brain;

public abstract class AbstractEncoder {
    /**
     * the client proxy
     */
    protected ProxyClient proxyClient;
    
    /**
     * The brain of the system
     */
    protected Brain brain;
    
    /**
     * Main Constructor of the encoders
     * @param proxyClient : the proxy client where the message are going
     * @param brain : the brain of the system
     */
    public AbstractEncoder(ProxyClient proxyClient, Brain brain) {
        this.proxyClient = proxyClient;
        this.brain = brain;
    }
    
    /**
     * Process called to encode the message to send
     * @param objects : some objects in the method
     * @return : the encoded message
     */
    public abstract String encodeMessage(Object... objects);
}
