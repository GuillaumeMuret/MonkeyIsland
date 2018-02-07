/**
 * @Author : Clément Pabst
 * @Collab : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.model.game.object;

import com.ddpm.project.monkeyisland.model.game.position.Position;

public class Treasure extends GameObject {

    /**
     * Boolean to know if the treasu
     */
    private boolean discovered;
    
    /**
     * Main constructor of the treasure
     * @param position : the position
     * @param visible the boolean if the treasure is visible or not
     */
    public Treasure(Position position, boolean visible) {
        super(position, visible);
        this.type = GameObjectType.TREASURE;
    }

    /**
     * Getter of the boolean discover
     * @return : of the treasure is discovered or not
     */
    public boolean isDiscovered() {
        return discovered;
    }

    /**
     * Setter of the boolean discover
     * @param discovered : boolean if discovered
     */
    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }
}
