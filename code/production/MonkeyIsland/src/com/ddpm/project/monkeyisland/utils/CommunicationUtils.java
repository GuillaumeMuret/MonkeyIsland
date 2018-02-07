/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.utils;

import com.ddpm.project.monkeyisland.communication.protocole.ProtocolVocabulary;

import java.util.ArrayList;

public class CommunicationUtils {

    /**
     * Process called to encode the params for the process
     *
     * @param params : the different params
     * @return : the params in a string
     */
    public static String encodeParams(ArrayList<String> params) {
        StringBuilder encodedMessage = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            encodedMessage.append(params.get(i));
            if (i < params.size() - 1) {
                encodedMessage.append(ProtocolVocabulary.SEPARATOR_MINUS);
            }
        }
        return encodedMessage.toString();
    }

    /**
     * Process called to get the process from a received message
     *
     * @param message : the message received
     * @return : the process received or an error message
     */
    public static String getProcessFromMessage(String message) {
        if (message.length() >= ProtocolVocabulary.COMMAND_STRING_SIZE) {
            LogUtils.debug("PROCESS ==> " + message.substring(0, ProtocolVocabulary.COMMAND_STRING_SIZE));
            return message.substring(0, ProtocolVocabulary.COMMAND_STRING_SIZE);
        }
        LogUtils.debug(ProtocolVocabulary.ERROR_PROCESS);
        return ProtocolVocabulary.ERROR_PROCESS;
    }

    /**
     * Process called to get the params from the received message
     *
     * @param message : the received message
     * @return the message or an empty string if there is no param
     */
    public static String getParamsFromMesage(String message) {
        if (message.length() >= ProtocolVocabulary.COMMAND_STRING_SIZE + 1) {
            LogUtils.debug("PARAMS ==> " + message.substring(ProtocolVocabulary.COMMAND_STRING_SIZE + 1));
            return message.substring(ProtocolVocabulary.COMMAND_STRING_SIZE + 1);
        }
        LogUtils.debug("NO_PARAMS");
        return "";
    }
    
    /**
     * Process called to convert visible boolean to client data
     * @param visible : boolean of the visibility
     * @return : the visibility in client language
     */
    public static String convertVisibleToClientData(boolean visible) {
        if (visible) {
            return "1";
        } else {
            return "0";
        }
    }
}
