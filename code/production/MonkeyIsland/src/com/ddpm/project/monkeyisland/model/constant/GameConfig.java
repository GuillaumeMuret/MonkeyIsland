/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.model.constant;

import com.ddpm.project.monkeyisland.model.game.character.Monkey;
import com.ddpm.project.monkeyisland.model.game.map.Square;
import com.ddpm.project.monkeyisland.model.game.object.Rum;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public final class GameConfig {
    
    /**
     * The map width
     */
    @SerializedName("map_width_x")
    private int mapWidthX;
    
    /**
     * The map height
     */
    @SerializedName("map_height_y")
    private int mapHeightY;
    
    /**
     * The map square list
     */
    @SerializedName("map_squares_list")
    private ArrayList<ArrayList<Square>> squaresList;
    
    /**
     * The rum list
     */
    @SerializedName("rum_list")
    private ArrayList<Rum> rumList;
    
    /**
     * The monkey list
     */
    @SerializedName("monkey_list")
    private ArrayList<Monkey> monkeyList;
    
    /**
     * The rum energy value
     */
    @SerializedName("rum_energy_value")
    private int rumEnergyValue;
    
    /**
     * The max energy value of a pirate
     */
    @SerializedName("max_energy_pirate_value")
    private int maxEnergyPirateValue;
    
    /**
     * The time for a bottle to be visible after being consumed
     */
    @SerializedName("time_bottle_visibility")
    private int timeRumVisibility;
    
    /**
     * The speed monkey erratic
     */
    @SerializedName("monkey_erratic_speed")
    private int monkeyErraticSpeed;
    
    /**
     * The speed monkey hunter
     */
    @SerializedName("monkey_hunter_speed")
    private int monkeyHunterSpeed;
    
    /**
     * Singleton management
     */
    private static GameConfig instance;
    
    /**
     * Main constructor of the game config
     */
    private GameConfig() {
    }
    
    /**
     * Getter of the GameConfig instance
     * @return : the GameConfig instance
     */
    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }
    
    /**
     * Setter of the GameConfig instance
     * @param gameConfig : the GameConfig
     */
    public static void setInstance(GameConfig gameConfig) {
        instance = gameConfig;
    }
    
    /**
     * Getter of the map width
     * @return : the map width
     */
    public int getMapWidthX() {
        return mapWidthX;
    }
    
    /**
     * Setter of the map width
     * @param mapWidthX : the map width
     */
    public void setMapWidthX(int mapWidthX) {
        this.mapWidthX = mapWidthX;
    }
    
    /**
     * Getter of the map height
     * @return : the map height
     */
    public int getMapHeightY() {
        return mapHeightY;
    }
    
    /**
     * Setter of the map height
     * @param mapHeightY : the map height
     */
    public void setMapHeightY(int mapHeightY) {
        this.mapHeightY = mapHeightY;
    }
    
    /**
     * Getter of the square list
     * @return : the square list
     */
    public ArrayList<ArrayList<Square>> getSquaresList() {
        ArrayList<ArrayList<Square>> clonedSquareList = new ArrayList<>();
        for (int i = 0; i < squaresList.size(); i++) {
            clonedSquareList.add(new ArrayList<>());
            for (int j = 0; j < squaresList.get(i).size(); j++) {
                clonedSquareList.get(i).add(new Square(squaresList.get(i).get(j)));
            }
        }
        return clonedSquareList;
    }
    
    /**
     * Setter of the square list
     * @param squaresList : the square list
     */
    public void setSquaresList(ArrayList<ArrayList<Square>> squaresList) {
        this.squaresList = squaresList;
    }
    
    /**
     * Getter of the rum list
     * @return : the rum list
     */
    public ArrayList<Rum> getRumList() {
        ArrayList<Rum> clonedRumList = new ArrayList<>();
        for (int i = 0; i < rumList.size(); i++) {
            clonedRumList.add(new Rum(rumList.get(i)));
        }
        return clonedRumList;
    }
    
    /**
     * Setter of the rum list
     * @param rumList : the rum list
     */
    public void setRumList(ArrayList<Rum> rumList) {
        this.rumList = rumList;
    }
    
    /**
     * Getter of the monkey list
     * @return : the monkey list
     */
    public ArrayList<Monkey> getMonkeyList() {
        ArrayList<Monkey> clonedMonkeyList = new ArrayList<>();
        for (int i = 0; i < monkeyList.size(); i++) {
            clonedMonkeyList.add(new Monkey(monkeyList.get(i)));
        }
        return clonedMonkeyList;
    }
    
    /**
     * Setter of the monkey list
     * @param monkeyList : the monkey list
     */
    public void setMonkeyList(ArrayList<Monkey> monkeyList) {
        this.monkeyList = monkeyList;
    }
    
    /**
     * Getter of the rum energy value
     * @return : the rum energy value
     */
    public int getRumEnergyValue() {
        return rumEnergyValue;
    }
    
    /**
     * Setter of the rum energy value
     * @param rumEnergyValue : the rum energy value
     */
    public void setRumEnergyValue(int rumEnergyValue) {
        this.rumEnergyValue = rumEnergyValue;
    }
    
    /**
     * Getter of the max energy pirate value
     * @return : the max energy pirate value
     */
    public int getMaxEnergyPirateValue() {
        return maxEnergyPirateValue;
    }
    
    /**
     * Setter of the max energy pirate value
     * @param maxEnergyPirateValue : the max energy pirate value
     */
    public void setMaxEnergyPirateValue(int maxEnergyPirateValue) {
        this.maxEnergyPirateValue = maxEnergyPirateValue;
    }
    
    /**
     * Setter of the rum bottle time visibility
     * @return the rum bottle time visibility
     */
    public int getTimeRumVisibility() {
        return timeRumVisibility;
    }
    
    /**
     * Setter of the rum bottle time visibility
     * @param timeRumVisibility : the rum bottle time visibility
     */
    public void setTimeRumVisibility(int timeRumVisibility) {
        this.timeRumVisibility = timeRumVisibility;
    }
    
    /**
     * Getter of the monkey erratic speed
     * @return : the monkey erratic speed
     */
    public int getMonkeyErraticSpeed() {
        return monkeyErraticSpeed;
    }
    
    /**
     * Setter of the monkey erratic speed
     * @param monkeyErraticSpeed : the monkey erratic speed
     */
    public void setMonkeyErraticSpeed(int monkeyErraticSpeed) {
        this.monkeyErraticSpeed = monkeyErraticSpeed;
    }
    
    /**
     * Getter of the monkey hunter speed
     * @return : the monkey hunter speed
     */
    public int getMonkeyHunterSpeed() {
        return monkeyHunterSpeed;
    }
    
    /**
     * Setter of the monkey hunter speed
     * @param monkeyHunterSpeed : the monkey hunter speed
     */
    public void setMonkeyHunterSpeed(int monkeyHunterSpeed) {
        this.monkeyHunterSpeed = monkeyHunterSpeed;
    }
}
