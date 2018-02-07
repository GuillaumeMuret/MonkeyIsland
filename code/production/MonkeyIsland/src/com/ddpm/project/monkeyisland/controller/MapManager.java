/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.controller;

import com.ddpm.project.monkeyisland.model.game.character.Pirate;
import com.ddpm.project.monkeyisland.model.game.map.Map;
import com.ddpm.project.monkeyisland.model.game.map.Square;
import com.ddpm.project.monkeyisland.model.game.object.Rum;
import com.ddpm.project.monkeyisland.model.game.object.Treasure;
import com.ddpm.project.monkeyisland.model.game.position.Position;

import java.util.ArrayList;

public final class MapManager extends AbstractManager {
    
    /**
     * The Brain of the system
     */
    private Brain brain;
    
    /**
     * The game map
     */
    private Map map;
    
    /**
     * Singleton management
     */
    private static MapManager instance;
    
    /**
     * Main constructor of the map manager
     *
     * @param brain : the brian
     */
    private MapManager(Brain brain) {
        this.brain = brain;
        this.map = Map.getInstance();
    }
    
    /**
     * Getter of the instance Map Manager
     *
     * @param brain : the brain
     * @return the instance Map Manager
     */
    public static MapManager getInstance(Brain brain) {
        if (instance == null) {
            instance = new MapManager(brain);
        }
        return instance;
    }
    
    /**
     * Getter of the ground square list
     * @return the ground square list
     */
    public ArrayList<Square> getClonedGroundSquareList() {
        return this.map.getGroundSquareList();
        /*
        ArrayList<Square> clonedList = new ArrayList<Square>();
        for (Square square : this.map.getGroundSquareList()) {
            clonedList.add(new Square(square));
        }
        return clonedList;
        */
    }
    
    /**
     * Process called to get the square type
     *
     * @param position : the position of the square
     * @return : the square type of the square
     */
    public Square.SquareType getSquareType(Position position) {
        if (position != null
                && position.getPositionX() < this.map.getSquareList().size()
                && position.getPositionY() < this.map.getSquareList().get(position.getPositionX()).size()
            ) {
            if (position.getPositionX() >= 0 && position.getPositionY() >= 0) {
                return this.map.getSquareList().get(position.getPositionX()).get(
                    position.getPositionY()).getSquareType();
            }
        }
        return Square.SquareType.NONE;
    }
    
    /**
     * Getter of the map
     * @return : the map
     */
    public Map getMap() {
        return map;
    }
    
    @Override
    public void onNewConnection() {
    
    }
    
    @Override
    public void onInitAll() {
        this.map.initMap();
    }
    
    @Override
    public void onPirateSubscribed(int port) {
        brain.sendMapToClient(port);
    }
    
    @Override
    public void onPirateDead(Pirate pirate) {
    
    }
    
    @Override
    public void onRumDrunk(Rum rum, int idRumInList) {
    
    }
    
    @Override
    public void onRumReappear(Rum rum, int idRumInList) {
    
    }
    
    @Override
    public void onTreasureFound(Treasure treasure) {
    
    }
    
    @Override
    public void onRemovePirate(int port) {
    
    }
}
