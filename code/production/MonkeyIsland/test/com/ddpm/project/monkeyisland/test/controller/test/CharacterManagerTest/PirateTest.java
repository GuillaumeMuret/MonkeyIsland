/**
 * @Author : François de Broch d'Hotelans
 * @Collab :
 */



package com.ddpm.project.monkeyisland.test.controller.test.CharacterManagerTest;

import com.ddpm.project.monkeyisland.communication.Communication;
import com.ddpm.project.monkeyisland.communication.Distributor;
import com.ddpm.project.monkeyisland.communication.PostmanServer;
import com.ddpm.project.monkeyisland.communication.proxy.ProxyClient;
import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.controller.CharacterManager;
import com.ddpm.project.monkeyisland.controller.MapManager;
import com.ddpm.project.monkeyisland.controller.ObjectManager;
import com.ddpm.project.monkeyisland.model.game.character.Monkey;
import com.ddpm.project.monkeyisland.model.game.object.Treasure;
import com.ddpm.project.monkeyisland.utils.LogUtils;
import com.ddpm.project.monkeyisland.model.constant.GameConfig;
import com.ddpm.project.monkeyisland.model.game.attribute.Energy;
import com.ddpm.project.monkeyisland.model.game.character.Character;
import com.ddpm.project.monkeyisland.model.game.character.Pirate;
import com.ddpm.project.monkeyisland.model.game.map.Square;
import com.ddpm.project.monkeyisland.model.game.object.Rum;
import com.ddpm.project.monkeyisland.model.game.position.Position;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Communication.class,PostmanServer.class})

public class PirateTest extends TestCase {

    /**
     * pirate's default energy
     */
    private static final int INIT_ENERGY_PIRATE = 15;

    /**
     * pirate's max energy
     */
    private static final int INIT_ENERGY_MAX_PIRATE = 20;

    /**
     * pirate's sup max energy
     */
    private static final int INIT_ENERGY_SUP_MAX_PIRATE = 25;

    /**
     * energy to test in method testMovePirateOnRumSupEnergyMax()
     */
    private static final int ENERGY_SUP_MAX_PIRATE_TO_TEST = 27;

    /**
     * energy to test in method testMovePirateOnRumEnergyMax()
     */
    private static final int ENERGY_MAX_TO_TEST = 22;

    /**
     * bottle value of energy
     */
    private static final int ENERGY_INT = 3;

    /**
     * energy to test in method testMovePirateOnRum()
     */
    private static final int ENERGY_TO_TEST = 17;

    /**
     * pirate's default port
     */
    private static final int INIT_PORT_PIRATE = 8020;

    /**
     * pirate's default port
     */
    private static final int INIT_OTHER_PORT_PIRATE = 12000;

    /**
     * Visible rum bottle
     */
    private static final boolean RUM_VISIBLE = true;

    /**
     * Counter
     */
    private static final int COUNTER_VALUE = 2100000000;

    /**
     * number of test
     */
    private static final int NUMBER_OF_TEST = 10000;

    /**
     * Ask move pirate string
     */
    private static final String ASK_MOVE_PIRATE = "askMovePirate";

    /**
     * Strict equality in probabilities has to be avoided
     */
    private static final double PROBABILITY_HALF_FOUR = 1 / 4f;

    /**
     * Semicolon string
     */
    private static final String SEMICOLON = " ; ";

    /**
     * probability min move test probability of drunk pirate movement
     */
    private static final double PROBABILITY_MIN_MV_ERRATIC_MONKEY = 0.23;

    /**
     * probability min move test probability of drunk pirate movement
     */
    private static final double PROBABILITY_MAX_MV_ERRATIC_MONKEY = 0.27;

    /**
     * string test probability of drunk pirate movement
     */
    private static final String STRING_DIRECTION = "(UP ; DOWN ; LEFT ; RIGHT)";

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
        PowerMockito.when(communicationMock.getProxyClient()).thenReturn(ProxyClient.getInstance(postmanServerMock, brain));
        brain.setCommunication(communicationMock);
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
     * Test right down diagonal pirate move : -> impossible
     */
    @Test
    public void testRightDownDiagonalMoveCharacterPirate() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2, GameConfig.
                getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate(new Position(piratePosition), INIT_PORT_PIRATE,
                new Energy(INIT_ENERGY_PIRATE));

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Add pirate to list
        characterManager.getPiratesList().add(pirateToTest);

        Position positionToTest = new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                GameConfig.getInstance().getMapHeightY() / 2 - 1);

        // Test right diagonal direction
        characterManager.askMovePirate(pirateToTest.getPort(), +1, -1);
        assertFalse(pirateToTest.getPosition().isEquals(positionToTest));

    }

    /**
     * Test right high diagonal pirate move : -> impossible
     */
    @Test
    public void testRightHighDiagonalMoveCharacterPirate() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2, GameConfig.
                getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate(new Position(piratePosition), INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Add pirate to list
        characterManager.getPiratesList().add(pirateToTest);

        Position positionToTest = new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                GameConfig.getInstance().getMapHeightY() / 2 + 1);


        // Test right diagonal direction
        characterManager.askMovePirate(pirateToTest.getPort(), +1, +1);
        assertFalse(pirateToTest.getPosition().isEquals(positionToTest));

    }

    /**
     * Test right down diagonal pirate move : -> impossible
     */
    @Test
    public void testLeftDownDiagonalMoveCharacterPirate() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2, GameConfig.
                getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate(new Position(piratePosition), INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Add pirate to list
        characterManager.getPiratesList().add(pirateToTest);

        Position positionToTest = new Position(GameConfig.getInstance().getMapWidthX() / 2 - 1,
                GameConfig.getInstance().getMapHeightY() / 2 - 1);

        // Test right diagonal direction
        characterManager.askMovePirate(pirateToTest.getPort(), -1, -1);
        assertFalse(pirateToTest.getPosition().isEquals(positionToTest));

    }

    /**
     * Test right high diagonal pirate move : -> impossible
     */
    @Test
    public void testLeftHighDiagonalMoveCharacterPirate() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2, GameConfig.
                getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate(new Position(piratePosition), INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Add pirate to list
        characterManager.getPiratesList().add(pirateToTest);

        Position positionToTest = new Position(GameConfig.getInstance().getMapWidthX() / 2 - 1,
                GameConfig.getInstance().getMapHeightY() / 2 + 1);

        // Test right diagonal direction
        characterManager.askMovePirate(pirateToTest.getPort(), -1, +1);
        assertFalse(pirateToTest.getPosition().isEquals(positionToTest));

    }

    /**
     * Test the pirate die by lack of energy
     */
    @Test
    public void testPirateDieWithoutEnergy() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init object
        //ObjectManager.getInstance(Brain.getInstance()).setTreasureList(new ArrayList<>());

        // Init pirate list
        ArrayList<Pirate> listPirates = new ArrayList<>();
        characterManager.setPirateList(listPirates);
        Pirate pirateForTest1 = new Pirate(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2),0, new Energy(3));
        characterManager.getPiratesList().add(pirateForTest1);

        characterManager.askMovePirate(0, 1, 0);
        Assert.assertFalse(listPirates.isEmpty());
        characterManager.askMovePirate(0, 0, 1);
        Assert.assertFalse(listPirates.isEmpty());
        characterManager.askMovePirate(0, 0, 1);
        LogUtils.test("Pirate State = pirateForTest1.getState() => " + pirateForTest1.getState());
        Assert.assertTrue(pirateForTest1.getState().equals(Character.State.DEAD));
    }


    /**
     * Test the method askMovePirate() for pirates : test return true
     * move pirate on free ground square
     */
    @Test
    public void testCanMovePirateOnGroundSquare() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2, GameConfig.
                getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate(new Position(piratePosition), INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Add pirate to list
        characterManager.getPiratesList().add(pirateToTest);

        // Test all direction

        // LEFT
        characterManager.askMovePirate(pirateToTest.getPort(), -1, 0);
        assertFalse(pirateToTest.getPosition().isEquals(piratePosition));
        piratePosition.setPositionX(piratePosition.getPositionX() - 1);
        assertTrue(pirateToTest.getPosition().isEquals(piratePosition));

        // RIGHT
        piratePosition.setPositionX(piratePosition.getPositionX() + 1);
        characterManager.askMovePirate(pirateToTest.getPort(), 1, 0);
        assertTrue(pirateToTest.getPosition().isEquals(piratePosition));

        // UP
        piratePosition.setPositionY(piratePosition.getPositionY() + 1);
        characterManager.askMovePirate(pirateToTest.getPort(), 0, 1);
        assertTrue(pirateToTest.getPosition().isEquals(piratePosition));

        // DOWN
        piratePosition.setPositionY(piratePosition.getPositionY() - 1);
        characterManager.askMovePirate(pirateToTest.getPort(), 0, -1);
        assertTrue(pirateToTest.getPosition().isEquals(piratePosition));
    }

    /**
     * Test the method askMovePirate() for pirates : test return true
     * move pirate on free ground square
     */
    @Test
    public void testCanMovePirateOnSeaSquare() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position positionOrigin = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);
        Position positionToTest = new Position(positionOrigin);

        // Prepare pirate
        Pirate pirateToTest = new Pirate(positionToTest, INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));

        // Prepare square around sea
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.SEA);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.SEA);

        Brain.getInstance().getMapManager().getMap().getSquareList().get(GameConfig.getInstance().getMapWidthX() / 2).
                get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.SEA);

        Brain.getInstance().getMapManager().getMap().getSquareList().get(GameConfig.getInstance().getMapWidthX() / 2).
                get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.SEA);

        // Add pirate to list
        characterManager.getPiratesList().add(pirateToTest);

        // Test all direction

        // LEFT
        characterManager.askMovePirate(pirateToTest.getPort(), -1, 0);
        assertTrue(pirateToTest.getPosition().isEquals(positionOrigin));

        // RIGHT
        characterManager.askMovePirate(pirateToTest.getPort(), 1, 0);
        assertTrue(pirateToTest.getPosition().isEquals(positionOrigin));

        // UP
        characterManager.askMovePirate(pirateToTest.getPort(), 0, +1);
        assertTrue(pirateToTest.getPosition().isEquals(positionOrigin));

        // DOWN
        characterManager.askMovePirate(pirateToTest.getPort(), 0, -1);
        assertTrue(pirateToTest.getPosition().isEquals(positionOrigin));

    }

    /**
     * Test of the move of the pirate on Rum square : value of his energy < ENERGY_MAX
     */

    public void testMovePirateOnRum() {

        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init object manager
        ObjectManager objectManager = Brain.getInstance().getObjectManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);

        Position rumPosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2 - 1);

        Energy energyPirateOrigin = new Energy(INIT_ENERGY_PIRATE);

        // Prepare rumBottle
        Rum rumToTest = new Rum(rumPosition, RUM_VISIBLE, ENERGY_INT);

        // Prepare pirate
        Pirate pirateToTest = new Pirate(new Position(piratePosition), INIT_PORT_PIRATE, energyPirateOrigin);



        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Add pirate to list
        characterManager.getPiratesList().add(pirateToTest);

        // Add rum to list
        objectManager.getRumList().add(rumToTest);

        // DOWN
        LogUtils.test(Integer.toString(energyPirateOrigin.getValue()));
        LogUtils.test(pirateToTest.getState().toString());

        assertTrue(pirateToTest.getState().toString().equals(Character.State.NORMAL.toString()));

        characterManager.askMovePirate(pirateToTest.getPort(), 0, -1);

        // Energy to test - Test with value 17 because the move of the pirate decrement energyPirateOrigin - 1
        Energy energyToTest = new Energy(ENERGY_TO_TEST);

        LogUtils.test(Integer.toString(energyToTest.getValue()));
        LogUtils.test(pirateToTest.getState().toString());

        assertTrue(pirateToTest.getState().toString().equals(Character.State.NORMAL.toString()));
        assertTrue(energyToTest.getValue() == energyPirateOrigin.getValue());
    }

    /**
     * Test of the move of the pirate on Rum square : value of his energy = ENERGY_MAX
     */
    @Test
    public void testMovePirateOnRumEnergyMax() {

        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init object manager
        ObjectManager objectManager = Brain.getInstance().getObjectManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);

        Position rumPosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2 - 1);

        Energy energyPirateOrigin = new Energy(INIT_ENERGY_MAX_PIRATE);

        // Prepare rumBottle
        Rum rumToTest = new Rum(rumPosition, RUM_VISIBLE, ENERGY_INT);

        // Prepare pirate
        Pirate pirateToTest = new Pirate(new Position(piratePosition), INIT_PORT_PIRATE, energyPirateOrigin);



        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Add pirate to list
        characterManager.getPiratesList().add(pirateToTest);

        // Add rum to list
        objectManager.getRumList().add(rumToTest);




        // DOWN
        assertTrue(pirateToTest.getState().toString().equals(Character.State.NORMAL.toString()));
        LogUtils.test(pirateToTest.getState().toString());
        LogUtils.test(Integer.toString(energyPirateOrigin.getValue()));
        characterManager.askMovePirate(pirateToTest.getPort(), 0, -1);
        LogUtils.test(Integer.toString(energyPirateOrigin.getValue()));
        LogUtils.test(pirateToTest.getState().toString());
        assertTrue(pirateToTest.getState().toString().equals(Character.State.DRUNK.toString()));

        // Energy to test - Test with value 17 because the move of the pirate decrement energyPirateOrigin - 1
        Energy energyToTest = new Energy(ENERGY_MAX_TO_TEST);

        assertTrue(energyToTest.getValue() == energyPirateOrigin.getValue());
    }

    /**
     * Test of the move of the pirate on Rum square : value of his energy > ENERGY_MAX
     */
    @Test
    public void testMovePirateOnRumSupEnergyMax() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init object manager
        ObjectManager objectManager = Brain.getInstance().getObjectManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);

        Position rumPosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2 - 1);

        Energy energyPirateOrigin = new Energy(INIT_ENERGY_SUP_MAX_PIRATE);

        // Prepare rumBottle
        Rum rumToTest = new Rum(rumPosition, RUM_VISIBLE, ENERGY_INT);

        // Prepare pirate
        Pirate pirateToTest = new Pirate(new Position(piratePosition), INIT_PORT_PIRATE, energyPirateOrigin);

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Add pirate to list
        characterManager.getPiratesList().add(pirateToTest);

        // Add rum to list
        objectManager.getRumList().add(rumToTest);



        // Energy to test - Test with value 17 because the move of the pirate decrement energyPirateOrigin - 1
        Energy energyToTest = new Energy(ENERGY_SUP_MAX_PIRATE_TO_TEST);

        // DOWN
        LogUtils.test(Integer.toString(energyPirateOrigin.getValue()));
        characterManager.askMovePirate(pirateToTest.getPort(), 0, -1);
        LogUtils.test(Integer.toString(energyToTest.getValue()));
        assertTrue(pirateToTest.getState().toString().equals(Character.State.DRUNK.toString()));
        LogUtils.test(pirateToTest.getState().toString());

        assertTrue(energyToTest.getValue() == energyPirateOrigin.getValue());
    }

    /**
     * Test of no movement with command (0,0)
     */
    @Test
    public void testMovePiratePositionZeroZero() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2, GameConfig.
                getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate(new Position(piratePosition), INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Add pirate to list
        characterManager.getPiratesList().add(pirateToTest);


        // NO MOVEMENT
        characterManager.askMovePirate(pirateToTest.getPort(), 0, 0);
        assertTrue(pirateToTest.getPosition().isEquals(piratePosition));
    }

    /**
     *  Test of pirate movement out of the map
     */
    @Test
    public void testMovePirateOutMap() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX(), GameConfig.
                getInstance().getMapHeightY());

        // Prepare pirate
        Pirate pirateToTest = new Pirate(new Position(piratePosition), INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        // Add pirate to list
        characterManager.getPiratesList().add(pirateToTest);

        // MOVEMENT OUT OF BOUNDS
        characterManager.askMovePirate(pirateToTest.getPort(), +1, 0);
        assertTrue(pirateToTest.getPosition().isEquals(piratePosition));
    }

    /**
     * Test of pirate movement on busy square (Pirate already there)
     */
    @Test
    public void testMovePirateOnOccupiedSquareByOtherPirateRight() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2, GameConfig.
                getInstance().getMapHeightY() / 2);

        // Prepare data test
        Position otherPiratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1, GameConfig.
                getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate(new Position(piratePosition), INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Prepare otherPirate
        Pirate otherPirateToTest = new Pirate(new Position(otherPiratePosition), INIT_PORT_PIRATE + 1,
                new Energy(INIT_ENERGY_PIRATE));

        // Add pirate and otherPirate to list
        characterManager.getPiratesList().add(pirateToTest);
        characterManager.getPiratesList().add(otherPirateToTest);

        // NO MOVEMENT
        characterManager.askMovePirate(pirateToTest.getPort(), +1, 0);
        assertTrue(pirateToTest.getPosition().isEquals(piratePosition));
    }

    /**
     * Test of pirate movement on busy square (Pirate already there)
     */
    @Test
    public void testMovePirateOnOccupiedSquareByOtherPirateUp() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2, GameConfig.
                getInstance().getMapHeightY() / 2);

        // Prepare data test
        Position otherPiratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2, GameConfig.
                getInstance().getMapHeightY() / 2 + 1);

        // Prepare pirate
        Pirate pirateToTest = new Pirate(new Position(piratePosition), INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Prepare otherPirate
        Pirate otherPirateToTest = new Pirate(new Position(otherPiratePosition), INIT_PORT_PIRATE + 1,
                new Energy(INIT_ENERGY_PIRATE));

        // Add pirate and otherPirate to list
        characterManager.getPiratesList().add(pirateToTest);
        characterManager.getPiratesList().add(otherPirateToTest);

        // Movement
        characterManager.askMovePirate(pirateToTest.getPort(), 0, +1);
        assertTrue(pirateToTest.getPosition().isEquals(piratePosition));
    }

    /**
     * Test of pirate movement on busy square (Pirate already there)
     */
    @Test
    public void testMovePirateOnOccupiedSquareByOtherPirateDown() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2, GameConfig.
                getInstance().getMapHeightY() / 2);

        // Prepare data test
        Position otherPiratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2, GameConfig.
                getInstance().getMapHeightY() / 2 - 1);

        // Prepare pirate
        Pirate pirateToTest = new Pirate(new Position(piratePosition), INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Prepare otherPirate
        Pirate otherPirateToTest = new Pirate(new Position(otherPiratePosition), INIT_PORT_PIRATE + 1,
                new Energy(INIT_ENERGY_PIRATE));

        // Add pirate and otherPirate to list
        characterManager.getPiratesList().add(pirateToTest);
        characterManager.getPiratesList().add(otherPirateToTest);

        // Movement
        characterManager.askMovePirate(pirateToTest.getPort(), 0, -1);
        assertTrue(pirateToTest.getPosition().isEquals(piratePosition));
    }

    /**
     * Test of pirate movement on busy square (Pirate already there)
     */
    @Test
    public void testMovePirateOnOccupiedSquareByOtherPirateLeft() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2, GameConfig.
                getInstance().getMapHeightY() / 2);

        // Prepare data test
        Position otherPiratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2 - 1,
                GameConfig.getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate(new Position(piratePosition), INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Prepare otherPirate
        Pirate otherPirateToTest = new Pirate(new Position(otherPiratePosition), INIT_PORT_PIRATE + 1,
                new Energy(INIT_ENERGY_PIRATE));

        // Add pirate and otherPirate to list
        characterManager.getPiratesList().add(pirateToTest);
        characterManager.getPiratesList().add(otherPirateToTest);

        // Movement
        characterManager.askMovePirate(pirateToTest.getPort(), - 1, 0);
        assertTrue(pirateToTest.getPosition().isEquals(piratePosition));
    }

    /**
     * Test of Pirate Death when movement on Monkey Square
     */
    @Test
    public void testPirateDeathWhenMoveOnMonkeyErraticSquare() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);

        Position monkeyPosition = new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                GameConfig.getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate (piratePosition, INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));


        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Prepare Monkey
        Monkey monkeyErratic = new Monkey(monkeyPosition, Monkey.MonkeyType.ERRATIC);

        // Prepare Data
        characterManager.getPiratesList().add(pirateToTest);
        characterManager.getMonkeys().add(monkeyErratic);

        // Pirate movement
        LogUtils.test(pirateToTest.getState().toString());
        characterManager.askMovePirate(pirateToTest.getPort(), +1, 0);

        LogUtils.test(pirateToTest.getState().toString());
    
        assertTrue(pirateToTest.getState().toString().equals(Character.State.DEAD.toString()));
    }

    /**
     * Test of Pirate Death when movement on MonkeyHunter square
     */
    @Test
    public void testPirateDeathWhenMoveOnMonkeyHunterSquare() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);

        Position monkeyPosition = new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                GameConfig.getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate (piratePosition, INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));


        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Prepare Monkey
        Monkey monkeyHunter = new Monkey(monkeyPosition, Monkey.MonkeyType.HUNTER);

        // Prepare Data
        characterManager.getPiratesList().add(pirateToTest);
        characterManager.getMonkeys().add(monkeyHunter);

        // Pirate movement
        LogUtils.test(pirateToTest.getState().toString());
        characterManager.askMovePirate(pirateToTest.getPort(), +1, 0);

        LogUtils.test(pirateToTest.getState().toString());

        assertTrue(pirateToTest.getState().toString().equals(Character.State.DEAD.toString()));
    }

    /**
     * Test of Pirate when he discover the treasure
     */
    @Test
    public void testTreasureDiscoveredByPirate() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init object manager
        ObjectManager objectManager = Brain.getInstance().getObjectManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);

        Position treasurePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                GameConfig.getInstance().getMapHeightY() / 2);

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Prepare Monkey
        Treasure treasure = new Treasure(treasurePosition, false);

        // Prepare pirate
        Pirate pirateToTest = new Pirate (piratePosition, INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));

        // Prepare Data
        characterManager.getPiratesList().add(pirateToTest);
        objectManager.getTreasureList().add(treasure);

        // Pirate movement
        LogUtils.test(Boolean.toString(treasure.isVisible()));
        LogUtils.test(pirateToTest.getState().toString());

        characterManager.askMovePirate(pirateToTest.getPort(), +1, 0);

        LogUtils.test(Boolean.toString(treasure.isVisible()));
        LogUtils.test(pirateToTest.getState().toString());

        assertTrue(treasure.isVisible());
        //We could add verification around the interruptMoveMonkeyThread variable

        long counter = COUNTER_VALUE;

        LogUtils.debug("Time : " + System.currentTimeMillis());
        while (counter > 0) {
            counter = counter - 1;
        }
        counter = COUNTER_VALUE;
        while (counter > 0) {
            counter = counter - 1;
        }
        counter = COUNTER_VALUE;

        while (counter > 0) {
            counter = counter - 1;
        }
        LogUtils.debug("Time :" + " " + System.currentTimeMillis());

        assertFalse(objectManager.getTreasureList().get(0).isVisible());
    }

    /**
     * Test of Pirate when he discovers the treasure on Monkey square
     */
    @Test
    public void testTreasureDiscoveredByPirateOnMonkeySquare() {
        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);

        Position treasurePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                GameConfig.getInstance().getMapHeightY() / 2);

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare pirate
        Pirate pirateToTest = new Pirate (piratePosition, INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));

        // Init game state
        Brain.getInstance().setGameState(Brain.GameState.NO_INIT);

        // Init object manager
        ObjectManager objectManager = Brain.getInstance().getObjectManager();

        // Prepare Monkey
        Monkey monkeyHunter = new Monkey(treasurePosition, Monkey.MonkeyType.HUNTER);

        // Prepare Monkey
        Treasure treasure = new Treasure(treasurePosition, false);

        // Prepare Data
        characterManager.getPiratesList().add(pirateToTest);
        characterManager.getMonkeys().add(monkeyHunter);
        objectManager.getTreasureList().add(treasure);

        Distributor.getInstance(Brain.getInstance()).dispatch("/I", "", INIT_OTHER_PORT_PIRATE);
        assertTrue(Brain.getInstance().getGameState().equals(Brain.GameState.GAMING));
        LogUtils.test(Brain.getInstance().getGameState().toString());

        // Pirate movement
        LogUtils.test(Boolean.toString(treasure.isVisible()));
        LogUtils.test("Etat du Pirate :");
        LogUtils.test(pirateToTest.getState().toString());

        characterManager.askMovePirate(pirateToTest.getPort(), +1, 0);

        LogUtils.test("Visibilité du trésor :");
        LogUtils.test(Boolean.toString(treasure.isVisible()));
        LogUtils.test("Etat du Pirate découvrant le trésor sur une case Monkey :");
        LogUtils.test(pirateToTest.getState().toString());
        assertTrue(treasure.isVisible());
        assertTrue(pirateToTest.getState().equals(Character.State.DEAD));
        LogUtils.test(Brain.getInstance().getGameState().toString());

        assertTrue(Brain.getInstance().getGameState().equals(Brain.GameState.END_GAME));
    }

    /**
     * Test of Pirate No movement when pirate is dead
     */
    @Test
    public void testMovePirateDeadOnRumSquare() {
        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        //Init object manager
        ObjectManager objectManager = Brain.getInstance().getObjectManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);

        Position rumPosition = new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                GameConfig.getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate (piratePosition, INIT_PORT_PIRATE, new Energy(0));
        pirateToTest.setState(Character.State.DEAD);

        // Prepare Rum
        Rum rumBottle = new Rum(rumPosition, RUM_VISIBLE, INIT_ENERGY_PIRATE);

        // Prepare Data
        characterManager.getPiratesList().add(pirateToTest);
        objectManager.getRumList().add(rumBottle);

        characterManager.askMovePirate(pirateToTest.getPort(), +1, 0);
        LogUtils.test(pirateToTest.getState().toString());

        assertTrue(pirateToTest.getPosition().equals(piratePosition));
    }

    /**
     * Test of Pirate resurrection already dead with a re appearance of rum bottle
     */
    @Test
    public void testPirateDeathWhenRumBottleReappear() {


        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Init object manager
        ObjectManager objectManager = Brain.getInstance().getObjectManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);

        Position rumBottlePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate (piratePosition, INIT_PORT_PIRATE, new Energy(0));
        pirateToTest.setState(Character.State.DEAD);

        // Prepare Bottle
        Rum rumBottle = new Rum(rumBottlePosition, false, ENERGY_INT);

        // Prepare Data
        characterManager.getPiratesList().add(pirateToTest);
        objectManager.getRumList().add(rumBottle);

        // Pirate movement
        LogUtils.test(Boolean.toString(rumBottle.isVisible()));
        LogUtils.test(pirateToTest.getState().toString());

        objectManager.onRumReappear(rumBottle, 0);

        LogUtils.test(Boolean.toString(rumBottle.isVisible()));
        LogUtils.test(pirateToTest.getState().toString());

        assertTrue(pirateToTest.getState().equals(Character.State.DEAD));
    }

    /**
     * Test of no movement of the pirate with the command (0,0)
     */
     @Test
     public void testPirateNoMovement() {
         // Prepare square around ground
         Brain.getInstance().getMapManager().getMap().getSquareList().
                 get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                 setSquareType(Square.SquareType.GROUND);

         Brain.getInstance().getMapManager().getMap().getSquareList().
                 get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                 setSquareType(Square.SquareType.GROUND);

         Brain.getInstance().getMapManager().getMap().getSquareList().
                 get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                 setSquareType(Square.SquareType.GROUND);

         Brain.getInstance().getMapManager().getMap().getSquareList().
                 get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                 setSquareType(Square.SquareType.GROUND);

         // Init character manager
         CharacterManager characterManager = Brain.getInstance().getCharacterManager();

         // Prepare data test
         Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                 GameConfig.getInstance().getMapHeightY() / 2);

         // Prepare pirate
         Pirate pirateToTest = new Pirate (piratePosition, INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));

         // Prepare Data
         characterManager.getPiratesList().add(pirateToTest);

         characterManager.askMovePirate(pirateToTest.getPort(), 0, 0);

         assertTrue(pirateToTest.getPosition().equals(piratePosition));
     }

    /**
     * Test of Pirate No movement when pirate is dead
     */
    @Test
    public void testMovePirateDead() {
        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate (piratePosition, INIT_PORT_PIRATE, new Energy(0));
        pirateToTest.setState(Character.State.DEAD);

        // Prepare Data
        characterManager.getPiratesList().add(pirateToTest);

        characterManager.askMovePirate(pirateToTest.getPort(), +1, 0);
        LogUtils.test(pirateToTest.getState().toString());

        assertTrue(pirateToTest.getPosition().equals(piratePosition));
    }

    /**
     * Test of clean Pirate move on rum (INVISIBLE) square and stay clean
     */
    @Test
    public void testMovePirateOnRumSquare() {
        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Init manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();
        ObjectManager objectManager = Brain.getInstance().getObjectManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);
        Position rumPosition = new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                GameConfig.getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate (piratePosition, INIT_PORT_PIRATE, new Energy(INIT_ENERGY_PIRATE));

        // Prepare rum
        Rum rumBottle = new Rum (rumPosition, false, ENERGY_TO_TEST);


        // Prepare Data
        characterManager.getPiratesList().add(pirateToTest);
        objectManager.getRumList().add(rumBottle);

        LogUtils.test(pirateToTest.getState().toString());
        LogUtils.test(Integer.toString(pirateToTest.getEnergy().getValue()));
        characterManager.askMovePirate(pirateToTest.getPort(), +1, 0);
        LogUtils.test(Integer.toString(pirateToTest.getEnergy().getValue()));
        LogUtils.test(pirateToTest.getState().toString());

        assertTrue(pirateToTest.getState().equals(Character.State.NORMAL));
    }

    /**
     * Test of drunk Pirate move on rum (VISIBLE) square and stay drunk
     */
    @Test
    public void testMovePirateDrunkOnRumVisibleSquare() {
        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Init manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();
        ObjectManager objectManager = Brain.getInstance().getObjectManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);
        Position rumPosition = new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                GameConfig.getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate (piratePosition, INIT_PORT_PIRATE, new Energy(INIT_ENERGY_SUP_MAX_PIRATE));
        pirateToTest.setState(Character.State.DRUNK);

        // Prepare rum
        Rum rumBottle = new Rum (rumPosition, true, ENERGY_TO_TEST);


        // Prepare Data
        characterManager.getPiratesList().add(pirateToTest);
        objectManager.getRumList().add(rumBottle);

        LogUtils.test(pirateToTest.getState().toString());
        characterManager.askMovePirate(pirateToTest.getPort(), +1, 0);

        LogUtils.test(pirateToTest.getState().toString());

        assertTrue(pirateToTest.getState().equals(Character.State.DRUNK));
    }

    /**
     * Test of drunk Pirate move on rum (INVISIBLE) square and stay drunk
     */
    @Test
    public void testMovePirateDrunkOnRumInvisibleSquare() {
        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Init manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();
        ObjectManager objectManager = Brain.getInstance().getObjectManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);
        Position rumPosition = new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                GameConfig.getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate (piratePosition, INIT_PORT_PIRATE, new Energy(INIT_ENERGY_SUP_MAX_PIRATE));
        pirateToTest.setState(Character.State.DRUNK);

        // Prepare rum
        Rum rumBottle = new Rum (rumPosition, false, ENERGY_TO_TEST);


        // Prepare Data
        characterManager.getPiratesList().add(pirateToTest);
        objectManager.getRumList().add(rumBottle);

        LogUtils.test(pirateToTest.getState().toString());
        LogUtils.test(Integer.toString(pirateToTest.getEnergy().getValue()));
        characterManager.askMovePirate(pirateToTest.getPort(), +1, 0);

        LogUtils.test(pirateToTest.getState().toString());
        LogUtils.test(Integer.toString(pirateToTest.getEnergy().getValue()));
        assertTrue(pirateToTest.getState().equals(Character.State.DRUNK));
    }


    /**
     * Test of drunk Pirate who move and become clean on rum invisible square
     */
    @Test
    public void testMovePirateDrunkAndBecomeClean() {
        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Init manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();
        ObjectManager objectManager = Brain.getInstance().getObjectManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);
        Position rumPosition = new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                GameConfig.getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate (piratePosition, INIT_PORT_PIRATE, new Energy(INIT_ENERGY_MAX_PIRATE + 1));
        pirateToTest.setState(Character.State.DRUNK);

        // Prepare rum
        Rum rumBottle = new Rum (rumPosition, false, ENERGY_TO_TEST);


        // Prepare Data
        characterManager.getPiratesList().add(pirateToTest);
        objectManager.getRumList().add(rumBottle);

        LogUtils.test(pirateToTest.getState().toString());
        LogUtils.test(Integer.toString(pirateToTest.getEnergy().getValue()));
        characterManager.askMovePirate(pirateToTest.getPort(), +1, 0);

        LogUtils.test(pirateToTest.getState().toString());
        LogUtils.test(Integer.toString(pirateToTest.getEnergy().getValue()));
        assertTrue(pirateToTest.getState().equals(Character.State.NORMAL));
    }

    /**
     * Test of rum bottle reapear on Pirate square
     */
    @Test
    public void testRumReappearOnPirateSquare () {
        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Init manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();
        ObjectManager objectManager = Brain.getInstance().getObjectManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2);
        Position rumPosition = new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
                GameConfig.getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate (piratePosition, INIT_PORT_PIRATE, new Energy(INIT_ENERGY_MAX_PIRATE));
        pirateToTest.setState(Character.State.DRUNK);

        // Prepare rum
        Rum rumBottle = new Rum (rumPosition, false, ENERGY_TO_TEST);


        // Prepare Data
        characterManager.getPiratesList().add(pirateToTest);
        objectManager.getRumList().add(rumBottle);

        LogUtils.test(pirateToTest.getState().toString());
        LogUtils.test(Integer.toString(pirateToTest.getEnergy().getValue()));
        characterManager.askMovePirate(pirateToTest.getPort(), +1, 0);

        rumBottle.setVisible(true);

        LogUtils.test(pirateToTest.getState().toString());
        LogUtils.test(Integer.toString(pirateToTest.getEnergy().getValue()));
        assertTrue(pirateToTest.getState().equals(Character.State.NORMAL));
    }


    /**
     * Test of drunk Pirate movement and probability of random movement
     */
    @Test
    public void testProbabilityDrunkPirateMovement() {

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);
        // get test data
        int numberUpDirection = 0;
        int numberDownDirection = 0;
        int numberLeftDirection = 0;
        int numberRightDirection = 0;

        // Prepare the monkey for test
        Pirate pirateOrigin = new Pirate(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), INIT_PORT_PIRATE, new Energy(INIT_OTHER_PORT_PIRATE));
        pirateOrigin.setState(Character.State.DRUNK);

        Pirate pirateToTest = new Pirate(new Position(GameConfig.getInstance().getMapWidthX() / 2,
                GameConfig.getInstance().getMapHeightY() / 2), INIT_PORT_PIRATE, new Energy(INIT_OTHER_PORT_PIRATE));
        pirateToTest.setState(Character.State.DRUNK);

        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();
        characterManager.getPiratesList().add(pirateToTest);
        LogUtils.test("characterManager.getPiratesList().size = " + characterManager.getPiratesList().size());
        LogUtils.test("Pirate energy = " + characterManager.getPiratesList().get(0).getEnergy().getValue());

        try {
            for (int i = 0; i < NUMBER_OF_TEST; i++) {
                characterManager.getPiratesList().get(0).setPosition(new Position(pirateOrigin.getPosition()));

                Method askMovePirate = characterManager.
                        getClass().getDeclaredMethod(ASK_MOVE_PIRATE, int.class, int.class, int.class);
                askMovePirate.setAccessible(true);
                askMovePirate.invoke(characterManager, INIT_PORT_PIRATE,1,0);

                CharacterManager.Direction direction = getDirectionMove(
                        pirateOrigin.getPosition(), characterManager.getPiratesList().get(0).getPosition());

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
     * Test right down diagonal pirate move : -> impossible
     */
    @Test
    public void testMovePirateDrunk() {
        // Init character manager
        CharacterManager characterManager = Brain.getInstance().getCharacterManager();

        // Prepare data test
        Position piratePosition = new Position(GameConfig.getInstance().getMapWidthX() / 2, GameConfig.
                getInstance().getMapHeightY() / 2);

        // Prepare pirate
        Pirate pirateToTest = new Pirate(new Position(piratePosition), INIT_PORT_PIRATE,
                new Energy(INIT_ENERGY_SUP_MAX_PIRATE));

        // Prepare square around ground
        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 - 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2 + 1).get(GameConfig.getInstance().getMapHeightY() / 2).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 - 1).
                setSquareType(Square.SquareType.GROUND);

        Brain.getInstance().getMapManager().getMap().getSquareList().
                get(GameConfig.getInstance().getMapWidthX() / 2).get(GameConfig.getInstance().getMapHeightY() / 2 + 1).
                setSquareType(Square.SquareType.GROUND);

        // Add pirate to list
        characterManager.getPiratesList().add(pirateToTest);
        pirateToTest.setState(Character.State.DRUNK);

        LogUtils.test(pirateToTest.getState().toString());

//        Position positionToTest = new Position(GameConfig.getInstance().getMapWidthX() / 2 + 1,
//                GameConfig.getInstance().getMapHeightY() / 2 - 1);

        // Test right diagonal direction
        characterManager.askMovePirate(pirateToTest.getPort(), +1, 0);

    }



    /**
     * method tearDown() called at the end of PirateTest class
     * @throws Exception an unexpected exception
     */
    @After
    public void tearDown() throws Exception {
    }
}
