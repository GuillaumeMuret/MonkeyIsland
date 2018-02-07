/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.communication;

import com.ddpm.project.monkeyisland.communication.distribution.ICommandFromClient;
import com.ddpm.project.monkeyisland.communication.distribution.MovePirate;
import com.ddpm.project.monkeyisland.communication.distribution.SubscribePirate;
import com.ddpm.project.monkeyisland.communication.protocole.ProcessIn;
import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.utils.LogUtils;

import java.util.HashMap;

public final class Distributor {

    /**
     * The process that could be called on the raspberry
     */
    private HashMap<String, ICommandFromClient> commands;

    /**
     * The brain that manage all managers
     */
    private Brain brain;
    
    /**
     * Singleton management
     */
    private static Distributor instance;
    
    /**
     * Main constructor -> create the different commands
     * @param brain : the brain
     */
    private Distributor(Brain brain) {
        this.brain = brain;

        // Create the object commands which will have all the process
        this.commands = new HashMap<>();

        // the different command
        this.commands.put(ProcessIn.SUBSCRIBE_PIRATE, new SubscribePirate());
        this.commands.put(ProcessIn.MOVE_PIRATE, new MovePirate());
    }
    
    /**
     * Getter of the instance Object Manager
     * @param brain : the brain
     * @return the instance Object Manager
     */
    public static Distributor getInstance (Brain brain) {
        if (instance == null) {
            instance = new Distributor(brain);
        }
        return instance;
    }

    /**
     * Main function that dispatch the process called
     *
     * @param idCommand : the id of the command called
     * @param params    : the params of the command
     * @param port : the port of the socket where the message arrived
     */
    public void dispatch(String idCommand, String params, int port) {
        LogUtils.debug("dispatch => " + idCommand + " on port " + port);
        if (commands.containsKey(idCommand)) {
            commands.get(idCommand).convertMessageAndCallManager(params, brain, port);
        } else {
            LogUtils.debug("NO_COMMAND_FOUND");
        }
    }
}
