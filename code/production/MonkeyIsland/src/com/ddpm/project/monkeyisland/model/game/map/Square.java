/**
 * @Author : Clément Pabst
 * @Collab : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.model.game.map;

import com.ddpm.project.monkeyisland.model.game.position.Position;
import com.google.gson.annotations.SerializedName;

public class Square {

    public enum SquareType {
        NONE,
        GROUND,
        SEA
    }

    /**
     * Enum to know the square type
     */
    @SerializedName("square_type")
    private SquareType squareType;

    /**
     * The position of the square
     */
    @SerializedName("square_position")
    private Position position;
    
    /**
     * Main constructor of the square
     *
     * @param squareType : the square type
     * @param position   : the square position
     */
    public Square(SquareType squareType, Position position) {
        this.position = position;
        this.squareType = squareType;
    }
    
    /**
     * Constructor square by square
     *
     * @param square : the square to clone
     */
    public Square(Square square) {
        this.position = square.getPosition();
        this.squareType = square.getSquareType();
    }

    /**
     * Getter of the square type
     * @return : the square type
     */
    public SquareType getSquareType() {
        return squareType;
    }

    /**
     * Setter of the square type
     * @param squareType : the square type
     */
    public void setSquareType(SquareType squareType) {
        this.squareType = squareType;
    }

    /**
     * Getter of the square position
     * @return : the square position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Setter of the square position
     * @param position : the new square position
     */
    public void setPosition(Position position) {
        this.position = position;
    }
}
