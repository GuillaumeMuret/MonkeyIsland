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

import java.util.ArrayList;

public class RumPosition extends AbstractEncoder {
    
    /**
     * Main constructor of the bottle position process out
     *
     * @param proxyClient : the client proxy
     * @param brain : the brain of the system
     */
    public RumPosition(ProxyClient proxyClient, Brain brain) {
        super(proxyClient, brain);
    }
    
    /**
     * Process called to encode message
     * @param objects : some objects in the method
     * @return the encoded message
     */
    @Override
    public String encodeMessage(Object... objects) {
        return ProcessOut.PROXY_CLIENT_BOTTLE_POSITION.process
                   + getEncodedMessageRumPositions(brain.getObjectManager().getRumList());
    }
    
    /**
     * Process called to get encoded message of rum bottle positions
     * @param rumList : the rum list
     * @return the encoded message
     */
    private String getEncodedMessageRumPositions(ArrayList<Rum> rumList) {
        StringBuilder encodedMessage = new StringBuilder("");
        for (int i = 0; i < rumList.size(); i++) {
            encodedMessage.append(rumList.get(i).getPosition().getPositionX());
            encodedMessage.append(ProtocolVocabulary.SEPARATOR_MINUS);
            encodedMessage.append(rumList.get(i).getPosition().getPositionY());
            encodedMessage.append(ProtocolVocabulary.SEPARATOR_MINUS);
            encodedMessage.append(CommunicationUtils.convertVisibleToClientData(rumList.get(i).isVisible()));
            if (i < rumList.size() - 1) {
                encodedMessage.append(ProtocolVocabulary.SEPARATOR_3_UNDERSCORE);
            }
        }
        return encodedMessage.toString();
    }
}