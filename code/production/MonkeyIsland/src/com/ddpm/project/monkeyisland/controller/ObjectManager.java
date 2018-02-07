/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.controller;

import com.ddpm.project.monkeyisland.controller.command.StoreCommand;
import com.ddpm.project.monkeyisland.model.constant.Constant;
import com.ddpm.project.monkeyisland.model.constant.GameConfig;
import com.ddpm.project.monkeyisland.model.game.character.Pirate;
import com.ddpm.project.monkeyisland.model.game.object.GameObject;
import com.ddpm.project.monkeyisland.model.game.object.Rum;
import com.ddpm.project.monkeyisland.model.game.object.Treasure;
import com.ddpm.project.monkeyisland.model.game.position.Position;
import com.ddpm.project.monkeyisland.utils.LogUtils;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public final class ObjectManager extends AbstractManager {
    
    /**
     * The brain of the system
     */
    private Brain brain;
    
    /**
     * The Rum list
     */
    private ArrayList<Rum> rumList;
    
    /**
     * The treasure list
     */
    private ArrayList<Treasure> treasureList;
    
    /**
     * Singleton management
     */
    private static ObjectManager instance;

    /**
     * Main constructor of the object manager
     *
     * @param brain : Mouahaha! Spin the wheel! I'm a cat, Meow
     */
    private ObjectManager(Brain brain) {
        this.brain = brain;
        this.rumList = new ArrayList<>();
        this.treasureList = new ArrayList<>();
    }
    
    /**
     * Getter of the instance Object Manager
     * @param brain : the brain
     * @return the instance Object Manager
     */
    public static ObjectManager getInstance (Brain brain) {
        if (instance == null) {
            instance = new ObjectManager(brain);
        }
        return instance;
    }

    /**
     * Initialize objects of the game
     */
    private void initGameObjects() {
        initTreasure();
        initRumList();
    }

    /**
     * Getter of the rum list
     * @return : the rum list
     */
    public ArrayList<Rum> getRumList() {
        return rumList;
    }
    
    /**
     * Getter of the treasure list
     * @return the treasure list
     */
    public ArrayList<Treasure> getTreasureList() {
        return treasureList;
    }
    
    /**
     * Setter of the rum list
     * @param rumList : the rum list
     */
    public void setRumList(ArrayList<Rum> rumList) {
        this.rumList = rumList;
    }
    
    /**
     * Setter of the treasure list
     * @param treasureList : the treasure list
     */
    public void setTreasureList(ArrayList<Treasure> treasureList) {
        this.treasureList = treasureList;
    }
    
    /**
     * Getter of the object list
     * @return : the object list
     */
    public ArrayList<GameObject> getObjectList() {
        ArrayList<GameObject> objectList = new ArrayList<>();
        objectList.addAll(rumList);
        objectList.addAll(treasureList);
        return objectList;
    }
    
    /**
     * Return the object at the specified position if there is one. return null otherwise
     *
     * @param position : the position investigated
     * @return : the position of the object. Null if no object
     */
    public GameObject getObjectInPosition(Position position) {
        GameObject objectFound = null;
        for (int i = 0; i < rumList.size() && objectFound == null; i++) {
            if (rumList.get(i).getPosition().isEquals(position)) {
                objectFound = rumList.get(i);
            }
        }
        for (int i = 0; i < treasureList.size() && objectFound == null; i++) {
            if (treasureList.get(i).getPosition().isEquals(position)) {
                objectFound = treasureList.get(i);
            }
        }
        return objectFound;
    }

    /**
     * Getter of the object position in list
     * @param gameObject : the game object
     * @return : the position of the object in list
     */
    public int getObjectPositionInList(GameObject gameObject) {
        int id = -1;
        if (gameObject != null && gameObject.getType() == GameObject.GameObjectType.RUM) {
            for (int i = 0; i < rumList.size() && id == -1; i++) {
                if (rumList.get(i).getPosition().equals(gameObject.getPosition())) {
                    id = i;
                }
            }
        }
        return id;
    }

    /**
     * Apply an event to an object
     *
     * @param gameObject : The object trigged
     * @param event      : the trigger event
     */
    public void gameObjectEvent(GameObject gameObject, int event) {

    }
    
    /**
     * Process called to init the treasure
     */
    private void initTreasure() {
        treasureList = new ArrayList<>();
        Position treasurePosition = StoreCommand.getInstance().getCommandSpawnPosition().getPosition(null);
        treasureList.add(new Treasure(
            treasurePosition,
            false
        ));
        LogUtils.debug("treasure position = " + treasurePosition.toString());
        // For mock treasureList.add(new Treasure(new Position(0,0),false)
    }
    
    /**
     * Process called to init the rum list with position
     */
    private void initRumList() {
        rumList = GameConfig.getInstance().getRumList();
    }
    
    /**
     * Process called to init the random rum list. To not use the conf file
     */
    private void initRandomRumList() {
        rumList = new ArrayList<>();
        for (int i = 0; i < Constant.RUM_NUMBER_INIT; i++) {
            rumList.add(new Rum(
                StoreCommand.getInstance().getCommandSpawnPosition().getPosition(null),
                true,
                Constant.RUM_ENERGY_INIT
            ));
        }
    }
    
    @Override
    public void onNewConnection() {
    
    }
    
    @Override
    public void onInitAll() {
        initGameObjects();
    }
    
    @Override
    public void onPirateSubscribed(int port) {
        brain.sendRumBottlesPosition(port);
    }
    
    @Override
    public void onPirateDead(Pirate pirate) {
    
    }
    
    @Override
    public void onRumDrunk(Rum rum, int idRumInList) {
        rum.setVisible(false);
        this.brain.sendRumBottleVisibility(rum, idRumInList);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                brain.notifyRumReappear(rum, idRumInList);
            }
        }, GameConfig.getInstance().getTimeRumVisibility());
    }
    
    @Override
    public void onRumReappear(Rum rum, int idRumInList) {
        rum.setVisible(true);
        brain.sendRumBottleVisibility(rum, idRumInList);
    }
    
    @Override
    public void onTreasureFound(Treasure treasure) {
        treasure.setVisible(true);
        this.brain.sendTreasureFound(treasure);
    }
    
    @Override
    public void onRemovePirate(int port) {
    
    }
}