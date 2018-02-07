/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.proxy.encoder;

import com.ddpm.project.monkeyisland.communication.protocole.ProcessOut;
import com.ddpm.project.monkeyisland.communication.protocole.ProtocolVocabulary;
import com.ddpm.project.monkeyisland.communication.proxy.ProxyClient;
import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.utils.CommunicationUtils;
import com.ddpm.project.monkeyisland.model.game.map.Square;

import java.util.ArrayList;

public class SetMap extends AbstractEncoder {
    
    /**
     * Main constructor of the set map process out
     * @param proxyClient : the client proxy
     * @param brain : the brain of the system
     */
    public SetMap(ProxyClient proxyClient, Brain brain) {
        super(proxyClient, brain);
    }
    
    /**
     * Process called to encode message
     * @param objects : some objects in the method
     * @return the encoded message
     */
    @Override
    public String encodeMessage(Object... objects) {
        return ProcessOut.PROXY_CLIENT_SET_MAP.process
                   + String.valueOf(brain.getMapManager().getMap().getWidth())
                   + ProtocolVocabulary.SEPARATOR_WHITESPACE
                   + String.valueOf(brain.getMapManager().getMap().getHeight())
                   + ProtocolVocabulary.SEPARATOR_WHITESPACE
                   + CommunicationUtils.encodeParams(listSquaresAsSingleList(
                       brain.getMapManager().getMap().getSquareList()));
    }
    
    /**
     * Process called to get a single list of the map square list
     * @param listSquares : the map square list
     * @return : the single list of the map square
     */
    private ArrayList<String> listSquaresAsSingleList(ArrayList<ArrayList<Square>> listSquares) {
        ArrayList<String> singleList = new ArrayList<>();
        for (int j = 0; j < listSquares.get(0).size(); j++) {
            for (int i = 0; i < listSquares.get(j).size(); i++) {
                if (listSquares.get(i).get(j).getSquareType() == Square.SquareType.GROUND) {
                    singleList.add(ProtocolVocabulary.GROUND_VALUE);
                }
                if (listSquares.get(i).get(j).getSquareType() == Square.SquareType.SEA) {
                    singleList.add(ProtocolVocabulary.SEA_VALUE);
                }
            }
        }
        return singleList;
    }
}