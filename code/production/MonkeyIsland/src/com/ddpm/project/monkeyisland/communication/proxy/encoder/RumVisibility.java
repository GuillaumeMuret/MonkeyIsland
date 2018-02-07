/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.proxy.encoder;

import com.ddpm.project.monkeyisland.communication.protocole.ProcessOut;
import com.ddpm.project.monkeyisland.communication.protocole.ProtocolVocabulary;
import com.ddpm.project.monkeyisland.communication.proxy.ProxyClient;
import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.utils.CommunicationUtils;
import com.ddpm.project.monkeyisland.model.game.object.Rum;

public class RumVisibility extends AbstractEncoder {
    
    /**
     * Main constructor of this instruction
     * @param proxyClient : the proxy client
     * @param brain : the brain of the system
     */
    public RumVisibility(ProxyClient proxyClient, Brain brain) {
        super(proxyClient, brain);
    }
    
    /**
     * Process called to encode message
     * @param objects : some objects in the method
     * @return the encoded message
     */
    @Override
    public String encodeMessage(Object... objects) {
        return ProcessOut.PROXY_CLIENT_BOTTLE_VISIBILITY.process
                   + String.valueOf((int)objects[1])
                   + ProtocolVocabulary.SEPARATOR_MINUS
                   + CommunicationUtils.convertVisibleToClientData(((Rum)objects[0]).isVisible());
    }
}
