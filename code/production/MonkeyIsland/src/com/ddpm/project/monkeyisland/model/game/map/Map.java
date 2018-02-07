/**
 * @Author : Clément Pabst
 * @Collab : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.model.game.map;

import com.ddpm.project.monkeyisland.model.constant.GameConfig;
import com.ddpm.project.monkeyisland.model.game.position.Position;

import java.util.ArrayList;

public final class Map {

    /**
     * The square list
     */
    private ArrayList<ArrayList<Square>> squareList;

    /**
     * The ground square list
     */
    private ArrayList<Square> groundSquareList;

    /**
     * The width of the map
     */
    private int width;

    /**
     * The height of the map
     */
    private int height;

    /**
     * Singleton management
     */
    private static Map instance;

    /**
     * Main constructor of the Map
     */
    private Map() {
        this.squareList = new ArrayList<>();
        this.width = GameConfig.getInstance().getMapWidthX();
        this.height = GameConfig.getInstance().getMapHeightY();
    }

    /**
     * Getter of the instance Map
     *
     * @return the instance Map
     */
    public static Map getInstance() {
        if (instance == null) {
            instance = new Map();
        }
        return instance;
    }

    /**
     * Process called to init the game map from conf file
     */
    public void initMap() {
        this.squareList = GameConfig.getInstance().getSquaresList();
        initGroundSquareList();
        this.setWidth(GameConfig.getInstance().getMapWidthX());
        this.setHeight(GameConfig.getInstance().getMapHeightY());
    }

    /**
     * Process called to the random map
     */
    public void initRandomMap() {
        this.squareList = new ArrayList<>();
        for (int i = 0; i < this.width; i++) {
            this.squareList.add(new ArrayList<>());
            for (int j = 0; j < this.height; j++) {
                this.squareList.get(i).add(new Square(Square.SquareType.GROUND, new Position(i, j)));
            }
        }
        initGroundSquareList();
    }

    /**
     * Process called to init the ground square list
     */
    private void initGroundSquareList() {
        this.groundSquareList = new ArrayList<>();
        for (int x = 0; x < getSquareList().size(); x++) {
            for (int y = 0; y < getSquareList().get(x).size(); y++) {
                if (getSquareList().get(x).get(y).getSquareType().equals(Square.SquareType.GROUND)) {
                    groundSquareList.add(getSquareList().get(x).get(y));
                }
            }
        }
    }

    /**
     * Getter of the ground square list
     *
     * @return the ground square list
     */
    public ArrayList<Square> getGroundSquareList() {
        return groundSquareList;
    }

    /**
     * Getter of the square list
     *
     * @return : the square list
     */
    public ArrayList<ArrayList<Square>> getSquareList() {
        return squareList;
    }

    /**
     * Setter of the square list
     *
     * @param squareList : the square list
     */
    public void setSquareList(ArrayList<ArrayList<Square>> squareList) {
        this.squareList = squareList;
    }

    /**
     * Getter of the width
     *
     * @return : the map width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Setter of the map width
     *
     * @param width : the map width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Getter of the map height
     *
     * @return : the map height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter of the map height
     *
     * @param height : the map height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /*
    public void mapToString() {
        for (int i = 0; i < this.getSquareList().size(); i++) {
            for (int j = 0; j < this.getSquareList().get(i).size(); j++) {
                String value = this.getSquareList().get(i).get(j).getSquareType()
                .equals(Square.SquareType.GROUND) ? "0" : "1";
                System.out.print(" " + value + " ");
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("");
        System.out.println("");

        for (int i = 0; i < this.getSquareList().size(); i++) {
            for (int j = 0; j < this.getSquareList().get(i).size(); j++) {
                String value = "("+this.getSquareList().get(i).get(j).getPosition().getPositionX()+";"
                +this.getSquareList().get(i).get(j).getPosition().getPositionY()+")";
                System.out.print(" " + value + " ");
            }
            System.out.println("");
        }

        String value = "("+this.getSquareList().get(5).get(13).getPosition().getPositionX()+";"
                        +this.getSquareList().get(5).get(13).getPosition().getPositionY()+")";
        LogUtils.debug(value);
    }
    */
}
