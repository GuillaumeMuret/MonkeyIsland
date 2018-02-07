/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication.distribution;

import com.ddpm.project.monkeyisland.controller.Brain;

/**
 * Use to convert message string into model data and call manager
 */
public interface ICommandFromClient {
    
    /**
     * Process called to convert the params received and call the manager
     * @param params : the params
     * @param brain : the brain (to call the managers)
     * @param port : the port of the player socket
     */
    void convertMessageAndCallManager(String params, Brain brain, int port);
}
