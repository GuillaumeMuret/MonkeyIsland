/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.proxy.encoder;

import com.ddpm.project.monkeyisland.communication.protocole.ProcessOut;
import com.ddpm.project.monkeyisland.communication.protocole.ProtocolVocabulary;
import com.ddpm.project.monkeyisland.communication.proxy.ProxyClient;
import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.model.game.object.Treasure;

public class TreasureFound extends AbstractEncoder {
    
    /**
     * Main constructor of the treasure found process encoder
     * @param proxyClient : the client proxy
     * @param brain : the brain of the system
     */
    public TreasureFound(ProxyClient proxyClient, Brain brain) {
        super(proxyClient, brain);
    }
    
    /**
     * Process called to encode message
     * @param objects : some objects in the method
     * @return the encoded message
     */
    @Override
    public String encodeMessage(Object... objects) {
        return ProcessOut.PROXY_CLIENT_TREASURE_POSITION.process
                   + ((Treasure)objects[0]).getPosition().getPositionX()
                   + ProtocolVocabulary.SEPARATOR_MINUS
                   + ((Treasure)objects[0]).getPosition().getPositionY();
    }
}
