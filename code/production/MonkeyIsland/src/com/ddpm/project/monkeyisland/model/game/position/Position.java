/**
 * @Author : Cailyn Davies
 * @Collab : Clément Pabst
 */

package com.ddpm.project.monkeyisland.model.game.position;

import com.google.gson.annotations.SerializedName;

public class Position {
    
    /**
     * The position X on the map
     */
    @SerializedName("position_x")
    private int positionX;
    
    /**
     * The position Y on the map
     */
    @SerializedName("position_y")
    private int positionY;

    /**
     * Override of the equals method for test use and hashmap
     * @param obj : position this should be equal to
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        boolean var = false;
            if (obj == null) {
                var = false;
            } else if (obj.getClass() == this.getClass() && this.isEquals((Position) obj)) {
                var = true;
            }
            return var;
    }

    /**
     * returns unique identifier for the same object, in this case we concatenate the x and y coordinates
     * @return unique int for each different object
     */
    @Override
    public int hashCode() {
        String wholeS = "" + this.getPositionX() + this.getPositionY();
        return Integer.parseInt(wholeS);
    }
    
    /**
     * Main constructor of the position
     * @param positionX : the position X
     * @param positionY : the position Y
     */
    public Position(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
    
    /**
     * Constructor of the position to clone it
     * @param position : the position to clone
     */
    public Position(Position position) {
        this.positionX = position.getPositionX();
        this.positionY = position.getPositionY();
    }
    
    /**
     * Process called to compared 2 positions if they are equals or not
     * @param position : the position to compare
     * @return : if the position are equals or not
     */
    public boolean isEquals(Position position) {
        return this.positionX == position.getPositionX() && this.positionY == position.getPositionY();
    }
    
    /**
     * Getter of the X position
     * @return : the X position
     */
    public int getPositionX() {
        return positionX;
    }
    
    /**
     * Setter of the X position
     * @param positionX : the X position
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    
    /**
     * Getter of the position Y
     * @return : the position Y
     */
    public int getPositionY() {
        return positionY;
    }
    
    /**
     * Setter of the position Y
     * @param positionY : the position Y
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    
    /**
     * Process called to get the Position object to string
     * @return : the position object to string
     */
    @Override
    public String toString() {
        return "position : (" + positionX + "," + positionY + ")";
    }
}
