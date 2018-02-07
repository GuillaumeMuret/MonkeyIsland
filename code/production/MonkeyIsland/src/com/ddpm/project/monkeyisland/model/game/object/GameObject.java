/**
 * @Author : Clément Pabst
 * @Collab : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.model.game.object;

import com.ddpm.project.monkeyisland.model.game.position.Position;

public abstract class GameObject {
    
    public enum GameObjectType {
        TREASURE,
        RUM
    }

    /**
     * Boolean if the game object is visible or not
     */
    private boolean visible;

    /**
     * The object position
     */
    private Position position;
    
    /**
     * The game object type
     */
    protected GameObjectType type;

    /**
     * Main constuctor of the game object
     * @param position : position of the object
     * @param visible : boolean if the object is visible or not
     */
    public GameObject(Position position, boolean visible) {
        this.position = position;
        this.visible = visible;
    }

    /**
     * Getter of the game object is visible or not
     * @return : the boolean if the object is visible or not
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Setter of the object visibility
     * @param visible : the object visibility
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    /**
     * Getter of the position
     * @return the position
     */
    public Position getPosition() {
        return position;
    }
    
    /**
     * Setter of the object position
     * @param position : the object position
     */
    public void setPosition(Position position) {
        this.position = position;
    }
    
    /**
     * Getter of the object type
     * @return : the object type
     */
    public GameObjectType getType() {
        return type;
    }
    
    /**
     * Setter of the object type
     * @param type : the object
     */
    public void setType(GameObjectType type) {
        this.type = type;
    }
}
