/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.distribution;

import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.utils.LogUtils;

public class MovePirate implements ICommandFromClient {
    
    /**
     * Process called to convert the MovePirate action into data
     * @param params : the params
     * @param brain : the brain (to call the managers)
     * @param port : the port of the player socket
     */
    @Override
    public void convertMessageAndCallManager(String params, Brain brain, int port) {
        String delimiter = " "; // define the delimiter
        String[] tokens = params.split(delimiter);
        if (tokens.length == 2) {
            try {
                int moveX = Integer.valueOf(tokens[0]);
                int moveY = Integer.valueOf(tokens[1]);
                brain.getCharacterManager().askMovePirate(port, moveX, moveY);
            } catch (NumberFormatException nfe) {
                LogUtils.error("NumberFormatException ==> " + nfe);
            }
        } else {
            LogUtils.error("Nb params error");
        }
    }
}