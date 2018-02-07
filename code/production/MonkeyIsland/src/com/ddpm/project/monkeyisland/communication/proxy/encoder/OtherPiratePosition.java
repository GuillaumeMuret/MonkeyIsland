/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.proxy.encoder;

import com.ddpm.project.monkeyisland.communication.protocole.ProcessOut;
import com.ddpm.project.monkeyisland.communication.protocole.ProtocolVocabulary;
import com.ddpm.project.monkeyisland.communication.proxy.ProxyClient;
import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.model.game.character.Pirate;

import java.util.ArrayList;

public class OtherPiratePosition extends AbstractEncoder {
    
    /**
     * Main constructor of the other pirate position process out
     *
     * @param proxyClient : the client proxy
     * @param brain : the brain of the system
     */
    public OtherPiratePosition(ProxyClient proxyClient, Brain brain) {
        super(proxyClient, brain);
    }
    
    /**
     * Process called to encode message
     * @param objects : some objects in the method
     * @return the encoded message
     */
    @Override
    public String encodeMessage(Object... objects) {
        return ProcessOut.PROXY_CLIENT_OTHER_PIRATES_POSITIONS.process
                   + getEncodedMessageOtherPiratePositions(
                       (Integer)objects[0],brain.getCharacterManager().getPiratesList());
    }
    
    /**
     * Process called to get the encoded message of the other pirate positions
     * @param port : the port of the player socket
     * @param pirateList : the pirate list
     * @return the encoded message
     */
    private String getEncodedMessageOtherPiratePositions(int port, ArrayList<Pirate> pirateList) {
        StringBuilder encodedMessage = new StringBuilder("");
        for (int i = 0; i < pirateList.size(); i++) {
            if (pirateList.get(i).getPort() != port) {
                if (encodedMessage.length() > 0) {
                    encodedMessage.append(ProtocolVocabulary.SEPARATOR_3_UNDERSCORE);
                }
                encodedMessage.append(pirateList.get(i).getPort());
                encodedMessage.append(ProtocolVocabulary.SEPARATOR_MINUS);
                encodedMessage.append(pirateList.get(i).getPosition().getPositionX());
                encodedMessage.append(ProtocolVocabulary.SEPARATOR_MINUS);
                encodedMessage.append(pirateList.get(i).getPosition().getPositionY());
            }
        }
        return encodedMessage.toString();
    }
}