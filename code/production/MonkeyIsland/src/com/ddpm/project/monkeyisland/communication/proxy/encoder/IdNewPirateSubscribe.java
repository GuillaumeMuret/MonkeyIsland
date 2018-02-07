/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.proxy.encoder;

import com.ddpm.project.monkeyisland.communication.protocole.ProcessOut;
import com.ddpm.project.monkeyisland.communication.protocole.ProtocolVocabulary;
import com.ddpm.project.monkeyisland.communication.proxy.ProxyClient;
import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.model.game.character.Pirate;

public class IdNewPirateSubscribe extends AbstractEncoder {
    
    /**
     * Main constructor of this instruction
     * @param proxyClient : the proxy client of the system
     * @param brain : the brain of the system
     */
    public IdNewPirateSubscribe(ProxyClient proxyClient, Brain brain) {
        super(proxyClient, brain);
    }
    
    /**
     * Process called to encode the message to send
     * @return : the encoded message
     */
    @Override
    public String encodeMessage(Object... objects) {
        return ProcessOut.PROXY_CLIENT_ID_NEW_PIRATE_SUBSCRIBED.process
                   + String.valueOf(((Pirate)objects[0]).getPort())
                   + ProtocolVocabulary.SEPARATOR_MINUS
                   + String.valueOf(((Pirate)objects[0]).getPosition().getPositionX())
                   + ProtocolVocabulary.SEPARATOR_MINUS
                   + String.valueOf(((Pirate)objects[0]).getPosition().getPositionY())
                   + ProtocolVocabulary.SEPARATOR_MINUS
                   + String.valueOf(((Pirate)objects[0]).getEnergy().getValue());
    }
}
