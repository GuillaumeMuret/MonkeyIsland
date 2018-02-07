/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.distribution;

import com.ddpm.project.monkeyisland.controller.Brain;

public class SubscribePirate implements ICommandFromClient {

    /**
     * Process called to convert params and call manager
     * @param params : the params to decode
     * @param brain : the brain
     * @param port  the port of player socket
     */
    @Override
    public void convertMessageAndCallManager(String params, Brain brain, int port) {
        brain.getCharacterManager().subscribeNewPirate(port);
    }
}
