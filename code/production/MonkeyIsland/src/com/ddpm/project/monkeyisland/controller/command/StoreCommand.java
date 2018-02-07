/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.controller.command;

import com.ddpm.project.monkeyisland.controller.Brain;

public final class StoreCommand {
    
    /**
     * Singleton management
     */
    private static StoreCommand instance;
    
    /**
     * The command to get spawn position
     */
    ICommandPosition commandSpawnPosition;
    
    /**
     * The command to get erratic position
     */
    ICommandPosition commandErraticPosition;
    
    /**
     * The command to get erratic position
     */
    ICommandPosition commandHunterPosition;
    
    /**
     * Main Constructor for the StoreCommand
     */
    private StoreCommand() {
        this.commandSpawnPosition = new CommandSpawnPosition(Brain.getInstance());
        this.commandErraticPosition = new CommandErraticPosition(Brain.getInstance());
        this.commandHunterPosition = new CommandHunterPosition(Brain.getInstance());
    }
    
    /**
     * Getter of the instance Brain
     * @return the instance Brain
     */
    public static StoreCommand getInstance () {
        if (instance == null) {
            instance = new StoreCommand();
        }
        return instance;
    }
    
    /**
     * Getter of the command spawn position
     * @return the spawn position
     */
    public ICommandPosition getCommandSpawnPosition() {
        return commandSpawnPosition;
    }
    
    /**
     * Getter of the command erratic position
     * @return the erratic position
     */
    public ICommandPosition getCommandErraticPosition() {
        return commandErraticPosition;
    }
    
    /**
     * Getter of the command hunter position
     * @return : the hunter position
     */
    public ICommandPosition getCommandHunterPosition() {
        return commandHunterPosition;
    }
}
