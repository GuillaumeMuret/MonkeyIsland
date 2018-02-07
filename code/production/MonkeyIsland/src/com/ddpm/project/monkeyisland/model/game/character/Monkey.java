/**
 * @Author : Cailyn Davies
 * @Collab : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.model.game.character;

import com.ddpm.project.monkeyisland.model.game.position.Position;
import com.google.gson.annotations.SerializedName;

public class Monkey extends Character {
    
    /**
     * The monkey type
     */
    public enum MonkeyType {
        ERRATIC,
        HUNTER
    }
    
    /**
     * The type of monkey (erratic or hunter)
     */
    @SerializedName("monkey_type")
    private MonkeyType monkeyType;
    
    /**
     * The monkey constructor
     *
     * @param position : position of the monkey
     * @param monkeyType : the monkey type
     */
    public Monkey(Position position, MonkeyType monkeyType) {
        super(position, CharacterType.MONKEY);
        this.monkeyType = monkeyType;
    }
    
    /**
     * Constructor for clone monkey in parameter
     *
     * @param monkey : the monkey to clone
     */
    public Monkey(Monkey monkey) {
        super(monkey.getPosition(), CharacterType.MONKEY);
        this.monkeyType = monkey.getMonkeyType();
    }
    
    /**
     * Getter of the type of monkey
     * @return the type of monkey
     */
    public MonkeyType getMonkeyType() {
        return monkeyType;
    }
    
    /**
     * Setter of the monkey type
     * @param monkeyType : the monkey type
     */
    public void setMonkeyType(MonkeyType monkeyType) {
        this.monkeyType = monkeyType;
    }
}
