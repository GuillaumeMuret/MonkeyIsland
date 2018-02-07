/**
 * @Author : Guillaume Muret
 * @Collab : Cailyn Davies
 */

package com.ddpm.project.monkeyisland.controller.command;

import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.model.game.character.Character;
import com.ddpm.project.monkeyisland.model.game.position.Position;
import com.ddpm.project.monkeyisland.utils.PathFindHunterUtils;


public class CommandHunterPosition implements ICommandPosition {
    
    /**
     * The brain of the game
     */
    private Brain brain;
    
    /**
     * Main constructor of the erratic position command
     * @param brain : the brain of the game
     */
    public CommandHunterPosition(Brain brain) {
        this.brain = brain;
    }
    
    /**
     * Process called to get the position according to the command
     * @param character : the character to move
     * @return : the position according to the command
     */
    @Override
    public Position getPosition(Character character) {
        Position newMonkeyPosition = PathFindHunterUtils.calculateHunterMove(character.getPosition(),brain);
        return newMonkeyPosition;
    }
}
