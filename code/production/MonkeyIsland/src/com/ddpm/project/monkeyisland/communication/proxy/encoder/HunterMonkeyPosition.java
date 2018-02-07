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

public class HunterMonkeyPosition extends AbstractEncoder {
    
    /**
     * Main constructor of the hunter monkey position process out
     *
     * @param proxyClient : the client proxy
     * @param brain : the brain of the system
     */
    public HunterMonkeyPosition(ProxyClient proxyClient, Brain brain) {
        super(proxyClient, brain);
    }
    
    /**
     * Process called to encode the message to send
     * @return : the encoded message
     */
    @Override
    public String encodeMessage(Object... objects) {
        return ProcessOut.PROXY_CLIENT_MONKEY_HUNTER_POSITION.process
                   + getEncodedMessageHunterPositions(brain.getCharacterManager().getMonkeys());
    }
    
    /**
     * Process called to get the encoded message of the hunter monkey positions
     * @param monkeyList : the monkey list
     * @return the encoded message
     */
    private String getEncodedMessageHunterPositions(ArrayList<Monkey> monkeyList) {
        StringBuilder encodedMessage = new StringBuilder("");
        ArrayList<Monkey> hunterMonkeyList = getHunterMonkeyList(monkeyList);
        for (int i = 0; i < hunterMonkeyList.size(); i++) {
            encodedMessage.append(hunterMonkeyList.get(i).getPosition().getPositionX());
            encodedMessage.append(ProtocolVocabulary.SEPARATOR_MINUS);
            encodedMessage.append(hunterMonkeyList.get(i).getPosition().getPositionY());
            if (i < hunterMonkeyList.size() - 1) {
                encodedMessage.append(ProtocolVocabulary.SEPARATOR_3_UNDERSCORE);
            }
        }
        return encodedMessage.toString();
    }
    
    /**
     * Process called to get the hunter monkey list
     * @param monkeyList : the monkey list
     * @return the erratic monkey list
     */
    public static ArrayList<Monkey> getHunterMonkeyList(ArrayList<Monkey> monkeyList) {
        ArrayList<Monkey> hunterMonkeyList = new ArrayList<>();
        for (int i = 0; i < monkeyList.size(); i++) {
            if (monkeyList.get(i).getMonkeyType() == Monkey.MonkeyType.HUNTER) {
                hunterMonkeyList.add(monkeyList.get(i));
            }
        }
        return hunterMonkeyList;
    }
}