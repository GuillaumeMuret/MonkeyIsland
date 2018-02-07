/**
 * @Author : Clément Pabst
 * @Collab : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.model.game.object;

import com.ddpm.project.monkeyisland.model.game.attribute.Energy;
import com.ddpm.project.monkeyisland.model.game.position.Position;

public class Rum extends GameObject {
    
    /**
     * The energy of the rum
     */
    private Energy energy;
    
    /**
     * Main constructor of the Rum bottle
     *
     * @param position : the position of the rum bottle
     * @param visible  : boolean if the Rum is visible or not
     * @param energy   : the energy of the bottle
     */
    public Rum(Position position, boolean visible, int energy) {
        super(position, visible);
        this.energy = new Energy(energy);
        this.type = GameObjectType.RUM;
    }
    
    /**
     * Constructor to clone the rum in parameter
     * @param rum : the rum to clone
     */
    public Rum(Rum rum) {
        super(rum.getPosition(), rum.isVisible());
        this.energy = new Energy(rum.getEnergy().getValue());
        this.type = GameObjectType.RUM;
    }
    
    /**
     * Getter of the energy
     * @return : the energy
     */
    public Energy getEnergy() {
        return energy;
    }
    
    /**
     * Setter of the energy
     * @param energy : the energy
     */
    public void setEnergy(Energy energy) {
        this.energy = energy;
    }
    
}
