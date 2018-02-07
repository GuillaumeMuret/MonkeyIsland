/**
 * @Author : Cailyn Davies
 * @Collab : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.model.game.character;

import com.ddpm.project.monkeyisland.model.game.attribute.Energy;
import com.ddpm.project.monkeyisland.model.game.position.Position;

public class Pirate extends Character {

    /**
     * The port of the socket
     */
    private int port;
    
    /**
     * The energy of the pirate
     */
    private Energy energy;

    /**
     * Main constructor of the pirate
     * @param position : the position of the pirate
     * @param port : the port where the server communicate with the user
     * @param energy : the energy of the pirate
     */
    public Pirate(Position position, int port, Energy energy) {
        super(position, CharacterType.PIRATE);
        this.port = port;
        this.energy = energy;
        this.state = State.NORMAL;
    }
    
    /**
     * Getter of the player socket
     * @return : the port of the player socket
     */
    public int getPort() {
        return port;
    }
    
    /**
     * Setter of the socket port
     * @param port : the port of the socket
     */
    public void setPort(int port) {
        this.port = port;
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
    
    /**
     * Process called to add energy to pirate
     * @param energy : the energy to add
     */
    public void addEnergy(Energy energy) {
        this.getEnergy().setValue(this.getEnergy().getValue() + energy.getValue());
    }

    /**
     * Remove energy for a move
     * @param energy : the energy
     */
    public void removeEnergy(Energy energy) {
        this.energy.setValue(this.energy.getValue() - energy.getValue());
    }
    
}
