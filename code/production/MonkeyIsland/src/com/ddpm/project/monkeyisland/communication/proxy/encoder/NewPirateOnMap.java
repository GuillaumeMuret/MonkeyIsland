/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.proxy.encoder;

import com.ddpm.project.monkeyisland.communication.protocole.ProcessOut;
import com.ddpm.project.monkeyisland.communication.protocole.ProtocolVocabulary;
import com.ddpm.project.monkeyisland.communication.proxy.ProxyClient;
import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.model.game.character.Pirate;

public class NewPirateOnMap extends AbstractEncoder {
    
    /**
     * Main constructor of this instruction
     * @param proxyClient : the proxy client
     * @param brain : the brain of the system
     */
    public NewPirateOnMap(ProxyClient proxyClient, Brain brain) {
        super(proxyClient, brain);
    }
    
    /**
     * Process called to encode message
     * @param objects : some objects in the method
     * @return the encoded message
     */
    @Override
    public String encodeMessage(Object... objects) {
        return ProcessOut.PROXY_CLIENT_NEW_PIRATE.process
                   + String.valueOf(((Pirate)objects[0]).getPort())
                   + ProtocolVocabulary.SEPARATOR_MINUS
                   + String.valueOf(((Pirate)objects[0]).getPosition().getPositionX())
                   + ProtocolVocabulary.SEPARATOR_MINUS
                   + String.valueOf(((Pirate)objects[0]).getPosition().getPositionY());
    }
}
