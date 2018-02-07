package com.ddpm.project.monkeyisland.test.controller.test.CharacterManagerTest;

import com.ddpm.project.monkeyisland.communication.Communication;
import com.ddpm.project.monkeyisland.communication.PostmanServer;
import com.ddpm.project.monkeyisland.communication.proxy.ProxyClient;
import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.controller.CharacterManager;
import com.ddpm.project.monkeyisland.controller.MapManager;
import com.ddpm.project.monkeyisland.controller.ObjectManager;
import com.ddpm.project.monkeyisland.model.game.character.Character;
import com.ddpm.project.monkeyisland.utils.LogUtils;
import com.ddpm.project.monkeyisland.model.constant.GameConfig;
import com.ddpm.project.monkeyisland.model.game.attribute.Energy;
import com.ddpm.project.monkeyisland.model.game.character.Monkey;
import com.ddpm.project.monkeyisland.model.game.character.Pirate;
import com.ddpm.project.monkeyisland.model.game.map.Square;
import com.ddpm.project.monkeyisland.model.game.object.Rum;
import com.ddpm.project.monkeyisland.model.game.object.Treasure;
import com.ddpm.project.monkeyisland.model.game.position.Position;
import com.ddpm.project.monkeyisland.utils.PositionUtils;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Random;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Communication.class,PostmanServer.class})

public class MonkeyTest extends TestCase {

    /**
     * pirate's default port
     */
    private static final int INIT_PORT_PIRATE = 8020;

    /**
     * Erratic's possible directions
     */
    private static final int ERRATIC_CHECK_DIR = 4;

    /**
     * int value to checkstyle
     */
    private static final int QUATRE = 4;

    /**
     * int value to checkstyle
     */
    private static final int CINQ = 5;

    /**
     * int value to checkstyle
     */
    private static final int SIX = 6;

    /**
     * bottle value of energy
     */
    private static final int ENERGY_INT = 3;

    /**
     * number of test
     */
    private static final int NUMBER_OF_TEST = 10000;

    /**
     * Number of test non repetitive schema
     */
    private static final int NUMBER_OF_TEST_NON_REPETITIVE_SCHEMA = 100;

    /**
     * Vector non repetitive schema for four direction
     */
    private static final int VECTOR_NON_REPETITIVE_SCHEMA_FOUR = 8;

    /**
     * Vector non repetitive schema for three direction
     */
    private static final int VECTOR_NON_REPETITIVE_SCHEMA_THREE = 10;

    /**
     * Vector non repetitive schema for two direction
     */
    private static final int VECTOR_NON_REPETITIVE_SCHEMA_TWO = 13;

    /**
     * number of monkey for the no cloud move
     */
    private static final int NUMBER_OF_MONKEY_NO_CLOUD_MOVE = 75;

    /**
     * number of test for the no cloud move
     */
    private static final int NUMBER_TEST_NO_CLOUD_MOVE = 10;

    /**
     * Semicolon string
     */
    private static final String SEMICOLON = " ; ";

    /**
     * eventWhenMoveMonkey string
     */
    private static final String EVENT_WHEN_MOVE_MONKEY = "eventWhenMoveMonkey";

    /**
     * setMovingErraticPosition string
     */
    private static final String SET_MOVING_ERRATIC_POSITION = "setMovingErraticPosition";

    /**
     * setMovingHunterPosition string
     */
    private static final String SET_MOVING_HUNTER_POSITION = "setMovingHunterPosition";

    /**
     * string testAskMoveMonkeyErraticProbabilityOfDirection()
     */
    private static final String STRING_DIRECTION = "(UP ; DOWN ; LEFT ; RIGHT)";
    /**
     * Ask move monkey string
     */
    private static final String ASK_MOVE_MONKEYS = "askMoveMonkeys";

    /**
     * probability min move erratic monkey testAskMoveMonkeyErraticProbabilityOfDirectionFour()
     */
    private static final double PROBABILITY_MIN_MV_ERRATIC_MONKEY = 0.23;

    /**
     * probability min move erratic monkey testAskMoveMonkeyErraticProbabilityOfDirectionFour()
     */
    private static final double PROBABILITY_MAX_MV_ERRATIC_MONKEY = 0.27;

    /**
     * probability min move erratic monkey testAskMoveMonkeyErraticProbabilityOfDirectionThree()
     */
    private static final double PROBABILITY_MIN_MV_ERRATIC_MONKEY_THREE = 0.31;

    /**
     * probability min move erratic monkey testAskMoveMonkeyErraticProbabilityOfDirectionThree()
     */
    private static final double PROBABILITY_MAX_MV_ERRATIC_MONKEY_THREE = 0.35;

    /**
     * probability min move erratic monkey testAskMoveMonkeyErraticProbabilityOfDirectionTwo()
     */
    private static final double PROBABILITY_MIN_MV_ERRATIC_MONKEY_TWO = 0.48;

    /**
     * probability min move erratic monkey testAskMoveMonkeyErraticProbabilityOfDirectionTwo()
     */
    private static final double PROBABILITY_MAX_MV_ERRATIC_MONKEY_TWO = 0.52;

    /**
     * Strict equality in probabilities has to be avoided
     */
    private static final double PROBABILITY_HALF_FOUR = 1 / 4f;

    /**
     * Strict equality in probabilities has to be avoided
     */
    private static final double PROBABILITY_HALF_THREE = 1 / 3f;

    /**
     * Strict equality in probabilities has to be avoided
     */
    private static final double PROBABILITY_HALF_TWO = 1 / 2f;

    /**
     * Strict equality in probabilities has to be avoided
     */
    private static final double RATIO_SAME_MOVE_MONKEY_MAX = 0.6f;


    /**
     * @Author : Guillaume Muret
     */
    /**
     * Set up of the this test case
     *
     * @throws Exception : Exception thrown by the process
     */
    @Before
    public void setUp() throws Exception {
        // Init parent data
        Brain brain = Brain.getInstance();

        if (Brain.getInstance().getTimerRelaunchGame() != null) {
            Brain.getInstance().getTimerRelaunchGame().cancel();
        }

        // Mock communication
        Communication communicationMock = PowerMockito.mock(Communication.class);
        PostmanServer postmanServerMock = PowerMockito.mock(PostmanServer.class);
        brain.setCommunication(communicationMock);
        PowerMockito.when(communicationMock.getProxyClient()).thenReturn(ProxyClient.getInstance(postmanServerMock, brain));


        //brain.setCommunication(communicationMock);
        brain.setMapManager(MapManager.getInstance(brain));
        brain.setObjectManager(ObjectManager.getInstance(brain));
        brain.setCharacterManager(CharacterManager.getInstance(brain));

        // Init unused directly data
        brain.notifyInitAll();

        // Init data
        brain.getObjectManager().setRumList(new ArrayList<>());
        brain.getObjectManager().setTreasureList(new ArrayList<>());
        brain.getCharacterManager().setPirateList(new ArrayList<>());
        brain.getCharacterManager().setMonkeyList(new ArrayList<>());
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the move of the erratic monkey
     */
    @Test
    public void testAskMoveMonkeyErraticMove() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());

        Monkey monkeyErraticOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);

        characterManager.getMonkeys().add(monkeyErraticToTest);

        try {
            Method method = characterManager.getClass().
                    getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);

            method.setAccessible(true);
            method.invoke(characterManager, Monkey.MonkeyType.ERRATIC);

            // Test
            Assert.assertNotEquals(monkeyErraticOrigin.getPosition(), monkeyErraticToTest.getPosition());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the move of the erratic monkey surrounded by sea
     */
    @Test
    public void testAskMoveMonkeyErraticAroundSea() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyErraticOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        characterManager.getMonkeys().add(monkeyErraticToTest);

        // Surround monkey of sea
        MapManager.getInstance(Brain.getInstance()).getMap().
                getSquareList().get(GameConfig.getInstance().getMapWidthX() / 2 - 1).
                get(GameConfig.getInstance().getMapHeightY() / 2).setSquareType(Square.SquareType.SEA);
        MapManager.getInstance(Brain.getInstance()).getMap().

                getSquareList().get(GameConfig.getInstance().getMapWidthX() / 2 + 1).
                get(GameConfig.getInstance().getMapHeightY() / 2).setSquareType(Square.SquareType.SEA);
        MapManager.getInstance(Brain.getInstance()).getMap().
                getSquareList().get(GameConfig.getInstance().getMapWidthX() / 2).
                get(GameConfig.getInstance().getMapHeightY() / 2 - 1).setSquareType(Square.SquareType.SEA);
        MapManager.getInstance(Brain.getInstance()).getMap().

                getSquareList().get(GameConfig.getInstance().getMapWidthX() / 2).
                get(GameConfig.getInstance().getMapHeightY() / 2 + 1).setSquareType(Square.SquareType.SEA);

        try {
            Method method = characterManager.
                    getClass().getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
            method.setAccessible(true);
            method.invoke(characterManager, Monkey.MonkeyType.ERRATIC);

            // Test
            Assert.assertTrue(monkeyErraticOrigin.getPosition().isEquals(monkeyErraticToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the move of the erratic monkey surrounded by Erratics monkeys
     */
    @Test
    public void testAskMoveMonkeyErraticAroundErratic() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyErraticOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);

        Monkey monkeyErraticOriginOne = new Monkey(new Position((GameConfig.getInstance().getMapWidthX() / 2) + 1,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticOriginTwo = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) + 1), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticOriginThree = new Monkey(new Position((GameConfig.getInstance().getMapWidthX() / 2) - 1,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticOriginFour = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) - 1), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        characterManager.getMonkeys().add(monkeyErraticToTest);
        characterManager.getMonkeys().add(monkeyErraticOriginOne);
        characterManager.getMonkeys().add(monkeyErraticOriginTwo);
        characterManager.getMonkeys().add(monkeyErraticOriginThree);
        characterManager.getMonkeys().add(monkeyErraticOriginFour);

        try {
            Method method = characterManager.
                    getClass().getDeclaredMethod(SET_MOVING_ERRATIC_POSITION, Monkey.class);
            method.setAccessible(true);
            method.invoke(characterManager, monkeyErraticToTest);

            // Test
            Assert.assertTrue(monkeyErraticOrigin.getPosition().isEquals(monkeyErraticToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the move of the erratic monkey surrounded by Hunter monkeys
     */
    @Test
    public void testAskMoveMonkeyErraticAroundHunter() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyErraticOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);

        Monkey monkeyHunterOriginOne = new Monkey(new Position((GameConfig.getInstance().getMapWidthX() / 2) + 1,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        Monkey monkeyHunterOriginTwo = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) + 1), Monkey.MonkeyType.HUNTER);
        Monkey monkeyHunterOriginThree = new Monkey(new Position((GameConfig.getInstance().getMapWidthX() / 2) - 1,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        Monkey monkeyHunterOriginFour = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) - 1), Monkey.MonkeyType.HUNTER);
        Monkey monkeyErraticToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        characterManager.getMonkeys().add(monkeyErraticToTest);
        characterManager.getMonkeys().add(monkeyHunterOriginOne);
        characterManager.getMonkeys().add(monkeyHunterOriginTwo);
        characterManager.getMonkeys().add(monkeyHunterOriginThree);
        characterManager.getMonkeys().add(monkeyHunterOriginFour);

        try {
            Method method = characterManager.
                    getClass().getDeclaredMethod(SET_MOVING_ERRATIC_POSITION, Monkey.class);
            method.setAccessible(true);
            method.invoke(characterManager, monkeyErraticToTest);

            // Test
            Assert.assertTrue(monkeyErraticOrigin.getPosition().isEquals(monkeyErraticToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the death of the (alive)pirate when erratic monkey walk on it
     */
    @Test
    public void testMoveMonkeyErraticOnAlivePirate() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyErraticOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        characterManager.getMonkeys().add(monkeyErraticToTest);

        //Init pirate list
        characterManager.setPirateList(new ArrayList<>());
        Pirate pirateOne = new Pirate(new Position((GameConfig.getInstance().getMapWidthX() / 2) + 1,
                GameConfig.getInstance().getMapHeightY() / 2), INIT_PORT_PIRATE, new Energy(ENERGY_INT));
        Pirate pirateTwo = new Pirate(new Position((GameConfig.getInstance().getMapWidthX() / 2) - 1,
                GameConfig.getInstance().getMapHeightY() / 2), INIT_PORT_PIRATE + 1, new Energy(ENERGY_INT));
        Pirate pirateThree = new Pirate(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) + 1), INIT_PORT_PIRATE + 2, new Energy(ENERGY_INT));
        Pirate pirateFour = new Pirate(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) - 1), INIT_PORT_PIRATE + 3, new Energy(ENERGY_INT));
        characterManager.getPiratesList().add(pirateOne);
        characterManager.getPiratesList().add(pirateTwo);
        characterManager.getPiratesList().add(pirateThree);
        characterManager.getPiratesList().add(pirateFour);

        try {
            Method askMoveMonkey = characterManager.
                    getClass().getDeclaredMethod(SET_MOVING_ERRATIC_POSITION, Monkey.class);
            askMoveMonkey.setAccessible(true);
            askMoveMonkey.invoke(characterManager, monkeyErraticToTest);

            Method eventWhenMoveMonkey = characterManager.
                    getClass().getDeclaredMethod(EVENT_WHEN_MOVE_MONKEY);
            eventWhenMoveMonkey.setAccessible(true);
            eventWhenMoveMonkey.invoke(characterManager);

            // Test
            Assert.assertFalse(characterManager.getPiratesList().get(0).getState()
                    == characterManager.getPiratesList().get(1).getState()
                    && characterManager.getPiratesList().get(0).getState()
                    == characterManager.getPiratesList().get(2).getState()
                    && characterManager.getPiratesList().get(0).getState()
                    == characterManager.getPiratesList().get(3).getState()
            );
            Assert.assertFalse(monkeyErraticOrigin.getPosition().isEquals(monkeyErraticToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the death of the (dead)pirate when erratic monkey walk on it
     */
    @Test
    public void testMoveMonkeyErraticOnDeadPirate() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyErraticOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        characterManager.getMonkeys().add(monkeyErraticToTest);

        //Init pirate list
        characterManager.setPirateList(new ArrayList<>());
        Pirate pirateOne = new Pirate(new Position((GameConfig.getInstance().getMapWidthX() / 2) + 1,
                GameConfig.getInstance().getMapHeightY() / 2), INIT_PORT_PIRATE, new Energy(0));
        pirateOne.setState(Character.State.DEAD);
        Pirate pirateTwo = new Pirate(new Position((GameConfig.getInstance().getMapWidthX() / 2) - 1,
                GameConfig.getInstance().getMapHeightY() / 2), INIT_PORT_PIRATE + 1, new Energy(0));
        pirateTwo.setState(Character.State.DEAD);
        Pirate pirateThree = new Pirate(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) + 1), INIT_PORT_PIRATE + 2, new Energy(0));
        pirateThree.setState(Character.State.DEAD);
        Pirate pirateFour = new Pirate(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) - 1), INIT_PORT_PIRATE + 3, new Energy(0));
        pirateFour.setState(Character.State.DEAD);
        characterManager.getPiratesList().add(pirateOne);
        characterManager.getPiratesList().add(pirateTwo);
        characterManager.getPiratesList().add(pirateThree);
        characterManager.getPiratesList().add(pirateFour);

        try {
            Method askMoveMonkey = characterManager.
                    getClass().getDeclaredMethod(SET_MOVING_ERRATIC_POSITION, Monkey.class);
            askMoveMonkey.setAccessible(true);
            askMoveMonkey.invoke(characterManager, monkeyErraticToTest);

            Method eventWhenMoveMonkey = characterManager.
                    getClass().getDeclaredMethod(EVENT_WHEN_MOVE_MONKEY);
            eventWhenMoveMonkey.setAccessible(true);
            eventWhenMoveMonkey.invoke(characterManager);

            // Test
            Assert.assertTrue(characterManager.getPiratesList().get(0).getState()
                    == characterManager.getPiratesList().get(1).getState()
                    && characterManager.getPiratesList().get(0).getState()
                    == characterManager.getPiratesList().get(2).getState()
                    && characterManager.getPiratesList().get(0).getState()
                    == characterManager.getPiratesList().get(3).getState()
            );
            Assert.assertFalse(monkeyErraticOrigin.getPosition().isEquals(monkeyErraticToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the move of the erratic monkey surrounded by treasure
     */
    @Test
    public void testAskMoveMonkeyErraticTreasure() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyErraticOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        characterManager.getMonkeys().add(monkeyErraticToTest);

        // Surround monkey of treasure
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                        GameConfig.getInstance().getMapWidthX() / 2), false));
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2 - 1,
                        GameConfig.getInstance().getMapWidthX() / 2), false));
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                        GameConfig.getInstance().getMapWidthX() / 2 + 1), false));
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                        GameConfig.getInstance().getMapWidthX() / 2 - 1), false));

        try {
            Method method = characterManager.
                    getClass().getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
            method.setAccessible(true);
            method.invoke(characterManager, Monkey.MonkeyType.ERRATIC);

            // Test
            LogUtils.test("monkeyErraticOrigin.getState() ==> " + monkeyErraticOrigin.getState().toString());
            LogUtils.test("monkeyErraticOrigin.getPosition() ==> " + monkeyErraticOrigin.getPosition().toString());
            LogUtils.test("monkeyErraticToTest.getPosition() ==> " + monkeyErraticToTest.getPosition().toString());
            Assert.assertFalse(monkeyErraticOrigin.getPosition().isEquals(monkeyErraticToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the move of the erratic monkey on treasure preserve its visibility
     */
    @Test
    public void testMoveMonkeyErraticTreasureVisibility() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyErraticOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        characterManager.getMonkeys().add(monkeyErraticToTest);

        // Surround monkey of treasures
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                        GameConfig.getInstance().getMapWidthX() / 2), false));
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2 - 1,
                        GameConfig.getInstance().getMapWidthX() / 2), false));
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                        GameConfig.getInstance().getMapWidthX() / 2 + 1), false));
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                        GameConfig.getInstance().getMapWidthX() / 2 - 1), false));

        try {
            Method method = characterManager.getClass().
                    getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
            method.setAccessible(true);
            method.invoke(characterManager, Monkey.MonkeyType.ERRATIC);

            // Test
            for (int i = 0; i < ObjectManager.getInstance(Brain.getInstance()).getTreasureList().size(); i++) {
                Assert.assertFalse(ObjectManager.getInstance(Brain.getInstance()).getTreasureList().get(i).isVisible());
            }
            Assert.assertFalse(monkeyErraticOrigin.getPosition().isEquals(monkeyErraticToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the move of the erratic monkey surrounded by Rum
     */
    @Test
    public void testMonkeyErraticRumStillAvailable() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyErraticToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        characterManager.getMonkeys().add(monkeyErraticToTest);

        // Surround monkey of rum
        ObjectManager.getInstance(Brain.getInstance()).getRumList().
                add(new Rum(new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                        GameConfig.getInstance().getMapWidthX() / 2), true, ENERGY_INT));
        ObjectManager.getInstance(Brain.getInstance()).getRumList().
                add(new Rum(new Position(GameConfig.getInstance().getMapWidthX() / 2 - 1,
                        GameConfig.getInstance().getMapWidthX() / 2), true, ENERGY_INT));
        ObjectManager.getInstance(Brain.getInstance()).getRumList().
                add(new Rum(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                        GameConfig.getInstance().getMapWidthX() / 2 + 1), true, ENERGY_INT));
        ObjectManager.getInstance(Brain.getInstance()).getRumList().
                add(new Rum(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                        GameConfig.getInstance().getMapWidthX() / 2 - 1), true, ENERGY_INT));

        try {
            Method method = characterManager.getClass().
                    getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
            method.setAccessible(true);
            method.invoke(characterManager, Monkey.MonkeyType.ERRATIC);

            // Test
            for (int i = 0; i < ObjectManager.getInstance(Brain.getInstance()).getRumList().size(); i++) {
                Assert.assertTrue(ObjectManager.getInstance(Brain.getInstance()).getRumList().get(i).isVisible());
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the monkey movement probability of direction Two
     */
    @Test
    public void testAskMoveMonkeyErraticProbabilityOfDirectionTwo() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();
        Random rand = new Random();
        int xAdd1;
        int yAdd1;
        int xAdd2;
        int yAdd2;
        int sea = rand.nextInt(CINQ) + 1;
        int up = 0;
        int down = up;
        int left = up;
        int right = up;
        switch (sea) {
            case 1:
                xAdd1 = 0;
                yAdd1 = 1;
                xAdd2 = 1;
                yAdd2 = 0;
                up = sea;
                right = sea;
                break;
            case 2:
                xAdd1 = 0;
                yAdd1 = 1;
                xAdd2 = 0;
                yAdd2 = -1;
                up = sea;
                down = sea;
                break;
            case 3:
                xAdd1 = 0;
                yAdd1 = 1;
                xAdd2 = -1;
                yAdd2 = 0;
                up = sea;
                left = sea;
                break;
            case QUATRE:
                xAdd1 = 1;
                yAdd1 = 0;
                xAdd2 = 0;
                yAdd2 = -1;
                right = sea;
                down = sea;
                break;
            case CINQ:
                xAdd1 = 1;
                yAdd1 = 0;
                xAdd2 = -1;
                yAdd2 = 0;
                right = sea;
                left = sea;
                break;
            case SIX:
                xAdd1 = 0;
                yAdd1 = -1;
                xAdd2 = -1;
                yAdd2 = 0;
                down = sea;
                left = sea;
                break;
            default:
                xAdd1 = 1;
                yAdd1 = 0;
                xAdd2 = 1;
                yAdd2 = 0;
                up = sea;
                right = sea;
        }
        // get test data
        int numberUpDirection = 0;
        int numberDownDirection = 0;
        int numberLeftDirection = 0;
        int numberRightDirection = 0;

        // Prepare the monkey for test
        Monkey monkeyErraticOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        characterManager.getMonkeys().add(monkeyErraticToTest);
        MapManager.getInstance(Brain.getInstance()).getMap().
                getSquareList().get(GameConfig.getInstance().getMapWidthX() / 2 + xAdd1).
                get(GameConfig.getInstance().getMapHeightY() / 2 + yAdd1).setSquareType(Square.SquareType.SEA);
        MapManager.getInstance(Brain.getInstance()).getMap().
                getSquareList().get(GameConfig.getInstance().getMapWidthX() / 2 + xAdd2).
                get(GameConfig.getInstance().getMapHeightY() / 2 + yAdd2).setSquareType(Square.SquareType.SEA);

        try {
            for (int i = 0; i < NUMBER_OF_TEST; i++) {
                characterManager.getMonkeys().get(0).setPosition(new Position(monkeyErraticOrigin.getPosition()));
                Method method = characterManager.
                        getClass().getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
                method.setAccessible(true);
                method.invoke(characterManager, Monkey.MonkeyType.ERRATIC);

                if (characterManager.getMonkeys().get(0).getPosition().getPositionY()
                        == monkeyErraticOrigin.getPosition().getPositionY() + 1) {
                    numberUpDirection++;
                }
                if (characterManager.getMonkeys().get(0).getPosition().getPositionY()
                        == monkeyErraticOrigin.getPosition().getPositionY() - 1) {
                    numberDownDirection++;
                }
                if (characterManager.getMonkeys().get(0).getPosition().getPositionX()
                        == monkeyErraticOrigin.getPosition().getPositionX() - 1) {
                    numberLeftDirection++;
                }
                if (characterManager.getMonkeys().get(0).getPosition().getPositionX()
                        == monkeyErraticOrigin.getPosition().getPositionX() + 1) {
                    numberRightDirection++;
                }
            }
            // Test all movement has been made
            Assert.assertEquals(NUMBER_OF_TEST, numberUpDirection + numberDownDirection
                    + numberLeftDirection + numberRightDirection);

            LogUtils.test(STRING_DIRECTION + "=(" + numberUpDirection + SEMICOLON + numberDownDirection + SEMICOLON
                    + numberLeftDirection + SEMICOLON + numberRightDirection + ")");

            LogUtils.test(STRING_DIRECTION + "  = ("
                    + ((float) numberUpDirection / (float) NUMBER_OF_TEST) + SEMICOLON
                    + ((float) numberDownDirection / (float) NUMBER_OF_TEST) + SEMICOLON
                    + ((float) numberLeftDirection / (float) NUMBER_OF_TEST) + SEMICOLON
                    + ((float) numberRightDirection / (float) NUMBER_OF_TEST) + ") ");

            // Test same probability
            if (sea != up) {
                Assert.assertTrue(((float) numberUpDirection
                        / (float) NUMBER_OF_TEST) >= PROBABILITY_MIN_MV_ERRATIC_MONKEY_THREE
                        && ((float) numberUpDirection / (float) NUMBER_OF_TEST) <= PROBABILITY_MAX_MV_ERRATIC_MONKEY_TWO);
            }
            if (sea != down) {
                Assert.assertTrue(((float) numberDownDirection
                        / (float) NUMBER_OF_TEST) >= PROBABILITY_MIN_MV_ERRATIC_MONKEY_TWO
                        && ((float) numberDownDirection / (float) NUMBER_OF_TEST) <= PROBABILITY_MAX_MV_ERRATIC_MONKEY_TWO);
            }
            if (sea != left) {
                Assert.assertTrue(((float) numberLeftDirection / (float) NUMBER_OF_TEST)
                        >= PROBABILITY_MIN_MV_ERRATIC_MONKEY_TWO && ((float) numberLeftDirection / (float) NUMBER_OF_TEST)
                        <= PROBABILITY_MAX_MV_ERRATIC_MONKEY_TWO);
            }
            if (sea != right) {
                Assert.assertTrue(((float) numberRightDirection
                        / (float) NUMBER_OF_TEST) >= PROBABILITY_MIN_MV_ERRATIC_MONKEY_TWO
                        && ((float) numberRightDirection / (float) NUMBER_OF_TEST) <= PROBABILITY_MAX_MV_ERRATIC_MONKEY_TWO);
            }
            Assert.assertTrue(PROBABILITY_HALF_TWO != ((float) numberRightDirection / (float) NUMBER_OF_TEST));
            Assert.assertTrue(PROBABILITY_HALF_TWO != ((float) numberLeftDirection / (float) NUMBER_OF_TEST));
            Assert.assertTrue(PROBABILITY_HALF_TWO != ((float) numberUpDirection / (float) NUMBER_OF_TEST));
            Assert.assertTrue(PROBABILITY_HALF_TWO != ((float) numberDownDirection / (float) NUMBER_OF_TEST));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the monkey movement probability of direction Three
     */
    @Test
    public void testAskMoveMonkeyErraticProbabilityOfDirectionThree() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();
        Random rand = new Random();
        int xAdd;
        int yAdd;
        int sea = rand.nextInt(3);
        int right = 0;
        int left = 1;
        int up = 2;
        int down = 3;
        switch (sea) {
            case 0:
                xAdd = 1;
                yAdd = 0;
                break;
            case 1:
                xAdd = -1;
                yAdd = 0;
                break;
            case 2:
                xAdd = 0;
                yAdd = 1;
                break;
            case 3:
                xAdd = 0;
                yAdd = -1;
                break;
            default:
                xAdd = 1;
                yAdd = 0;
        }
        // get test data
        int numberUpDirection = 0;
        int numberDownDirection = 0;
        int numberLeftDirection = 0;
        int numberRightDirection = 0;

        // Prepare the monkey for test
        Monkey monkeyErraticOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        characterManager.getMonkeys().add(monkeyErraticToTest);
        MapManager.getInstance(Brain.getInstance()).getMap().
                getSquareList().get(GameConfig.getInstance().getMapWidthX() / 2 + xAdd).
                get(GameConfig.getInstance().getMapHeightY() / 2 + yAdd).setSquareType(Square.SquareType.SEA);

        try {
            for (int i = 0; i < NUMBER_OF_TEST; i++) {
                characterManager.getMonkeys().get(0).setPosition(new Position(monkeyErraticOrigin.getPosition()));
                Method method = characterManager.
                        getClass().getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
                method.setAccessible(true);
                method.invoke(characterManager, Monkey.MonkeyType.ERRATIC);

                if (characterManager.getMonkeys().get(0).getPosition().getPositionY()
                        == monkeyErraticOrigin.getPosition().getPositionY() + 1) {
                    numberUpDirection++;
                }
                if (characterManager.getMonkeys().get(0).getPosition().getPositionY()
                        == monkeyErraticOrigin.getPosition().getPositionY() - 1) {
                    numberDownDirection++;
                }
                if (characterManager.getMonkeys().get(0).getPosition().getPositionX()
                        == monkeyErraticOrigin.getPosition().getPositionX() - 1) {
                    numberLeftDirection++;
                }
                if (characterManager.getMonkeys().get(0).getPosition().getPositionX()
                        == monkeyErraticOrigin.getPosition().getPositionX() + 1) {
                    numberRightDirection++;
                }
            }
            // Test all movement has been made
            Assert.assertEquals(NUMBER_OF_TEST, numberUpDirection + numberDownDirection
                    + numberLeftDirection + numberRightDirection);

            LogUtils.test(STRING_DIRECTION + " =(" + numberUpDirection + SEMICOLON + numberDownDirection + SEMICOLON
                    + numberLeftDirection + SEMICOLON + numberRightDirection + " )");

            LogUtils.test(STRING_DIRECTION + " = ( "
                    + ((float) numberUpDirection / (float) NUMBER_OF_TEST) + SEMICOLON
                    + ((float) numberDownDirection / (float) NUMBER_OF_TEST) + SEMICOLON
                    + ((float) numberLeftDirection / (float) NUMBER_OF_TEST) + SEMICOLON
                    + ((float) numberRightDirection / (float) NUMBER_OF_TEST) + " ) ");

            // Test same probability
            if (sea != up) {
                Assert.assertTrue(((float) numberUpDirection
                        / (float) NUMBER_OF_TEST) >= PROBABILITY_MIN_MV_ERRATIC_MONKEY_THREE
                        && ((float) numberUpDirection / (float) NUMBER_OF_TEST)
                        <= PROBABILITY_MAX_MV_ERRATIC_MONKEY_THREE);
            }
            if (sea != down) {
                Assert.assertTrue(((float) numberDownDirection
                        / (float) NUMBER_OF_TEST) >= PROBABILITY_MIN_MV_ERRATIC_MONKEY_THREE
                        && ((float) numberDownDirection / (float) NUMBER_OF_TEST)
                        <= PROBABILITY_MAX_MV_ERRATIC_MONKEY_THREE);
            }
            if (sea != left) {
                Assert.assertTrue(((float) numberLeftDirection / (float) NUMBER_OF_TEST)
                        >= PROBABILITY_MIN_MV_ERRATIC_MONKEY_THREE
                        && ((float) numberLeftDirection / (float) NUMBER_OF_TEST)
                        <= PROBABILITY_MAX_MV_ERRATIC_MONKEY_THREE);
            }
            if (sea != right) {
                Assert.assertTrue(((float) numberRightDirection
                        / (float) NUMBER_OF_TEST) >= PROBABILITY_MIN_MV_ERRATIC_MONKEY_THREE
                        && ((float) numberRightDirection / (float) NUMBER_OF_TEST)
                        <= PROBABILITY_MAX_MV_ERRATIC_MONKEY_THREE);
            }
            Assert.assertTrue(PROBABILITY_HALF_THREE != ((float) numberRightDirection / (float) NUMBER_OF_TEST));
            Assert.assertTrue(PROBABILITY_HALF_THREE != ((float) numberLeftDirection / (float) NUMBER_OF_TEST));
            Assert.assertTrue(PROBABILITY_HALF_THREE != ((float) numberUpDirection / (float) NUMBER_OF_TEST));
            Assert.assertTrue(PROBABILITY_HALF_THREE != ((float) numberDownDirection / (float) NUMBER_OF_TEST));

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Guillaume Muret
     * @Collab :
     */
    /**
     * Test the monkey movement probability of direction Four
     */
    @Test
    public void testAskMoveMonkeyErraticProbabilityOfDirectionFour() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // get test data
        int numberUpDirection = 0;
        int numberDownDirection = 0;
        int numberLeftDirection = 0;
        int numberRightDirection = 0;

        // Prepare the monkey for test
        Monkey monkeyErraticOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        characterManager.getMonkeys().add(monkeyErraticToTest);

        try {
            for (int i = 0; i < NUMBER_OF_TEST; i++) {
                characterManager.getMonkeys().get(0).setPosition(new Position(monkeyErraticOrigin.getPosition()));

                Method method = characterManager.
                        getClass().getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
                method.setAccessible(true);
                method.invoke(characterManager, Monkey.MonkeyType.ERRATIC);

                CharacterManager.Direction direction = getDirectionMove(
                        monkeyErraticOrigin.getPosition(), characterManager.getMonkeys().get(0).getPosition());

                if (direction.equals(CharacterManager.Direction.UP)) {
                    numberUpDirection++;
                }
                if (direction.equals(CharacterManager.Direction.DOWN)) {
                    numberDownDirection++;
                }
                if (direction.equals(CharacterManager.Direction.LEFT)) {
                    numberLeftDirection++;
                }
                if (direction.equals(CharacterManager.Direction.RIGHT)) {
                    numberRightDirection++;
                }
            }

            // Test all movement has been made
            Assert.assertEquals(NUMBER_OF_TEST, numberUpDirection + numberDownDirection
                    + numberLeftDirection + numberRightDirection);

            LogUtils.test(STRING_DIRECTION + " = (" + numberUpDirection + SEMICOLON + numberDownDirection + SEMICOLON
                    + numberLeftDirection + SEMICOLON + numberRightDirection + "  )");

            LogUtils.test(STRING_DIRECTION + "= ("
                    + ((float) numberUpDirection / (float) NUMBER_OF_TEST) + SEMICOLON
                    + ((float) numberDownDirection / (float) NUMBER_OF_TEST) + SEMICOLON
                    + ((float) numberLeftDirection / (float) NUMBER_OF_TEST) + SEMICOLON
                    + ((float) numberRightDirection / (float) NUMBER_OF_TEST) + ")  ");

            // Test same probability
            Assert.assertTrue(((float) numberUpDirection / (float) NUMBER_OF_TEST) >= PROBABILITY_MIN_MV_ERRATIC_MONKEY
                    && ((float) numberUpDirection / (float) NUMBER_OF_TEST) <= PROBABILITY_MAX_MV_ERRATIC_MONKEY);
            Assert.assertTrue(((float) numberDownDirection / (float) NUMBER_OF_TEST) >= PROBABILITY_MIN_MV_ERRATIC_MONKEY
                    && ((float) numberDownDirection / (float) NUMBER_OF_TEST) <= PROBABILITY_MAX_MV_ERRATIC_MONKEY);
            Assert.assertTrue(((float) numberLeftDirection / (float) NUMBER_OF_TEST) >= PROBABILITY_MIN_MV_ERRATIC_MONKEY
                    && ((float) numberLeftDirection / (float) NUMBER_OF_TEST) <= PROBABILITY_MAX_MV_ERRATIC_MONKEY);
            Assert.assertTrue(((float) numberRightDirection / (float) NUMBER_OF_TEST) >= PROBABILITY_MIN_MV_ERRATIC_MONKEY
                    && ((float) numberRightDirection / (float) NUMBER_OF_TEST) <= PROBABILITY_MAX_MV_ERRATIC_MONKEY);

            Assert.assertTrue(PROBABILITY_HALF_FOUR != ((float) numberRightDirection / (float) NUMBER_OF_TEST)
                    && PROBABILITY_HALF_FOUR != ((float) numberLeftDirection / (float) NUMBER_OF_TEST)
                    && PROBABILITY_HALF_FOUR != ((float) numberUpDirection / (float) NUMBER_OF_TEST)
                    && PROBABILITY_HALF_FOUR != ((float) numberDownDirection / (float) NUMBER_OF_TEST));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Guillaume Muret
     * @Collab :
     */
    /**
     * Test of non repetitive schema for the four direction of the monkey erratics movement
     */
    @Test
    public void testAskMoveMonkeyErraticNonRepetitiveSchemaFour() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        LinkedList<CharacterManager.Direction> directionList = new LinkedList<>();

        // Prepare the monkey for test
        Monkey monkeyErraticOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        characterManager.getMonkeys().add(monkeyErraticToTest);

        try {
            for (int i = 0; i < NUMBER_OF_TEST_NON_REPETITIVE_SCHEMA; i++) {
                characterManager.getMonkeys().get(0).setPosition(new Position(monkeyErraticOrigin.getPosition()));

                Method method = characterManager.
                        getClass().getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
                method.setAccessible(true);
                method.invoke(characterManager, Monkey.MonkeyType.ERRATIC);

                CharacterManager.Direction direction = getDirectionMove(
                        monkeyErraticOrigin.getPosition(), characterManager.getMonkeys().get(0).getPosition());

                directionList.add(direction);
            }

            int vectorLength = VECTOR_NON_REPETITIVE_SCHEMA_FOUR;

            // TEST SCHEMA NON REPETITIF
            for (int i = 0; i < directionList.size() - vectorLength; i++) {
                ArrayList<CharacterManager.Direction> directionVectorOrigin = new ArrayList<>();
                for (int j = i; j < i + vectorLength; j++) {
                    directionVectorOrigin.add(directionList.get(j));
                }
                for (int j = 0; j < (directionList.size() - vectorLength); j++) {
                    if (j != i) {
                        boolean sameSerie = true;
                        ArrayList<CharacterManager.Direction> directionVectorToTest = new ArrayList<>();
                        for (int k = j; k < j + vectorLength; k++) {
                            directionVectorToTest.add(directionList.get(k));
                        }

                        for (int k = 0; k < vectorLength; k++) {
                            sameSerie &= directionVectorOrigin.get(k) == directionVectorToTest.get(k);
                        }
                        //LogUtils.test(" i = " + i + " j = " + j + " sameSerie = " + sameSerie);
                        Assert.assertFalse(sameSerie);
                    }
                }
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Guillaume Muret
     * @Collab :
     */
    /**
     * Test of non repetitive schema for the three direction of the monkey erratics movement
     */
    @Test
    public void testAskMoveMonkeyErraticNonRepetitiveSchemaThree() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();
        Random rand = new Random();
        int xAdd;
        int yAdd;
        int sea = rand.nextInt(3);
        switch (sea) {
            case 0:
                xAdd = 1;
                yAdd = 0;
                break;
            case 1:
                xAdd = -1;
                yAdd = 0;
                break;
            case 2:
                xAdd = 0;
                yAdd = 1;
                break;
            case 3:
                xAdd = 0;
                yAdd = -1;
                break;
            default:
                xAdd = 1;
                yAdd = 0;
        }


        LinkedList<CharacterManager.Direction> directionList = new LinkedList<>();

        // Prepare the monkey for test
        Monkey monkeyErraticOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        characterManager.getMonkeys().add(monkeyErraticToTest);
        MapManager.getInstance(Brain.getInstance()).getMap().
                getSquareList().get(GameConfig.getInstance().getMapWidthX() / 2 + xAdd).
                get(GameConfig.getInstance().getMapHeightY() / 2 + yAdd).setSquareType(Square.SquareType.SEA);

        try {
            for (int i = 0; i < NUMBER_OF_TEST_NON_REPETITIVE_SCHEMA; i++) {
                characterManager.getMonkeys().get(0).setPosition(new Position(monkeyErraticOrigin.getPosition()));

                Method method = characterManager.
                        getClass().getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
                method.setAccessible(true);
                method.invoke(characterManager, Monkey.MonkeyType.ERRATIC);

                CharacterManager.Direction direction = getDirectionMove(
                        monkeyErraticOrigin.getPosition(), characterManager.getMonkeys().get(0).getPosition());

                directionList.add(direction);
            }

            int vectorLength = VECTOR_NON_REPETITIVE_SCHEMA_THREE;

            // TEST SCHEMA NON REPETITIF
            for (int i = 0; i < directionList.size() - vectorLength; i++) {
                ArrayList<CharacterManager.Direction> directionVectorOrigin = new ArrayList<>();
                for (int j = i; j < i + vectorLength; j++) {
                    directionVectorOrigin.add(directionList.get(j));
                }
                for (int j = 0; j < (directionList.size() - vectorLength); j++) {
                    if (j != i) {
                        boolean sameSerie = true;
                        ArrayList<CharacterManager.Direction> directionVectorToTest = new ArrayList<>();
                        for (int k = j; k < j + vectorLength; k++) {
                            directionVectorToTest.add(directionList.get(k));
                        }

                        for (int k = 0; k < vectorLength; k++) {
                            sameSerie &= directionVectorOrigin.get(k) == directionVectorToTest.get(k);
                        }
                        //LogUtils.test(" i = " + i + " j = " + j + " sameSerie = " + sameSerie);
                        Assert.assertFalse(sameSerie);
                    }
                }
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Guillaume Muret
     * @Collab :
     */
    /**
     * Test of non repetitive schema for the two direction of the monkey erratics movement
     */
    @Test
    public void testAskMoveMonkeyErraticNonRepetitiveSchemaTwo() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();
        Random rand = new Random();
        int xAdd1;
        int yAdd1;
        int xAdd2;
        int yAdd2;
        int sea = rand.nextInt(CINQ) + 1;
        switch (sea) {
            case 1:
                xAdd1 = 0;
                yAdd1 = 1;
                xAdd2 = 1;
                yAdd2 = 0;
                break;
            case 2:
                xAdd1 = 0;
                yAdd1 = 1;
                xAdd2 = 0;
                yAdd2 = -1;
                break;
            case 3:
                xAdd1 = 0;
                yAdd1 = 1;
                xAdd2 = -1;
                yAdd2 = 0;
                break;
            case QUATRE:
                xAdd1 = 1;
                yAdd1 = 0;
                xAdd2 = 0;
                yAdd2 = -1;
                break;
            case CINQ:
                xAdd1 = 1;
                yAdd1 = 0;
                xAdd2 = -1;
                yAdd2 = 0;
                break;
            case SIX:
                xAdd1 = 0;
                yAdd1 = -1;
                xAdd2 = -1;
                yAdd2 = 0;
                break;
            default:
                xAdd1 = 1;
                yAdd1 = 0;
                xAdd2 = 1;
                yAdd2 = 0;
        }
        // get test data
        LinkedList<CharacterManager.Direction> directionList = new LinkedList<>();

        // Prepare the monkey for test
        Monkey monkeyErraticOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        characterManager.getMonkeys().add(monkeyErraticToTest);
        MapManager.getInstance(Brain.getInstance()).getMap().
                getSquareList().get(GameConfig.getInstance().getMapWidthX() / 2 + xAdd1).
                get(GameConfig.getInstance().getMapHeightY() / 2 + yAdd1).setSquareType(Square.SquareType.SEA);
        MapManager.getInstance(Brain.getInstance()).getMap().
                getSquareList().get(GameConfig.getInstance().getMapWidthX() / 2 + xAdd2).
                get(GameConfig.getInstance().getMapHeightY() / 2 + yAdd2).setSquareType(Square.SquareType.SEA);

        try {
            for (int i = 0; i < NUMBER_OF_TEST_NON_REPETITIVE_SCHEMA; i++) {
                characterManager.getMonkeys().get(0).setPosition(new Position(monkeyErraticOrigin.getPosition()));

                Method method = characterManager.
                        getClass().getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
                method.setAccessible(true);
                method.invoke(characterManager, Monkey.MonkeyType.ERRATIC);

                CharacterManager.Direction direction = getDirectionMove(
                        monkeyErraticOrigin.getPosition(), characterManager.getMonkeys().get(0).getPosition());

                directionList.add(direction);
            }

            int vectorLength = VECTOR_NON_REPETITIVE_SCHEMA_TWO;

            // TEST SCHEMA NON REPETITIF
            for (int i = 0; i < directionList.size() - vectorLength; i++) {
                ArrayList<CharacterManager.Direction> directionVectorOrigin = new ArrayList<>();
                for (int j = i; j < i + vectorLength; j++) {
                    directionVectorOrigin.add(directionList.get(j));
                }
                for (int j = 0; j < (directionList.size() - vectorLength); j++) {
                    if (j != i) {
                        boolean sameSerie = true;
                        ArrayList<CharacterManager.Direction> directionVectorToTest = new ArrayList<>();
                        for (int k = j; k < j + vectorLength; k++) {
                            directionVectorToTest.add(directionList.get(k));
                        }

                        for (int k = 0; k < vectorLength; k++) {
                            sameSerie &= directionVectorOrigin.get(k) == directionVectorToTest.get(k);
                        }
                        //LogUtils.test(" i = " + i + " j = " + j + " sameSerie = " + sameSerie);
                        Assert.assertFalse(sameSerie);
                    }
                }
            }

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Guillaume Muret
     * @Collab :
     */
    /**
     * Test that the monkey erratic team move not like a cloud.
     * We test the number of same movement / number of erratic monkey and this ratio have
     * to be less or equal than 0.6
     */
    @Test
    public void testAskMoveMonkeyErraticNoCloudMove() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init data for test
        int sameMoveProbability = 0;
        float ratioSameMoveNumberMonkey = 0;
        ArrayList<Monkey> erraticMonkeyOrigin = new ArrayList<>();
        ArrayList<Monkey> erraticMonkeyToTest = new ArrayList<>();

        // Init monkeys position
        for (int i = 0; i < NUMBER_OF_MONKEY_NO_CLOUD_MOVE; i++) {
            Monkey monkeyErraticOrigin = new Monkey(PositionUtils.getSpawnPosition(
                    Brain.getInstance().getMapManager().getMap().getGroundSquareList(),
                    Brain.getInstance().getCharacterManager().getCharacterList(),
                    Brain.getInstance().getObjectManager().getObjectList()),
                    Monkey.MonkeyType.ERRATIC
            );
            Monkey monkeyErraticToTest = new Monkey(monkeyErraticOrigin.getPosition(),
                    Monkey.MonkeyType.ERRATIC
            );
            erraticMonkeyOrigin.add(monkeyErraticOrigin);
            erraticMonkeyToTest.add(monkeyErraticToTest);
            characterManager.getMonkeys().add(monkeyErraticToTest);
        }

        try {
            for (int i = 0; i < NUMBER_TEST_NO_CLOUD_MOVE; i++) {
                // Invoke method askMoveMonkeys
                Method method = characterManager.getClass().getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
                method.setAccessible(true);
                method.invoke(characterManager, Monkey.MonkeyType.ERRATIC);

                for (int j = 0; j < NUMBER_OF_MONKEY_NO_CLOUD_MOVE; j++) {
                    // Init the same move probability
                    sameMoveProbability = 0;
                    CharacterManager.Direction directionMonkey = getDirectionMove(
                            erraticMonkeyOrigin.get(j).getPosition(),
                            erraticMonkeyToTest.get(j).getPosition()
                    );

                    for (int k = 0; k < NUMBER_OF_MONKEY_NO_CLOUD_MOVE; k++) {
                        if (k == j) {
                            k++;
                        } else {
                            CharacterManager.Direction directionOtherMonkey = getDirectionMove(
                                    erraticMonkeyOrigin.get(k).getPosition(),
                                    erraticMonkeyToTest.get(k).getPosition()
                            );
                            if (directionMonkey == directionOtherMonkey) {
                                sameMoveProbability++;
                            }
                        }
                    }
                    ratioSameMoveNumberMonkey = (float) sameMoveProbability / NUMBER_OF_MONKEY_NO_CLOUD_MOVE;
                    LogUtils.test("ratioSameMoveNumberMonkey ==> " + ratioSameMoveNumberMonkey);
                    assertTrue(ratioSameMoveNumberMonkey <= RATIO_SAME_MOVE_MONKEY_MAX);
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Guillaume Muret
     * @Collab :
     */
    /**
     * Process called to get the direction enum of the movement
     * @param positionOrigin : the origin position
     * @param positionMove : the next position
     * @return the direction enum
     */
    private CharacterManager.Direction getDirectionMove(Position positionOrigin, Position positionMove) {
        CharacterManager.Direction direction = null;
        int diffPosX = positionOrigin.getPositionX() - positionMove.getPositionX();
        int diffPosY = positionOrigin.getPositionY() - positionMove.getPositionY();
        if (diffPosX == 1) {
            direction = CharacterManager.Direction.LEFT;
        }
        if (diffPosX == -1) {
            direction = CharacterManager.Direction.RIGHT;
        }
        if (diffPosY == 1) {
            direction = CharacterManager.Direction.DOWN;
        }
        if (diffPosY == -1) {
            direction = CharacterManager.Direction.UP;
        }
        if (direction == null) {
            direction = CharacterManager.Direction.NONE;
        }
        return direction;
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the move of the hunter monkey
     */
    @Test
    public void testAskMoveMonkeyHunterMove() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());

        Monkey monkeyHunterOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);

        Monkey monkeyHunterToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        characterManager.getMonkeys().add(monkeyHunterToTest);

        try {
            Method method = characterManager.getClass().
                    getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);

            method.setAccessible(true);
            method.invoke(characterManager, Monkey.MonkeyType.HUNTER);

            // Test
            Assert.assertNotEquals(monkeyHunterOrigin.getPosition(), monkeyHunterToTest.getPosition());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the move of the hunter monkey surrounded by sea
     */
    @Test
    public void testAskMoveMonkeyHunterAroundSea() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());

        Monkey monkeyHunterOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        Monkey monkeyHunterToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);

        characterManager.getMonkeys().add(monkeyHunterToTest);

        // Surround monkey of sea
        MapManager.getInstance(Brain.getInstance()).getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.SEA);
        MapManager.getInstance(Brain.getInstance()).getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.SEA);
        MapManager.getInstance(Brain.getInstance()).getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.SEA);
        MapManager.getInstance(Brain.getInstance()).getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.SEA);

        try {
            Method method = characterManager.getClass().
                    getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
            method.setAccessible(true);
            method.invoke(characterManager, Monkey.MonkeyType.HUNTER);

            // Test
            Assert.assertTrue(monkeyHunterOrigin.getPosition().isEquals(monkeyHunterToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the move of the hunter monkey surrounded by treasure
     */
    @Test
    public void testAskMoveMonkeyHunterTreasure() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init object
        ObjectManager.getInstance(Brain.getInstance()).setTreasureList(new ArrayList<>());
        ObjectManager.getInstance(Brain.getInstance()).setRumList(new ArrayList<>());


        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyHunterOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        Monkey monkeyHunterToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        characterManager.getMonkeys().add(monkeyHunterToTest);

        // Surround monkey of treasure
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                        GameConfig.getInstance().getMapWidthX() / 2), false));
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2 - 1,
                        GameConfig.getInstance().getMapWidthX() / 2), false));
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                        GameConfig.getInstance().getMapWidthX() / 2 + 1), false));
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                        GameConfig.getInstance().getMapWidthX() / 2 - 1), false));

        try {
            Method method = characterManager.getClass().
                    getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
            method.setAccessible(true);
            method.invoke(characterManager, Monkey.MonkeyType.HUNTER);

            // Test
            Assert.assertFalse(monkeyHunterOrigin.getPosition().isEquals(monkeyHunterToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the move of the hunter monkey surrounded by Rum
     */
    @Test
    public void testAskMoveMonkeyHunterRum() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init object
        ObjectManager.getInstance(Brain.getInstance()).setTreasureList(new ArrayList<>());
        ObjectManager.getInstance(Brain.getInstance()).setRumList(new ArrayList<>());

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyHunterOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        Monkey monkeyHunterToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        characterManager.getMonkeys().add(monkeyHunterToTest);

        // Surround monkey of rum
        ObjectManager.getInstance(Brain.getInstance()).getRumList().
                add(new Rum(new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                        GameConfig.getInstance().getMapWidthX() / 2), false, ENERGY_INT));
        ObjectManager.getInstance(Brain.getInstance()).getRumList().
                add(new Rum(new Position(GameConfig.getInstance().getMapWidthX() / 2 - 1,
                        GameConfig.getInstance().getMapWidthX() / 2), false, ENERGY_INT));
        ObjectManager.getInstance(Brain.getInstance()).getRumList().
                add(new Rum(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                        GameConfig.getInstance().getMapWidthX() / 2 + 1), false, ENERGY_INT));
        ObjectManager.getInstance(Brain.getInstance()).getRumList().
                add(new Rum(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                        GameConfig.getInstance().getMapWidthX() / 2 - 1), false, ENERGY_INT));

        try {
            Method method = characterManager.getClass().
                    getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
            method.setAccessible(true);
            method.invoke(characterManager, Monkey.MonkeyType.HUNTER);

            // Test
            Assert.assertFalse(monkeyHunterOrigin.getPosition().isEquals(monkeyHunterToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test that the Rum is still available after the monkey stepped on it
     */
    @Test
    public void testMonkeyHunterRumStillAvailable() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyHunterToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        characterManager.getMonkeys().add(monkeyHunterToTest);

        // Surround monkey of rum
        ObjectManager.getInstance(Brain.getInstance()).getRumList().
                add(new Rum(new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                        GameConfig.getInstance().getMapWidthX() / 2), true, ENERGY_INT));
        ObjectManager.getInstance(Brain.getInstance()).getRumList().
                add(new Rum(new Position(GameConfig.getInstance().getMapWidthX() / 2 - 1,
                        GameConfig.getInstance().getMapWidthX() / 2), true, ENERGY_INT));
        ObjectManager.getInstance(Brain.getInstance()).getRumList().
                add(new Rum(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                        GameConfig.getInstance().getMapWidthX() / 2 + 1), true, ENERGY_INT));
        ObjectManager.getInstance(Brain.getInstance()).getRumList().
                add(new Rum(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                        GameConfig.getInstance().getMapWidthX() / 2 - 1), true, ENERGY_INT));

        try {
            Method method = characterManager.getClass().
                    getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
            method.setAccessible(true);
            method.invoke(characterManager, Monkey.MonkeyType.HUNTER);

            // Test
            for (int i = 0; i < ObjectManager.getInstance(Brain.getInstance()).getRumList().size(); i++) {
                Assert.assertTrue(ObjectManager.getInstance(Brain.getInstance()).getRumList().get(i).isVisible());
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the move of the hunter monkey on treasure preserve its visibility
     */
    @Test
    public void testMoveMonkeyHunterTreasureVisibility() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyHunterOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        Monkey monkeyHunterToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        characterManager.getMonkeys().add(monkeyHunterToTest);

        // Surround monkey of treasures
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                        GameConfig.getInstance().getMapWidthX() / 2), false));
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2 - 1,
                        GameConfig.getInstance().getMapWidthX() / 2), false));
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                        GameConfig.getInstance().getMapWidthX() / 2 + 1), false));
        ObjectManager.getInstance(Brain.getInstance()).getTreasureList().
                add(new Treasure(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                        GameConfig.getInstance().getMapWidthX() / 2 - 1), false));

        try {
            Method method = characterManager.getClass().
                    getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
            method.setAccessible(true);
            method.invoke(characterManager, Monkey.MonkeyType.HUNTER);

            // Test
            for (int i = 0; i < ObjectManager.getInstance(Brain.getInstance()).getTreasureList().size(); i++) {
                Assert.assertFalse(ObjectManager.getInstance(Brain.getInstance()).getTreasureList().get(i).isVisible());
            }
            Assert.assertFalse(monkeyHunterOrigin.getPosition().isEquals(monkeyHunterToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the move of the hunter monkey surrounded by Erratics monkeys
     */
    @Test
    public void testAskMoveMonkeyHunterAroundErratic() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyHunterOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);

        Monkey monkeyErraticOriginOne = new Monkey(new Position((GameConfig.getInstance().getMapWidthX() / 2) + 1,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticOriginTwo = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) + 1), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticOriginThree = new Monkey(new Position((GameConfig.getInstance().getMapWidthX() / 2) - 1,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyErraticOriginFour = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) - 1), Monkey.MonkeyType.ERRATIC);
        Monkey monkeyHunterToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.ERRATIC);
        characterManager.getMonkeys().add(monkeyHunterToTest);
        characterManager.getMonkeys().add(monkeyErraticOriginOne);
        characterManager.getMonkeys().add(monkeyErraticOriginTwo);
        characterManager.getMonkeys().add(monkeyErraticOriginThree);
        characterManager.getMonkeys().add(monkeyErraticOriginFour);

        try {
            Method method = characterManager.
                    getClass().getDeclaredMethod(SET_MOVING_HUNTER_POSITION, Monkey.class);
            method.setAccessible(true);
            method.invoke(characterManager, monkeyHunterToTest);

            // Test
            Assert.assertTrue(monkeyHunterOrigin.getPosition().isEquals(monkeyHunterToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the move of the hunter monkey surrounded by Hunter monkeys
     */
    @Test
    public void testAskMoveMonkeyHunterAroundHunter() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyHunterOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);

        Monkey monkeyHunterOriginOne = new Monkey(new Position((GameConfig.getInstance().getMapWidthX() / 2) + 1,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        Monkey monkeyHunterOriginTwo = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) + 1), Monkey.MonkeyType.HUNTER);
        Monkey monkeyHunterOriginThree = new Monkey(new Position((GameConfig.getInstance().getMapWidthX() / 2) - 1,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        Monkey monkeyHunterOriginFour = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) - 1), Monkey.MonkeyType.HUNTER);
        Monkey monkeyHunterToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        characterManager.getMonkeys().add(monkeyHunterToTest);
        characterManager.getMonkeys().add(monkeyHunterOriginOne);
        characterManager.getMonkeys().add(monkeyHunterOriginTwo);
        characterManager.getMonkeys().add(monkeyHunterOriginThree);
        characterManager.getMonkeys().add(monkeyHunterOriginFour);

        try {
            Method method = characterManager.
                    getClass().getDeclaredMethod(SET_MOVING_HUNTER_POSITION, Monkey.class);
            method.setAccessible(true);
            method.invoke(characterManager, monkeyHunterToTest);

            // Test
            Assert.assertTrue(monkeyHunterOrigin.getPosition().isEquals(monkeyHunterToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the death of the (alive)pirate when hunter monkey walk on it
     */
    @Test
    public void testMoveMonkeyHunterOnAlivePirate() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyHunterOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        Monkey monkeyHunterToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        characterManager.getMonkeys().add(monkeyHunterToTest);

        //Init pirate list
        characterManager.setPirateList(new ArrayList<>());
        Pirate pirateOne = new Pirate(new Position((GameConfig.getInstance().getMapWidthX() / 2) + 1,
                GameConfig.getInstance().getMapHeightY() / 2), INIT_PORT_PIRATE, new Energy(ENERGY_INT));
        Pirate pirateTwo = new Pirate(new Position((GameConfig.getInstance().getMapWidthX() / 2) - 1,
                GameConfig.getInstance().getMapHeightY() / 2), INIT_PORT_PIRATE + 1, new Energy(ENERGY_INT));
        Pirate pirateThree = new Pirate(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) + 1), INIT_PORT_PIRATE + 2, new Energy(ENERGY_INT));
        Pirate pirateFour = new Pirate(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) - 1), INIT_PORT_PIRATE + 3, new Energy(ENERGY_INT));
        characterManager.getPiratesList().add(pirateOne);
        characterManager.getPiratesList().add(pirateTwo);
        characterManager.getPiratesList().add(pirateThree);
        characterManager.getPiratesList().add(pirateFour);

        try {
            Method askMoveMonkey = characterManager.
                    getClass().getDeclaredMethod(SET_MOVING_HUNTER_POSITION, Monkey.class);
            askMoveMonkey.setAccessible(true);
            askMoveMonkey.invoke(characterManager, monkeyHunterToTest);

            Method eventWhenMoveMonkey = characterManager.
                    getClass().getDeclaredMethod(EVENT_WHEN_MOVE_MONKEY);
            eventWhenMoveMonkey.setAccessible(true);
            eventWhenMoveMonkey.invoke(characterManager);

            // Test
            Assert.assertFalse(characterManager.getPiratesList().get(0).getState()
                    == characterManager.getPiratesList().get(1).getState()
                    && characterManager.getPiratesList().get(0).getState()
                    == characterManager.getPiratesList().get(2).getState()
                    && characterManager.getPiratesList().get(0).getState()
                    == characterManager.getPiratesList().get(3).getState()
            );
            Assert.assertFalse(monkeyHunterOrigin.getPosition().isEquals(monkeyHunterToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * Test the death of the (dead)pirate when hunter monkey walk on it
     */
    @Test
    public void testMoveMonkeyHunterOnDeadPirate() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyHunterOrigin = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        Monkey monkeyHunterToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        characterManager.getMonkeys().add(monkeyHunterToTest);

        //Init pirate list
        characterManager.setPirateList(new ArrayList<>());
        Pirate pirateOne = new Pirate(new Position((GameConfig.getInstance().getMapWidthX() / 2) + 1,
                GameConfig.getInstance().getMapHeightY() / 2), INIT_PORT_PIRATE, new Energy(0));
        pirateOne.setState(Character.State.DEAD);
        Pirate pirateTwo = new Pirate(new Position((GameConfig.getInstance().getMapWidthX() / 2) - 1,
                GameConfig.getInstance().getMapHeightY() / 2), INIT_PORT_PIRATE + 1, new Energy(0));
        pirateTwo.setState(Character.State.DEAD);
        Pirate pirateThree = new Pirate(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) + 1), INIT_PORT_PIRATE + 2, new Energy(0));
        pirateThree.setState(Character.State.DEAD);
        Pirate pirateFour = new Pirate(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                (GameConfig.getInstance().getMapHeightY() / 2) - 1), INIT_PORT_PIRATE + 3, new Energy(0));
        pirateFour.setState(Character.State.DEAD);
        characterManager.getPiratesList().add(pirateOne);
        characterManager.getPiratesList().add(pirateTwo);
        characterManager.getPiratesList().add(pirateThree);
        characterManager.getPiratesList().add(pirateFour);

        try {
            Method askMoveMonkey = characterManager.
                    getClass().getDeclaredMethod(SET_MOVING_HUNTER_POSITION, Monkey.class);
            askMoveMonkey.setAccessible(true);
            askMoveMonkey.invoke(characterManager, monkeyHunterToTest);

            Method eventWhenMoveMonkey = characterManager.
                    getClass().getDeclaredMethod(EVENT_WHEN_MOVE_MONKEY);
            eventWhenMoveMonkey.setAccessible(true);
            eventWhenMoveMonkey.invoke(characterManager);

            // Test
            Assert.assertTrue(characterManager.getPiratesList().get(0).getState()
                    == characterManager.getPiratesList().get(1).getState()
                    && characterManager.getPiratesList().get(0).getState()
                    == characterManager.getPiratesList().get(2).getState()
                    && characterManager.getPiratesList().get(0).getState()
                    == characterManager.getPiratesList().get(3).getState()
            );
            Assert.assertFalse(monkeyHunterOrigin.getPosition().isEquals(monkeyHunterToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Cailyn Davies
     * @Collab :
     */
    /**
     * Test the move of the hunter monkey follow the pirate on map
     */
    @Test
    public void testAskMoveMonkeyHunterFollowPirate() {


        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init object
        ObjectManager.getInstance(Brain.getInstance()).setTreasureList(new ArrayList<>());

        // Init monkey list
        characterManager.setMonkeyList(new ArrayList<>());
        Monkey monkeyHunterToTest = new Monkey(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), Monkey.MonkeyType.HUNTER);
        characterManager.getMonkeys().add(monkeyHunterToTest);

        // Init pirate list
        ArrayList<Pirate> listPirates = new ArrayList<>();
        characterManager.setPirateList(listPirates);

        //Init variables
        int xClosestPirate;
        int yClosestPirate;

        xClosestPirate = GameConfig.getInstance().getMapWidthX() / ERRATIC_CHECK_DIR;
        yClosestPirate = GameConfig.getInstance().getMapHeightY() / ERRATIC_CHECK_DIR;

        Pirate pirateForTest1 = new Pirate(new Position(xClosestPirate,
                yClosestPirate), INIT_PORT_PIRATE, new Energy(ENERGY_INT));
        Pirate pirateForTest2 = new Pirate(new Position(GameConfig.getInstance().getMapWidthX() - 1,
                GameConfig.getInstance().getMapHeightY() - 1), INIT_PORT_PIRATE, new Energy(ENERGY_INT));
        characterManager.getPiratesList().add(pirateForTest1);
        characterManager.getPiratesList().add(pirateForTest2);

        //Init variables
        int numberOfMovesCalculated = 0;
        int xMonkeyO = 0;
        int yMonkeyO = 0;

        //Here, calculate the minimum distance between the closest pirate and the monkey :
        numberOfMovesCalculated += Math.sqrt((xMonkeyO - xClosestPirate) * (xMonkeyO - xClosestPirate));
        numberOfMovesCalculated += Math.sqrt((yMonkeyO - yClosestPirate) * (yMonkeyO - yClosestPirate));

        try {
            Method askMoveMonkeys = characterManager.
                    getClass().getDeclaredMethod(ASK_MOVE_MONKEYS, Monkey.MonkeyType.class);
            askMoveMonkeys.setAccessible(true);
            //Move the monkey the exact number of times needed, at minimum, to reach the closest pirate
            for (int i = 0; i < numberOfMovesCalculated; i++) {
                askMoveMonkeys.invoke(characterManager, Monkey.MonkeyType.HUNTER);
            }
            // Test if the monkey is on the pirate in the ideal time
            Assert.assertTrue(pirateForTest1.getPosition().isEquals(monkeyHunterToTest.getPosition()));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author : Clément Pabst
     * @Collab :
     */
    /**
     * method tearDown() called at the end of MonkeyTest class
     *
     * @throws Exception an unexpected exception
     */
    @After
    public void tearDown() throws Exception {
    }
}
