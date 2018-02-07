/**
 * @Author : Cailyn Davies
 * @Collab : Guillaume Muret
 * @Collab : Francois de Broch d’Hotelans
 */
package com.ddpm.project.monkeyisland.controller;

import com.ddpm.project.monkeyisland.controller.command.StoreCommand;
import com.ddpm.project.monkeyisland.utils.LogUtils;
import com.ddpm.project.monkeyisland.model.constant.Constant;
import com.ddpm.project.monkeyisland.model.constant.GameConfig;
import com.ddpm.project.monkeyisland.model.game.attribute.Energy;
import com.ddpm.project.monkeyisland.model.game.character.Character;
import com.ddpm.project.monkeyisland.model.game.character.Monkey;
import com.ddpm.project.monkeyisland.model.game.character.Pirate;
import com.ddpm.project.monkeyisland.model.game.map.Square;
import com.ddpm.project.monkeyisland.model.game.object.GameObject;
import com.ddpm.project.monkeyisland.model.game.object.Rum;
import com.ddpm.project.monkeyisland.model.game.object.Treasure;
import com.ddpm.project.monkeyisland.model.game.position.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public final class CharacterManager extends AbstractManager {
    
    public enum Direction {
        /**
         * Move none
         */
        NONE,
        /**
         * Move left
         */
        LEFT,
        /**
         * Move right
         */
        RIGHT,
        /**
         * Move up
         */
        UP,
        /**
         * Move down
         */
        DOWN
    }
    
    /**
     * The Brain of the system
     */
    private Brain brain;
    
    /**
     * The list of pirates
     */
    private ArrayList<Pirate> pirateList;
    
    /**
     * The list of monkeys
     */
    private ArrayList<Monkey> monkeyList;
    
    /**
     * Singleton management
     */
    private static CharacterManager instance;

    /**
     * Timer used for the hunter monkey
     */
    private Timer timerHunter;

    /**
     * Timer used for the erratic monkey
     */
    private Timer timerErratic;

    /**
     * The boolean of the move monkey thread
     */
    private boolean interruptMoveMonkeyThread;

    /**
     * Main constructor of the CharacterManager
     *
     * @param brain : the brain
     */
    private CharacterManager(Brain brain) {
        this.brain = brain;
        this.pirateList = new ArrayList<>();
        this.monkeyList = new ArrayList<>();
    }
    
    /**
     * Getter of the instance Character Manager
     *
     * @param brain : the brain
     * @return the instance Character Manager
     */
    public static CharacterManager getInstance(Brain brain) {
        if (instance == null) {
            instance = new CharacterManager(brain);
        }
        return instance;
    }

    /**
     * Getter of the monkey list
     *
     * @return : the monkey list
     */
    public ArrayList<Monkey> getMonkeys() {
        return monkeyList;
    }

    /**
     * Setter of the monkey list
     * @param monkeyList : the monkey list
     */
    public void setMonkeyList(ArrayList<Monkey> monkeyList) {
        this.monkeyList = monkeyList;
    }

    /**
     * Getter of the pirate list
     *
     * @return : the pirate list
     */
    public ArrayList<Pirate> getPiratesList() {
        return pirateList;
    }

    /**
     * Setter of the pirate list
     * @param pirateList : the monkey list
     */
    public void setPirateList(ArrayList<Pirate> pirateList) {
        this.pirateList = pirateList;
    }
    
    /**
     * Process called to verify if the pirate movement is correct
     *
     * @param directionX : the X direction
     * @param directionY : the Y direction
     * @return : if the pirate movement is correct
     */
    private boolean isValidPirateMove(int directionX, int directionY) {
        boolean validMove = false;
        if (directionX == 1 && directionY == 0) {
            validMove = true;
        }
        if (directionX == 0 && directionY == 1) {
            validMove = true;
        }
        if (directionX == -1 && directionY == 0) {
            validMove = true;
        }
        if (directionX == 0 && directionY == -1) {
            validMove = true;
        }
        return validMove;
    }
    
    /**
     * Process called to init the monkeys
     */
    private void initMonkeys() {
        monkeyList = GameConfig.getInstance().getMonkeyList();
    }
    
    /**
     * Process called to init the monkeys on random game
     */
    private void initRandomMonkeys() {
        monkeyList = new ArrayList<>();
        for (int i = 0; i < Constant.MONKEY_ERRATIC_NUMBER_INIT; i++) {
            monkeyList.add(new Monkey(
                StoreCommand.getInstance().getCommandSpawnPosition().getPosition(null),
                Monkey.MonkeyType.ERRATIC)
            );
        }
        for (int i = 0; i < Constant.MONKEY_HUNTER_NUMBER_INIT; i++) {
            monkeyList.add(new Monkey(
                 StoreCommand.getInstance().getCommandSpawnPosition().getPosition(null),
                 Monkey.MonkeyType.HUNTER)
            );
        }
    }
    
    /**
     * Process called when the player want to move pirate
     *
     * @param port       : the port
     * @param directionX : the direction in x
     * @param directionY : the direction in y
     */
    public void askMovePirate(int port, int directionX, int directionY) {
        LogUtils.debug("askMovePirate");
        if (isValidPirateMove(directionX, directionY)) {
            Pirate pirate = getPirateFromPort(port);
            if (pirate != null && pirate.getState() != Character.State.DEAD) {
                Position goToPosition;
                if (pirate.getState() == Character.State.DRUNK) {
                    goToPosition = StoreCommand.getInstance().getCommandErraticPosition().getPosition(pirate);
                } else {
                    goToPosition = getPositionDestination(new Position(pirate.getPosition()), directionX, directionY);
                }
                if (goToPosition != null) {
                    managePirateMovePossible(port, pirate, goToPosition);
                } else {
                    this.brain.sendPirateMoveRefused(port);
                }
            } else {
                LogUtils.debug("Pirate null or dead");
                this.brain.sendPirateMoveRefused(port);
            }
        } else {
            LogUtils.debug("Invalid movement");
            this.brain.sendPirateMoveRefused(port);
        }
    }
    
    /**
     * Process called to manage if the move is possible for the pirate according to other character on map
     * @param port : the port of the pirate
     * @param pirate : the pirate on the map
     * @param goToPosition : the destination position of the pirate
     */
    private void managePirateMovePossible(int port, Pirate pirate, Position goToPosition) {
        LogUtils.debug(goToPosition.toString());
        Square.SquareType squareType = this.brain.getMapManager().getSquareType(goToPosition);
        LogUtils.debug(squareType.toString());
        LogUtils.debug(goToPosition.toString());
        Character otherCharacter = getCharacterInPosition(goToPosition);
        boolean movePossible = canMovePirate(pirate, squareType, otherCharacter);
        int id=-1;
        if (movePossible) {
            pirate.moveCharacter(goToPosition);
            GameObject gameObject = this.brain.getObjectManager().getObjectInPosition(goToPosition);
            id = this.brain.getObjectManager().getObjectPositionInList(gameObject);
            pirate.removeEnergy(new Energy(Constant.ENERGY_TO_REMOVE_WHEN_MOVE));
            if (pirate.getEnergy().getValue() <= GameConfig.getInstance().getMaxEnergyPirateValue()) {
                pirate.setState(Character.State.NORMAL);
            }
            HashMap<Character.CharacterEventWhenMove, String> eventMap = eventWhenMovePirate(
                pirate, otherCharacter, gameObject);
            computePirateEvent(eventMap, pirate, gameObject, id);
        } else {
            this.brain.sendPirateMoveRefused(port);
        }
    }
    
    /**
     * Process called to compute the action by event
     *
     * @param eventMap : the event list when move
     * @param pirate        : the pirate on move
     * @param object        : the object destination
     * @param id        : the id of the bottle
     */
    private void computePirateEvent(
            HashMap<Character.CharacterEventWhenMove,String> eventMap, Pirate pirate, GameObject object, int id) {
        for (Character.CharacterEventWhenMove event : eventMap.keySet()) {
            switch (eventMap.get(event)) {
                case Constant.EVENT_DIE_BY_ENERGY_VALUE:
                    if (!eventMap.containsKey(Character.CharacterEventWhenMove.DRINK)) {
                        pirate.setEnergy(new Energy(0));
                        pirate.setState(Character.State.DEAD);
                        this.brain.notifyPirateDead(pirate);
                    }
                    break;

                case Constant.EVENT_DIE_BY_MONKEY_VALUE:
                    pirate.setEnergy(new Energy(0));
                    pirate.setState(Character.State.DEAD);
                    this.brain.notifyPirateDead(pirate);
                    break;


                case Constant.EVENT_DRINK_VALUE:
                    if (object.isVisible()) {
                        pirate.addEnergy(((Rum) object).getEnergy());
                        if (pirate.getEnergy().getValue() > GameConfig.getInstance().getMaxEnergyPirateValue()) {
                            pirate.setState(Character.State.DRUNK);
                        }
                    } else {
                        // you do nothing
                        LogUtils.debug("Pirate move on a invisible rum bottle");
                    }
                    this.brain.notifyRumDrunk((Rum) object, id);
                    break;
    
                case Constant.EVENT_TREASURE_VALUE:
                    this.brain.notifyTreasureFound((Treasure) object);
                    break;
    
                default:
                case Constant.EVENT_NONE_VALUE:
                    break;
            }
        }
        this.brain.sendPirateMoveAccepted(pirate);
    }
    
    /**
     * Getter of the pirate port
     *
     * @param port : the gamer port
     * @return the pirate
     */
    public Pirate getPirateFromPort(int port) {
        Pirate pirate = null;
        for (int i = 0; i < this.pirateList.size() && pirate == null; i++) {
            if (this.pirateList.get(i).getPort() == port) {
                pirate = this.pirateList.get(i);
            }
        }
        return pirate;
    }
    
    /**
     * Process called to get the destination position
     *
     * @param piratePosition   : the initial position
     * @param directionX : the X direction where the character go
     * @param directionY : the Y direction where the character go
     * @return the new position
     */
    private Position getPositionDestination(Position piratePosition, int directionX, int directionY) {
        if ((piratePosition.getPositionX() + directionX) >= 0
                && (piratePosition.getPositionX() + directionX) < brain.getMapManager().getMap().getWidth()
                && (piratePosition.getPositionY() + directionY) >= 0
                && (piratePosition.getPositionY() + directionY) < brain.getMapManager().getMap().getHeight()
            ) {
            piratePosition.setPositionX(piratePosition.getPositionX() + directionX);
            piratePosition.setPositionY(piratePosition.getPositionY() + directionY);
        } else {
            LogUtils.debug("Pirate destination NOT in map !");
            piratePosition = null;
        }
        return piratePosition;
    }
    
    /**
     * Process called to get the character in position. If no character, return null
     *
     * @param position : the destination position
     * @return : the character position. If no position return null
     */
    private Character getCharacterInPosition(Position position) {
        Character characterFound = null;
        ArrayList<Character> characterList = getCharacterList();
        for (int i = 0; i < characterList.size() && characterFound == null; i++) {
            if (position.isEquals(characterList.get(i).getPosition())
                    && characterList.get(i).getState() != Character.State.DEAD) {
                characterFound = characterList.get(i);
            }
        }
        if (characterFound == null) {
            LogUtils.debug("No character found in position => " + position.toString());
        }
        return characterFound;
    }
    
    /**
     * Send true if the movement is legal, false otherwise.
     *
     * @param characterToMove             : the character to move
     * @param squareType                  : the square type
     * @param otherCharacterOnDestination : the other character on destination (null if no character)
     * @return : if the pirate can move or not
     */
    private boolean canMovePirate(
            Character characterToMove, Square.SquareType squareType, Character otherCharacterOnDestination) {
        boolean possible = false;
        if (characterToMove.getCharacterType().equals(Character.CharacterType.PIRATE)) {
            if (squareType.equals(Square.SquareType.GROUND)) {
                if (otherCharacterOnDestination == null) {
                    possible = true;
                } else {
                    if (otherCharacterOnDestination.getCharacterType().equals(Character.CharacterType.MONKEY)
                            || otherCharacterOnDestination.getState() == Character.State.DEAD
                    ) {
                        possible = true;
                    } else {
                        LogUtils.debug("Cannot move on square with a pirate");
                    }
                }
            } else {
                LogUtils.debug("pirate cannot move on sea square");
            }
        }
        LogUtils.debug("can move pirate ==> " + possible);
        return possible;
    }
    
    /**
     * Process called to get the character list
     *
     * @return the character list
     */
    public ArrayList<Character> getCharacterList() {
        ArrayList<Character> characterList = new ArrayList<>();
        characterList.addAll(monkeyList);
        characterList.addAll(pirateList);
        return characterList;
    }
    
    /**
     * Process called to get the event if the pirate move
     *
     * @param pirate                 : the pirate to move
     * @param characterOnDestination : the character on destination
     * @param object                 : the object on destination
     * @return : the event if the character move
     */
    private HashMap<Character.CharacterEventWhenMove,String> eventWhenMovePirate(
            Pirate pirate, Character characterOnDestination, GameObject object) {
        HashMap<Character.CharacterEventWhenMove, String> eventList = new HashMap<>();
    
        // when pirate go on rum bottle
        if (object != null && object.getType().equals(GameObject.GameObjectType.RUM)) {
            eventList.put(Character.CharacterEventWhenMove.DRINK,Constant.EVENT_DRINK_VALUE);
        }
    
        // when pirate go on treasure
        if (object != null && object.getType().equals(GameObject.GameObjectType.TREASURE)) {
            eventList.put(Character.CharacterEventWhenMove.TREASURE,Constant.EVENT_TREASURE_VALUE);
        }
    
        // when pirate has not enough energy
        if (pirate.getEnergy().getValue() <= 0) {
            eventList.put(Character.CharacterEventWhenMove.DIE_BY_ENERGY,Constant.EVENT_DIE_BY_ENERGY_VALUE);
        }
    
        // when pirate go on a monkey
        if (characterOnDestination != null) {
            if (characterOnDestination.getCharacterType().equals(Character.CharacterType.MONKEY)) {
                if (((Monkey) characterOnDestination).getMonkeyType() == Monkey.MonkeyType.ERRATIC
                        || ((Monkey) characterOnDestination).getMonkeyType() == Monkey.MonkeyType.HUNTER) {
                    eventList.put(Character.CharacterEventWhenMove.DIE_BY_MONKEY, Constant.EVENT_DIE_BY_MONKEY_VALUE);
                }
            }
        }
    
        // List the event on console
        for (Character.CharacterEventWhenMove events : eventList.keySet()) {
            LogUtils.debug("eventWhenMovePirate ==> " + eventList.get(events));
        }
        return eventList;
    }
    
    /**
     * Process called to subscribe a new pirate
     *
     * @param port : the port of the socket
     */
    public void subscribeNewPirate(int port) {
        LogUtils.debug("Subscribe new pirate");
    
        // Get spawn position
        Position position = StoreCommand.getInstance().getCommandSpawnPosition().getPosition(null);
    
        if (position != null) {
            LogUtils.debug("update pirate position ==> " + position.toString());
            if (!isPirateAlreadyInList(port)) {
                Pirate pirate = new Pirate(position, port, new Energy(GameConfig.getInstance().getMaxEnergyPirateValue()));
                this.pirateList.add(pirate);
                this.brain.notifyPirateSubscribed(pirate);
            }
        }
    }
    
    /**
     * Process called to remove the pirate in the pirate list
     * @param port the id of the pirate to remove
     */
    private void removePirate(int port) {
        boolean found = false;
        for (int i = 0; i < pirateList.size() && !found; i++) {
            if (pirateList.get(i).getPort() == port) {
                pirateList.remove(i);
                found = true;
            }
        }
    }
    
    /**
     * Process called to know if the pirate is already in the pirate list
     *
     * @param port : the port
     * @return the
     */
    private boolean isPirateAlreadyInList(int port) {
        boolean found = false;
        for (int i = 0; i < pirateList.size() && !found; i++) {
            if (pirateList.get(i).getPort() == port) {
                found = true;
            }
        }
        return found;
    }
    
    /**
     * Process called send event when monkey has move
     */
    private void eventWhenMoveMonkey() {
        ArrayList<Pirate> listPirateDead = new ArrayList<>();
        for (int i = 0; i < pirateList.size(); i++) {
            for (int j = 0; j < monkeyList.size(); j++) {
                if (pirateList.get(i).getPosition().isEquals(monkeyList.get(j).getPosition())) {
                    pirateList.get(i).setState(Character.State.DEAD);
                    pirateList.get(i).setEnergy(new Energy(0));
                    brain.sendPirateMoveAccepted(pirateList.get(i));
                    listPirateDead.add(pirateList.get(i));
                }
            }
        }
        for (int i = 0; i < listPirateDead.size(); i++) {
            brain.notifyPirateDead(listPirateDead.get(i));
        }
    }

    private class ThreadMoveMonkeysErratic extends Thread {

        @Override
        public void run() {
            LogUtils.debug("ThreadMoveMonkeysErratic");
            askMoveMonkeys(Monkey.MonkeyType.ERRATIC);
            brain.sendBroadcastErraticMonkeyPosition();
            eventWhenMoveMonkey();
            manageNextMoveThreadMonkeysErratic();
        }
    }

    /**
     * Process called to manage the next move monkeys thread
     */
    private void manageNextMoveThreadMonkeysErratic() {
        if (timerErratic != null) {
            timerErratic.cancel();
        }
        timerErratic = new Timer();
        timerErratic.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!interruptMoveMonkeyThread) {
                    new ThreadMoveMonkeysErratic().start();
                }
            }
        }, GameConfig.getInstance().getMonkeyErraticSpeed());
    }

    private class ThreadMoveMonkeysHunter extends Thread {

        @Override
        public void run() {
            LogUtils.debug("ThreadMoveMonkeysHunter");
            askMoveMonkeys(Monkey.MonkeyType.HUNTER);
            brain.sendBroadcastHunterMonkeyPosition();
            eventWhenMoveMonkey();
            manageNextMoveThreadMonkeysHunter();
        }
    }

    /**
     * Process called to manage the next move monkeys thread
     */
    private void manageNextMoveThreadMonkeysHunter() {
        if (timerHunter != null) {
            timerHunter.cancel();
        }
        timerHunter = new Timer();
        timerHunter.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!interruptMoveMonkeyThread) {
                    new ThreadMoveMonkeysHunter().start();
                }
            }
        }, GameConfig.getInstance().getMonkeyHunterSpeed());
    }
    
    /**
     * Process called to move monkeys according to its type
     * @param monkeyType : the monkey type
     */
    private synchronized void askMoveMonkeys(Monkey.MonkeyType monkeyType) {
        for (int i = 0; i < monkeyList.size(); i++) {
            if (monkeyList.get(i).getMonkeyType().equals(monkeyType)) {
                if (monkeyType.equals(Monkey.MonkeyType.ERRATIC)) {
                    setMovingErraticPosition(monkeyList.get(i));
                }
                if (monkeyType.equals(Monkey.MonkeyType.HUNTER)) {
                    setMovingHunterPosition(monkeyList.get(i));
                }
            }
        }
    }
    
    /**
     * Setter of the erratic monkey
     * @param monkey the erratic monkey
     */
    private void setMovingErraticPosition(Monkey monkey) {
        Position erraticPosition = StoreCommand.getInstance().getCommandErraticPosition().getPosition(monkey);
        if (erraticPosition != null) {
            monkey.setPosition(erraticPosition);
        }
    }
    
    /**
     * Setter of the hunter monkey position
     * @param monkeyHunter : the monkey hunter
     */
    private void setMovingHunterPosition(Monkey monkeyHunter) {
        Position hunterPosition = StoreCommand.getInstance().getCommandHunterPosition().getPosition(monkeyHunter);
        if (hunterPosition != null) {
            monkeyHunter.setPosition(hunterPosition);
        }
    }
    
    /**
     * Process called to get the pirate on the position in param. If found return pirate else return null
     * @param position : the position where pirate to find
     * @return the pirate if found else null
     */
    public Pirate getPirateOnPosition(Position position) {
        Pirate pirate = null;
        for (int i = 0; i < pirateList.size() && pirate == null; i++) {
            if (pirateList.get(i).getPosition().isEquals(position)) {
                pirate = pirateList.get(i);
            }
        }
        return pirate;
    }

    /**
     * Process called after a new game to reinit the pirate positions
     */
    public void remakeAllPiratePosition() {
        // Change all pirate position
        for (int i = 0; i < pirateList.size(); i++) {
            // Get spawn position
            Position position = StoreCommand.getInstance().getCommandSpawnPosition().getPosition(null);
            
            if (position != null) {
                LogUtils.debug("new pirate position ==> " + position.toString());
                pirateList.set(i, new Pirate(position, pirateList.get(i).getPort(),
                                                new Energy(GameConfig.getInstance().getMaxEnergyPirateValue())));
            }
        }
        // Send all new pirate position
        for (int i = 0; i < pirateList.size(); i++) {
            this.brain.notifyPirateReinitialisation(pirateList.get(i));
        }
    }
    
    /**
     * Process called to get the number of pirate alive
     * @return : the number of pirate alive
     */
    public int getNumberOfPirateAlive() {
        int sumPirateAlive = 0;
        for (int i = 0; i < pirateList.size(); i++) {
            if (pirateList.get(i).getState() != Character.State.DEAD) {
                sumPirateAlive++;
            }
        }
        return sumPirateAlive;
    }
    
    ////////////////
    // GAME EVENT //
    ////////////////
    
    @Override
    public void onNewConnection() {
        if (pirateList.size() == 0) {
            this.brain.notifyInitAll();
        }
    }
    
    @Override
    public void onInitAll() {
        initMonkeys();
        if (pirateList.size() != 0) {
            startMonkeyMovement();
        }
    }
    
    @Override
    public void onPirateSubscribed(int port) {
        // Send other pirate positions
        brain.sendOtherPiratePosition(port);
    
        // Send erratic monkey position
        brain.sendErraticMonkeyPosition(port);
    
        // Send hunter monkey position
        brain.sendHunterMonkeyPosition(port);

        if (pirateList.size() == 1) {
            startMonkeyMovement();
        }
    }

    /**
     * method used to start or restart monkey movement
     */
    private void startMonkeyMovement() {
        interruptMoveMonkeyThread = false;
        manageNextMoveThreadMonkeysErratic();
        manageNextMoveThreadMonkeysHunter();
    }
    
    @Override
    public void onPirateDead(Pirate pirate) {
        pirate.setState(Character.State.DEAD);
        if (getNumberOfPirateAlive() == 0) {
            interruptMoveMonkeyThread = true;
            brain.notifyTreasureFound();
        }
    }
    
    @Override
    public void onRumDrunk(Rum rum, int idRumInList) {

    }
    
    @Override
    public void onRumReappear(Rum rum, int idRumInList) {
        Pirate pirate = getPirateOnPosition(rum.getPosition());
        if (pirate != null && pirate.getState() != Character.State.DEAD) {
            pirate.addEnergy(rum.getEnergy());
            this.brain.notifyRumDrunk(rum, idRumInList);
        }
    }
    
    @Override
    public void onTreasureFound(Treasure treasure) {

    }
    
    @Override
    public void onRemovePirate(int port) {
        removePirate(port);
        if (pirateList.size() == 0) {
            interruptMoveMonkeyThread = true;
            this.brain.notifyInitAll();
        }
    }
}