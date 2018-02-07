/**
 * @Author : Cailyn Davies
 * @Collab : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.model.game.character;

import com.ddpm.project.monkeyisland.model.constant.Constant;
import com.ddpm.project.monkeyisland.model.game.position.Position;
import com.google.gson.annotations.SerializedName;

public abstract class Character {

    public enum State {
        /**
         * When a pirate is dead
         */
        DEAD,
        /**
         * When a pirate is drunk
         */
        DRUNK,
        /**
         * When the character is normal
         */
        NORMAL
    }

    public enum CharacterType {
        PIRATE,
        MONKEY,
    }

    public enum CharacterEventWhenMove {
        NONE(Constant.EVENT_NONE_VALUE),
        DIE_BY_MONKEY(Constant.EVENT_DIE_BY_MONKEY_VALUE),
        DIE_BY_ENERGY(Constant.EVENT_DIE_BY_ENERGY_VALUE),
        DRINK(Constant.EVENT_DRINK_VALUE),
        TREASURE(Constant.EVENT_TREASURE_VALUE),
        KILL(Constant.EVENT_KILL_VALUE);
    
        public String value;
    
        /**
         * The event when character event move
         * @param value the event value
         */
        CharacterEventWhenMove(String value) {
            this.value = value;
        }
    }

    /**
     * The position of the character
     */
    @SerializedName("position")
    private Position position;
    
    /**
     * The state of the pirate
     */
    @SerializedName("state")
    protected State state;

    /**
     * The type of the character
     */
    @SerializedName("character_type")
    private CharacterType characterType;

    /**
     * The event trigged by the character
     */
    private CharacterEventWhenMove characterEventWhenMove;

    /**
     * Main constructor of the character
     * @param position : the position of the character
     * @param type : the character type
     */
    public Character(Position position, CharacterType type) {
        this.position=position;
        this.characterType=type;
        this.state=State.NORMAL;
    }

    /**
     * Getter of the character type
     * @return : the character type
     */
    public CharacterType getCharacterType() {
        return characterType;
    }

    /**
     * Setter of the character type
     * @param type : the character type
     */
    public void setCharacterType(CharacterType type) {
        this.characterType = type;
    }

    /**
     * Getter of the character position
     * @return : the character position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Setter of the character position
     * @param position : the character position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Process called to set the position of the character
     * @param newPosition : the new position
     */
    public void moveCharacter(Position newPosition) {
        this.position = newPosition;
    }
    
    /**
     * Getter of the state
     * @return : the state
     */
    public State getState() {
        return state;
    }
    
    /**
     * Setter of the state
     * @param newState : the new state
     */
    public void setState(State newState) {
        this.state = newState;
    }
    
}
