/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.controller.command;

import com.ddpm.project.monkeyisland.model.game.character.Character;
import com.ddpm.project.monkeyisland.model.game.position.Position;

public interface ICommandPosition {
    
    /**
     * Process called to get the position according to the command
     * @param character : the character to move
     * @return : the position according to the command
     */
    Position getPosition(Character character);
}
