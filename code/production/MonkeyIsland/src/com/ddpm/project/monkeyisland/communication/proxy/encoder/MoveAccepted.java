/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.proxy.encoder;

import com.ddpm.project.monkeyisland.communication.protocole.ProcessOut;
import com.ddpm.project.monkeyisland.communication.protocole.ProtocolVocabulary;
import com.ddpm.project.monkeyisland.communication.proxy.ProxyClient;
import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.model.game.character.Pirate;

public class MoveAccepted extends AbstractEncoder {
    
    /**
     * Main constructor of the proxy client
     * @param proxyClient : the proxy client
     * @param brain : the brain of the system
     */
    public MoveAccepted(ProxyClient proxyClient, Brain brain) {
        super(proxyClient, brain);
    }
    
    /**
     * Process called to encode message
     * @param objects : some objects in the method
     * @return the encoded message
     */
    @Override
    public String encodeMessage(Object... objects) {
        return ProcessOut.PROXY_CLIENT_MOVE_ACCEPTED.process
            + String.valueOf(((Pirate)objects[0]).getPosition().getPositionX())
            + ProtocolVocabulary.SEPARATOR_MINUS
            + String.valueOf(((Pirate)objects[0]).getPosition().getPositionY())
            + ProtocolVocabulary.SEPARATOR_MINUS
            + String.valueOf(((Pirate)objects[0]).getEnergy().getValue());
    }
}
