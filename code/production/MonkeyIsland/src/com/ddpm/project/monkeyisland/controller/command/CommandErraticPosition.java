/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.controller.command;

import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.model.game.character.Character;
import com.ddpm.project.monkeyisland.model.game.position.Position;
import com.ddpm.project.monkeyisland.utils.LogUtils;
import com.ddpm.project.monkeyisland.utils.PositionUtils;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class CommandErraticPosition implements ICommandPosition {
    
    /**
     * The brain of the game
     */
    private Brain brain;
    
    /**
     * Main constructor of the erratic position command
     * @param brain : the brain of the game
     */
    public CommandErraticPosition(Brain brain) {
        this.brain = brain;
    }
    
    /**
     * Process called to get the position according to the command
     * @param character : the character to move
     * @return : the position according to the command
     */
    @Override
    public Position getPosition(Character character) {
        Position theErraticPosition = null;
        int randomPositionIndex;
        ArrayList<Position> possiblePositions = getAllPossiblePositions(character);
        if (possiblePositions.size() > 1) {
            randomPositionIndex = ThreadLocalRandom.current().nextInt(0, possiblePositions.size());
            theErraticPosition = possiblePositions.get(randomPositionIndex);
        } else if (possiblePositions.size() == 1) {
            theErraticPosition = possiblePositions.get(0);
        } else {
            LogUtils.debug("no position found...");
        }
        return theErraticPosition;
    }
    
    /**
     * Getter of all the possible positions
     * @param character : the character to move
     * @return : all position possibilities
     */
    private ArrayList<Position> getAllPossiblePositions(Character character) {
        ArrayList<Position> possiblePositions = new ArrayList<>();
        int posX = character.getPosition().getPositionX();
        int posY = character.getPosition().getPositionY();
        
        // Test RIGHT (1;0)
        Position possiblePosition = new Position(posX + 1, posY);
        if (PositionUtils.isPossibleMoveMe(brain,character.getCharacterType(), possiblePosition)) {
            possiblePositions.add(possiblePosition);
        }
        
        // Test LEFT (-1;0)
        possiblePosition = new Position(posX - 1, posY);
        if (PositionUtils.isPossibleMoveMe(brain,character.getCharacterType(), possiblePosition)) {
            possiblePositions.add(possiblePosition);
        }
        
        // Test UP (0;1)
        possiblePosition = new Position(posX, posY + 1);
        if (PositionUtils.isPossibleMoveMe(brain,character.getCharacterType(), possiblePosition)) {
            possiblePositions.add(possiblePosition);
        }
        
        // Test DOWN (0;-1)
        possiblePosition = new Position(posX, posY - 1);
        if (PositionUtils.isPossibleMoveMe(brain,character.getCharacterType(), possiblePosition)) {
            possiblePositions.add(possiblePosition);
        }
        
        return possiblePositions;
    }
}
