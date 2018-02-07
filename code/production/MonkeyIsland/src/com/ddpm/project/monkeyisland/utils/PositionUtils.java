/**
 * @Author : Cailyn Davies
 * @Collab : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.utils;

import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.model.game.character.Character;
import com.ddpm.project.monkeyisland.model.game.map.Square;
import com.ddpm.project.monkeyisland.model.game.object.GameObject;
import com.ddpm.project.monkeyisland.model.game.position.Position;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class PositionUtils {
    
    /**
     * Process called to know if a character is on this position
     * @param position : the position of the ground square
     * @param characterList : the character list
     * @return if a character is on this position
     */
    private static boolean isCharacterOnThisPosition(Position position, ArrayList<Character> characterList) {
        boolean characterOnThisPosition = false;
        for (int i = 0; i < characterList.size() && !characterOnThisPosition;i++) {
            if (characterList.get(i).getPosition().equals(position)) {
                characterOnThisPosition = true;
            }
        }
        return characterOnThisPosition;
    }
    
    /**
     * Process called to know if an object is on this position
     * @param position : the position of the ground square
     * @param objectList : the object list
     * @return if an object is on this position
     */
    private static boolean isObjectOnThisPosition(Position position, ArrayList<GameObject> objectList) {
        boolean objectOnThisPosition = false;
        for (int i = 0; i < objectList.size() && !objectOnThisPosition;i++) {
            if (objectList.get(i).getPosition().equals(position)) {
                objectOnThisPosition = true;
            }
        }
        return objectOnThisPosition;
    }
    
    /**
     * Process called to get a spawn position
     * @param groundSquareList : the ground square list
     * @param characterList : the character list
     * @param objectList : the object list
     * @return the spone position. Null if no position found
     */
    public static synchronized Position getSpawnPosition(
            ArrayList<Square> groundSquareList, ArrayList<Character> characterList, ArrayList<GameObject> objectList) {
        Position spawnPosition = null;
        for (int i = 0; i < groundSquareList.size() && spawnPosition == null; i++) {
            int randomPosition = ThreadLocalRandom.current().nextInt(0, groundSquareList.size());
            Position randomGroundPosition = groundSquareList.get(randomPosition).getPosition();
            if (!isCharacterOnThisPosition(randomGroundPosition, characterList)
                    && !isObjectOnThisPosition(randomGroundPosition, objectList)) {
                spawnPosition = groundSquareList.get(randomPosition).getPosition();
            } else {
                groundSquareList.remove(randomPosition);
            }
        }
        return spawnPosition;
    }
    
    /**
     * Process called to know if a movement is possible for a monkey
     * @param brain : the brain of the system
     * @param possiblePosition : the possible position
     * @param characterType : the character type
     * @return if a movement is possible
     */
    public static boolean isPossibleMoveMe(Brain brain, Character.CharacterType characterType, Position possiblePosition) {
        boolean movePossible = false;
        int positionX = possiblePosition.getPositionX();
        int positionY = possiblePosition.getPositionY();
        ArrayList<Square.SquareType> possibleSquareTypes = new ArrayList<>();
        possibleSquareTypes.add(Square.SquareType.GROUND);
        if (positionX < brain.getMapManager().getMap().getWidth()
                && positionX >= 0
                && positionY >= 0
                && positionY < brain.getMapManager().getMap().getHeight()) {
            if (possibleSquareTypes.contains(brain.getMapManager().getSquareType(possiblePosition))
                    && !isMyTypeOnThisPosition(brain, characterType, possiblePosition)
                ) {
                movePossible = true;
            }
        }
        return movePossible;
    }
    
    /**
     * Process called to know if a character of same type is on the position in param
     * @param brain : the brain of the system
     * @param position : the position where the character want to go
     * @param characterType : the character type if it's a pirate or a monkey
     * @return : if a character of same type is on the position in param
     */
    public static boolean isMyTypeOnThisPosition(Brain brain, Character.CharacterType characterType, Position position) {
        boolean found = false;
        ArrayList<Character> characterList = brain.getCharacterManager().getCharacterList();
        for (int i = 0; i < characterList.size() && !found; i++) {
            if (characterList.get(i).getCharacterType() == characterType
                    && characterList.get(i).getState() != Character.State.DEAD
                    && characterList.get(i).getPosition().isEquals(position)
                ) {
                found = true;
            }
        }
        return found;
    }
}
