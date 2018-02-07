/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.controller;

import com.ddpm.project.monkeyisland.model.game.character.Pirate;
import com.ddpm.project.monkeyisland.model.game.object.Rum;
import com.ddpm.project.monkeyisland.model.game.object.Treasure;

public interface IGameEvent {
    
    /**
     * Process called when a new connection occur
     */
    void onNewConnection();
    
    /**
     * Process called to init all the data
     */
    void onInitAll();
    
    /**
     * Process called when a pirate has been subscribed
     * @param port the port
     */
    void onPirateSubscribed(int port);
    
    /**
     * Process called when a pirate die
     * @param pirate : the pirate
     */
    void onPirateDead(Pirate pirate);
    
    /**
     * Process called when a rum bottle is drunk
     * @param rum : the rum bottle
     * @param idRumInList : the id of the rum bottle
     */
    void onRumDrunk(Rum rum, int idRumInList);
    
    /**
     * Process called when a rum bottle reappear
     * @param rum : the rum bottle
     * @param idRumInList : the id of the rum bottle
     */
    void onRumReappear(Rum rum, int idRumInList);
    
    /**
     * Process called when the treasure is found
     * @param treasure : the treasure found
     */
    void onTreasureFound(Treasure treasure);
    
    /**
     * Process called when the pirate is removed when socket error
     * @param port the port of the pirate removed
     */
    void onRemovePirate(int port);
    
}
