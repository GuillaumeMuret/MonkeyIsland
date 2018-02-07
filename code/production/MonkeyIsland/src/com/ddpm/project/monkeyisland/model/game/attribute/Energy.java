/**
 * @Author : Clément Pabst
 * @Collab : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.model.game.attribute;

public class Energy {

    /**
     * The value of the energy
     */
    private int value;

    /**
     * Main constructor of the energy
     *
     * @param value : the value of the energy
     */
    public Energy(int value) {
        this.value = value;
    }

    /**
     * Getter of the energy value
     * @return : the energy value
     */
    public int getValue() {
        return value;
    }

    /**
     * Setter of the energy value
     * @param value : the value of the energy
     */
    public void setValue(int value) {
        this.value = value;
    }
}
