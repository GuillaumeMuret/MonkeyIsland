/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.controller.command;

import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.model.game.character.Character;
import com.ddpm.project.monkeyisland.model.game.position.Position;
import com.ddpm.project.monkeyisland.utils.PositionUtils;

public class CommandSpawnPosition implements ICommandPosition {
    
    /**
     * The brain of the game
     */
    private Brain brain;
    
    /**
     * Main constructor of the spawn position command
     * @param brain : the brain of the game
     */
    public CommandSpawnPosition(Brain brain) {
        this.brain = brain;
    }
    
    /**
     * Process called to get the position according to the command
     * @param character : the character to move
     * @return : the position according to the command
     */
    @Override
    public Position getPosition(Character character) {
        return PositionUtils.getSpawnPosition(
            brain.getMapManager().getClonedGroundSquareList(),
            brain.getCharacterManager().getCharacterList(),
            brain.getObjectManager().getObjectList()
        );
    }
}
