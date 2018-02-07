/**
 * @Author : Guillaume Muret
 * @Collab : Cailyn Davies
 */

package com.ddpm.project.monkeyisland.controller;

import com.ddpm.project.monkeyisland.communication.Communication;
import com.ddpm.project.monkeyisland.communication.protocole.ProcessOut;
import com.ddpm.project.monkeyisland.communication.proxy.ProxyClient;
import com.ddpm.project.monkeyisland.utils.FileUtils;
import com.ddpm.project.monkeyisland.utils.LogUtils;
import com.ddpm.project.monkeyisland.model.constant.Constant;
import com.ddpm.project.monkeyisland.model.game.character.Pirate;
import com.ddpm.project.monkeyisland.model.game.object.Rum;
import com.ddpm.project.monkeyisland.model.game.object.Treasure;

import java.util.Timer;
import java.util.TimerTask;

public final class Brain {

    public enum GameState {
        /**
         * No init
         */
        NO_INIT,
        /**
         * game
         */
        GAMING,
        /**
         * End of the game
         */
        END_GAME
    }


    /**
     * Boolean Game State
     */
    private GameState gameState;

    /**
     * The communication of the application
     */
    private Communication communication;

    /**
     * The character manager who manage characters
     */
    private CharacterManager characterManager;

    /**
     * The map Manager who manage the map (square type)
     */
    private MapManager mapManager;

    /**
     * The object manager who manage the object
     */
    private ObjectManager objectManager;
    
    /**
     * Singleton management
     */
    private static Brain instance;

    /**
     * The timer of the relaunched game
     */
    private Timer timerRelaunchGame;

    /**
     * Main Constructor for the Brain
     */
    private Brain() {
        gameState = GameState.NO_INIT;
        FileUtils.loadGameConfiguration();
    }
    
    /**
     * Process called to wake up the brain and launch server
     */
    public void wakeUpBrain() {
        this.characterManager = CharacterManager.getInstance(this);
        this.mapManager = MapManager.getInstance(this);
        this.objectManager = ObjectManager.getInstance(this);
        this.communication = Communication.getInstance(this);
    }
    
    /**
     * Getter of the instance Brain
     * @return the instance Brain
     */
    public static Brain getInstance () {
        if (instance == null) {
            instance = new Brain();
        }
        return instance;
    }

    /**
     * Creation of the CharacterManager
     * @return : the character manager
     */
    public CharacterManager getCharacterManager() {
        return characterManager;
    }

    /**
     * Creation of the MapManager
     * @return : the map manager
     */
    public MapManager getMapManager() {
        return mapManager;
    }

    /**
     * Creation of the ObjectManager
     * @return the object manager
     */
    public ObjectManager getObjectManager() {
        return objectManager;
    }

    /**
     * Creation of the GameState
     * @return the Game state
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Creation of the Communication object
     * @return : the communication
     */
    public Communication getCommunication() {
        return communication;
    }
    
    /**
     * Setter of the communication instance
     * @param communication : the communication
     */
    public void setCommunication(Communication communication) {
        this.communication = communication;
    }

    /**
     * Setter of the GameSate
     * @param gameState : the game state
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Setter of the character manager instance
     * @param characterManager : the character manager
     */
    public void setCharacterManager(CharacterManager characterManager) {
        this.characterManager = characterManager;
    }
    
    /**
     * Setter of the map manager
     * @param mapManager : the map manager
     */
    public void setMapManager(MapManager mapManager) {
        this.mapManager = mapManager;
    }
    
    /**
     * Setter of the object manager
     * @param objectManager : the object manager
     */
    public void setObjectManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    /**
     * Getter of the timer that is used for reinitialize the game
     * @return : the timer
     */
    public Timer getTimerRelaunchGame() {
        return timerRelaunchGame;
    }

    /**
     * Process called to launch the timer that reinit all the game
     */
    public void launchReinitGameAndTimer() {
        if (timerRelaunchGame != null) {
            timerRelaunchGame.cancel();
        }
        timerRelaunchGame = new Timer();
        timerRelaunchGame.schedule(new TimerTask() {
            @Override
            public void run() {
                notifyInitAll();
                sendNewGame();
                characterManager.remakeAllPiratePosition();
                LogUtils.debug("End of launch timer");
            }
        }, Constant.TIME_REINIT_GAME);
    }
    
    /////////////////////
    // NETWORK REQUEST //
    /////////////////////
    
    /**
     * Process called to send pirate subscribed
     * @param port : the port of the pirate
     * @param pirate : the pirate subscribed
     */
    public void sendPirateSubscribed(int port, Pirate pirate) {
        getCommunication().getProxyClient().sendMessage(port, getCommunication().getProxyClient().getEncoders().get(
            ProcessOut.PROXY_CLIENT_ID_NEW_PIRATE_SUBSCRIBED.process).encodeMessage(pirate));
    }
    
    /**
     * Process called to send new pirate subscribed on map
     * @param pirate : the pirate on map
     */
    public void sendNewPirateOnMap(Pirate pirate) {
        ProxyClient proxyClient = getCommunication().getProxyClient();
        proxyClient.sendBroadcastExceptPort(pirate.getPort(), proxyClient.getEncoders().get(
            ProcessOut.PROXY_CLIENT_NEW_PIRATE.process).encodeMessage(pirate));
    
    }
    
    /**
     * Process called to send pirate move accepted
     * @param pirate : the pirate
     */
    public void sendPirateMoveAccepted(Pirate pirate) {
        LogUtils.debug("send pirate move accepted");
        ProxyClient proxyClient = getCommunication().getProxyClient();
        
        proxyClient.sendMessage(pirate.getPort(), proxyClient.getEncoders().get(
            ProcessOut.PROXY_CLIENT_MOVE_ACCEPTED.process).encodeMessage(pirate));
    
        proxyClient.sendBroadcastExceptPort(pirate.getPort(), proxyClient.getEncoders().get(
            ProcessOut.PROXY_CLIENT_MOVE_PIRATE.process).encodeMessage(pirate));
    }
    
    /**
     * Process called ot send the map to client
     * @param port : the port where to send the message
     */
    public void sendMapToClient(int port) {
        getCommunication().getProxyClient().sendMessage(port, getCommunication().getProxyClient().getEncoders().get(
            ProcessOut.PROXY_CLIENT_SET_MAP.process).encodeMessage());
    }
    
    /**
     * Process called to send the other pirate position
     * @param port : the port of the pirate
     */
    public void sendOtherPiratePosition(int port) {
        getCommunication().getProxyClient().sendMessage(port, getCommunication().getProxyClient().getEncoders().get(
            ProcessOut.PROXY_CLIENT_OTHER_PIRATES_POSITIONS.process).encodeMessage(port));
    }
    
    /**
     * Send the erratic monkey position to a port
     * @param port : the port where the message have to go
     */
    public void sendErraticMonkeyPosition(int port) {
        getCommunication().getProxyClient().sendMessage(port, getCommunication().getProxyClient().getEncoders().get(
            ProcessOut.PROXY_CLIENT_MONKEY_ERRATIC_POSITION.process).encodeMessage());
    }
    
    /**
     * Send broadcast of the erratic monkey position
     */
    public void sendBroadcastErraticMonkeyPosition() {
        getCommunication().getProxyClient().sendBroadcast(getCommunication().getProxyClient().getEncoders().get(
            ProcessOut.PROXY_CLIENT_MONKEY_ERRATIC_POSITION.process).encodeMessage());
    }
    
    /**
     * Send the hunter monkey position to a port
     * @param port : the socket player port
     */
    public void sendHunterMonkeyPosition(int port) {
        getCommunication().getProxyClient().sendMessage(port, getCommunication().getProxyClient().getEncoders().get(
            ProcessOut.PROXY_CLIENT_MONKEY_HUNTER_POSITION.process).encodeMessage());
    }
    
    /**
     * Send broadcast of the hunter monkey position
     */
    public void sendBroadcastHunterMonkeyPosition() {
        getCommunication().getProxyClient().sendBroadcast(getCommunication().getProxyClient().getEncoders().get(
            ProcessOut.PROXY_CLIENT_MONKEY_HUNTER_POSITION.process).encodeMessage());
    }
    
    /**
     * Process called to send pirate move refused
     * @param port : the player socket port
     */
    public void sendPirateMoveRefused(int port) {
        LogUtils.debug("send pirate move refused");
        getCommunication().getProxyClient().sendMessage(port, getCommunication().getProxyClient().getEncoders().get(
            ProcessOut.PROXY_CLIENT_MOVE_REFUSED.process).encodeMessage());
    }
    
    /**
     * Process called to send rum visibility to client
     * @param rum : the rum visibility
     * @param idInList : the id of the rum bottle
     */
    public void sendRumBottleVisibility(Rum rum, int idInList) {
        LogUtils.debug("Rum drink visibility change");
        getCommunication().getProxyClient().sendBroadcast(getCommunication().getProxyClient().getEncoders().get(
            ProcessOut.PROXY_CLIENT_BOTTLE_VISIBILITY.process).encodeMessage(rum, idInList));
    }
    
    /**
     * Process called to send the rum bottles position
     * @param port : the port of the client
     */
    public void sendRumBottlesPosition(int port) {
        getCommunication().getProxyClient().sendMessage(port, getCommunication().getProxyClient().getEncoders().get(
            ProcessOut.PROXY_CLIENT_BOTTLE_POSITION.process).encodeMessage());
    }
    
    /**
     * Process called to send broadcast that treasure is found
     * @param treasure : the treasure
     */
    public void sendTreasureFound(Treasure treasure) {
        LogUtils.debug("send treasure found");
        getCommunication().getProxyClient().sendBroadcast(getCommunication().getProxyClient().getEncoders().get(
            ProcessOut.PROXY_CLIENT_TREASURE_POSITION.process).encodeMessage(treasure));
    }
    
    /**
     * Process called to send broadcast that pirate is removed
     * @param port : the port of the pirate
     */
    private void sendPirateRemoved(int port) {
        LogUtils.debug("send suppress pirate");
        getCommunication().getProxyClient().sendBroadcast(getCommunication().getProxyClient().getEncoders().get(
            ProcessOut.PROXY_CLIENT_SUPPRESS_PIRATE.process).encodeMessage(port));
    }

    /**
     * Process called to send broadcast that a new game begin
     */
    private void sendNewGame() {
        LogUtils.debug("send new game");
        getCommunication().getProxyClient().sendBroadcast(getCommunication().getProxyClient().getEncoders().get(
            ProcessOut.PROXY_CLIENT_NEW_GAME.process).encodeMessage());
    }
    
    ///////////////////////////
    // INTERFACE INTERACTION //
    ///////////////////////////
    
    /**
     * Process called when a new connection occur
     */
    public void notifyNewConnection() {
        mapManager.onNewConnection();
        objectManager.onNewConnection();
        characterManager.onNewConnection();
    }
    
    /**
     * Process called to notify the manager to init the data
     */
    public void notifyInitAll() {
        mapManager.onInitAll();
        objectManager.onInitAll();
        characterManager.onInitAll();
        // FileUtils.generateCustomGameConfiguration();
    }
    
    /**
     * Process called to notify every manager that a new pirate is subscribed
     * @param pirate : the pirate subscribed
     */
    public void notifyPirateSubscribed(Pirate pirate) {
        if (gameState == GameState.NO_INIT) {
            gameState = GameState.GAMING;
        }
        mapManager.onPirateSubscribed(pirate.getPort());
        objectManager.onPirateSubscribed(pirate.getPort());
        characterManager.onPirateSubscribed(pirate.getPort());
        sendPirateSubscribed(pirate.getPort(), pirate);
        sendNewPirateOnMap(pirate);
    }
    
    /**
     * Process called to notify every manager that a pirate die
     * @param pirate : the pirate
     */
    public void notifyPirateDead(Pirate pirate) {
        mapManager.onPirateDead(pirate);
        objectManager.onPirateDead(pirate);
        characterManager.onPirateDead(pirate);
    }
    
    /**
     * Process called when a rum bottle is drunk
     * @param rum : the rum bottle
     * @param idRumInList : the rum bottle
     */
    public void notifyRumDrunk(Rum rum, int idRumInList) {
        objectManager.onRumDrunk(rum, idRumInList);
        characterManager.onRumDrunk(rum, idRumInList);
        mapManager.onRumDrunk(rum, idRumInList);
    }
    
    /**
     * Process called when a rum bottle reappear
     * @param rum : the rum bottle
     * @param idRumInList : the rum bottle
     */
    public void notifyRumReappear(Rum rum, int idRumInList) {
        mapManager.onRumReappear(rum, idRumInList);
        objectManager.onRumReappear(rum, idRumInList);
        characterManager.onRumReappear(rum, idRumInList);
    }

    /**
     * Process called when a treasure is found
     * @param treasure : the treasure found
     */
    public void notifyTreasureFound(Treasure treasure) {
        this.sendTreasureFound(treasure);
        mapManager.onTreasureFound(treasure);
        objectManager.onTreasureFound(treasure);
        characterManager.onTreasureFound(treasure);
        notifyEndGame();
    }

    /**
     * Process called when a treasure is found
     */
    public void notifyTreasureFound() {
        for (int i = 0; i < this.objectManager.getTreasureList().size(); i++) {
            Treasure treasure = this.objectManager.getTreasureList().get(0);
            notifyTreasureFound(treasure);
        }
    }

    /**
     * Process called to notify the managers that this is the end of the game
     */
    public void notifyEndGame() {
        gameState = GameState.END_GAME;
        launchReinitGameAndTimer();
    }
    
    /**
     * Process called when a pirate is disconnected or quit the game
     * @param port : the port of the pirate
     */
    public void notifyRemovePirate(int port) {
        mapManager.onRemovePirate(port);
        objectManager.onRemovePirate(port);
        characterManager.onRemovePirate(port);
        this.sendPirateRemoved(port);
    }
    
    /**
     * Process called to notify the clients of the pirate position and the other map elements
     * @param pirate : the pirate
     */
    public void notifyPirateReinitialisation(Pirate pirate) {
        mapManager.onPirateSubscribed(pirate.getPort());
        objectManager.onPirateSubscribed(pirate.getPort());
        characterManager.onPirateSubscribed(pirate.getPort());
        sendPirateSubscribed(pirate.getPort(), pirate);
    }
}
