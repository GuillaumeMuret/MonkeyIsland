/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.proxy.encoder;

import com.ddpm.project.monkeyisland.communication.protocole.ProcessOut;
import com.ddpm.project.monkeyisland.communication.protocole.ProtocolVocabulary;
import com.ddpm.project.monkeyisland.communication.proxy.ProxyClient;
import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.model.game.character.Monkey;

import java.util.ArrayList;

public class ErraticMonkeyPosition extends AbstractEncoder {
    
    /**
     * Main constructor of the erratic monkey position process out
     *
     * @param proxyClient : the client proxy
     * @param brain : the brain of the system
     */
    public ErraticMonkeyPosition(ProxyClient proxyClient, Brain brain) {
        super(proxyClient, brain);
    }
    
    /**
     * Process called to encode the message to send
     * @return : the encoded message
     */
    @Override
    public String encodeMessage(Object... objects) {
        return ProcessOut.PROXY_CLIENT_MONKEY_ERRATIC_POSITION.process
                   + getEncodedMessageErraticPositions(brain.getCharacterManager().getMonkeys());
    }
    
    /**
     * Process called to get the encoded message of the erratic monkey positions
     * @param monkeyList : the monkey list
     * @return the encoded message
     */
    private String getEncodedMessageErraticPositions(ArrayList<Monkey> monkeyList) {
        StringBuilder encodedMessage = new StringBuilder("");
        ArrayList<Monkey> erraticMonkeyList = getErraticMonkeyList(monkeyList);
        for (int i = 0; i < erraticMonkeyList.size(); i++) {
            encodedMessage.append(erraticMonkeyList.get(i).getPosition().getPositionX());
            encodedMessage.append(ProtocolVocabulary.SEPARATOR_MINUS);
            encodedMessage.append(erraticMonkeyList.get(i).getPosition().getPositionY());
            if (i < erraticMonkeyList.size() - 1) {
                encodedMessage.append(ProtocolVocabulary.SEPARATOR_3_UNDERSCORE);
            }
        }
        return encodedMessage.toString();
    }
    
    /**
     * Process called to get the erratic monkey list
     * @param monkeyList : the monkey list
     * @return the erratic monkey list
     */
    public static ArrayList<Monkey> getErraticMonkeyList(ArrayList<Monkey> monkeyList) {
        ArrayList<Monkey> erraticMonkeyList = new ArrayList<>();
        for (int i = 0; i < monkeyList.size(); i++) {
            if (monkeyList.get(i).getMonkeyType() == Monkey.MonkeyType.ERRATIC) {
                erraticMonkeyList.add(monkeyList.get(i));
            }
        }
        return erraticMonkeyList;
    }
}