/**
 * @Author : Cailyn Davies
 */
package com.ddpm.project.monkeyisland.test.utils.test;

import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.controller.CharacterManager;
import com.ddpm.project.monkeyisland.controller.MapManager;
import com.ddpm.project.monkeyisland.controller.ObjectManager;
import com.ddpm.project.monkeyisland.model.game.attribute.Energy;
import com.ddpm.project.monkeyisland.model.game.character.Monkey;
import com.ddpm.project.monkeyisland.model.game.character.Pirate;
import com.ddpm.project.monkeyisland.model.game.map.Square;
import com.ddpm.project.monkeyisland.model.game.position.Position;
import com.ddpm.project.monkeyisland.utils.PathFindHunterUtils;
import junit.framework.TestCase;
import org.junit.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class PathFindHunterUtilsTest extends TestCase {

    /**
     * pirate's default port
     */
    private static final int INIT_PORT_PIRATE = 8020;

    /**
     * method name
     */
    private static final String SET_PARENTS = "setParents";

    /**
     * method name
     */
    private static final String REMOVE_CHECKED = "removeChecked";

    /**
     * method name
     */
    private static final String REMOVE_IMPOSSIBLE = "removeImpossibleMonkeyPositions";

    /**
     * method name
     */
    private static final String GET_NEIGHBOURS = "getNeighbours";

    /**
     * method name
     */
    private static final String GET_VALID_NEIGHBOURS = "getValidNeighbours";

    /**
     * method name
     */
    private static final String CONTAINS_PIRATE = "containsPirate";

    /**
     * method name
     */
    private static final String CALCULATE_HUNTER_MOVE = "calculateHunterMove";

    /**
     * constant used
     */
    private static final int INT4 = 4;

    /**
     * constant used
     */
    private static final int INT5 = 5;

    /**
     * constant used
     */
    private static final int INT6 = 6;

    /**
     * constant used
     */
    private static final int INT10 = 10;

    /**
     * constant used
     */
    private static final int INT11 = 11;

    /**
     * constant used
     */
    private static final int INT12 = 12;

    /**
     * constant used
     */
    private static final int INT13 = 13;

    /**
     * constant used
     */
    private static final int INT14 = 14;

    /**
     * constant used
     */
    private static final int INT15 = 15;

    /**
     * constant used
     */
    private static final int INT16 = 16;

    /**
     * constant used
     */
    private static final int INT17 = 17;

    /**
     * constant used
     */
    private static final int INT18 = 18;

    /**
     * method called before each test
     * @throws Exception exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Test the setParents method
     */
    public void testSetParents() {
        ArrayList<Position> firstChildren = new ArrayList<>();
        ArrayList<Position> secondChildren = new ArrayList<>();
        HashMap<Position, Position> tree = new HashMap<>();

        Position firstChild = new Position(1,2);
        firstChildren.add(firstChild);
        Position secondChild = new Position(2,INT5);
        firstChildren.add(secondChild);
        Position thirdChild = new Position(INT13,INT15);
        Position thirdChild2 = new Position(INT13,INT15);
        secondChildren.add(thirdChild);
        Position firstParent = new Position(INT4,INT6);
        secondChildren.add(firstParent);
        Position secondParent = new Position(INT18,INT12);

        try {
            Method method = PathFindHunterUtils.class.
                    getDeclaredMethod(SET_PARENTS, Position.class, ArrayList.class, HashMap.class);

            method.setAccessible(true);
            method.invoke(PathFindHunterUtils.class, firstParent, firstChildren, tree);
            method.invoke(PathFindHunterUtils.class, secondParent, secondChildren, tree);

            // Test
            Assert.assertEquals(tree.get(firstChild), firstParent);
            Assert.assertEquals(tree.get(secondChild), firstParent);
            Assert.assertEquals(tree.get(thirdChild), secondParent);
            Assert.assertEquals(tree.get(thirdChild2),secondParent);
            Assert.assertEquals(tree.get(firstParent), secondParent);
            Assert.assertEquals(tree.get(secondParent), null);
            Assert.assertNotEquals(tree.get(firstChild), secondParent);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the removeChecked method
     */
    public void testRemoveChecked() {
        ArrayList<Position> posList = new ArrayList<>();

        Position pos1 = new Position(INT18,INT13);
        posList.add(pos1);
        Position bothPos3 = new Position(1,2);
        posList.add(bothPos3);
        posList.add(bothPos3);
        Position pos4 = new Position(2,INT5);
        posList.add(pos4);
        posList.add(pos4);
        Position pos5 = new Position(INT4,INT15);
        posList.add(pos5);
        posList.add(pos5);

        ArrayList<Position> usedList = new ArrayList<>();
        Position usedPos2 = new Position(INT4,INT6);
        usedList.add(usedPos2);
        usedList.add(bothPos3);
        Position usedPos5 = new Position(INT4,INT15);
        usedList.add(usedPos5);

        try {
            Method method = PathFindHunterUtils.class.
                    getDeclaredMethod(REMOVE_CHECKED, ArrayList.class, ArrayList.class);

            method.setAccessible(true);
            method.invoke(PathFindHunterUtils.class, posList, usedList);

            // Test
            Assert.assertFalse(posList.contains(usedPos2));
            Assert.assertFalse(posList.contains(bothPos3));
            Assert.assertFalse(posList.contains(usedPos5));
            Assert.assertFalse(posList.contains(pos5));
            Assert.assertTrue(posList.contains(pos1));
            Assert.assertTrue(posList.contains(pos4));

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the removeImpossibleMonkeyPositions method
     */
    public void testRemoveImpossibleMonkeyPositions() {
        Brain brain = Brain.getInstance();

        brain.setMapManager(MapManager.getInstance(brain));
        brain.setObjectManager(ObjectManager.getInstance(brain));
        brain.setCharacterManager(CharacterManager.getInstance(brain));

        brain.notifyInitAll();

        ArrayList<Position> positions = new ArrayList<>();

        Position erratic = new Position(INT13,INT14);
        positions.add(erratic);
        Position hunter = new Position(INT11,INT16);
        positions.add(hunter);
        Position rum = new Position(INT12,3);
        positions.add(rum);
        Position ground = new Position(INT18,1);
        positions.add(ground);
        Position sea = new Position(INT18,0);
        positions.add(sea);
        Position sea2 = new Position(INT18,0);

        try {
            Method method = PathFindHunterUtils.class.
                    getDeclaredMethod(REMOVE_IMPOSSIBLE, ArrayList.class, Brain.class);

            method.setAccessible(true);
            method.invoke(PathFindHunterUtils.class, positions, brain);

            // Test
            Assert.assertFalse(positions.contains(erratic));
            Assert.assertFalse(positions.contains(hunter));
            Assert.assertFalse(positions.contains(sea));
            Assert.assertFalse(positions.contains(sea2));
            Assert.assertTrue(positions.contains(rum));
            Assert.assertTrue(positions.contains(ground));

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the getNeighbours method
     */
    public void testGetNeighbours() {
        Position start1 = new Position(INT5,INT5);
        ArrayList<Position> supposedNeighbours1 = new ArrayList<>();
        supposedNeighbours1.add(new Position(INT4,INT5));
        supposedNeighbours1.add(new Position(INT6,INT5));
        supposedNeighbours1.add(new Position(INT5,INT6));
        supposedNeighbours1.add(new Position(INT5,INT4));

        Position start2 = new Position(INT5,INT13);
        ArrayList<Position> supposedNeighbours2 = new ArrayList<>();
        supposedNeighbours2.add(new Position(INT4,INT13));
        supposedNeighbours2.add(new Position(INT6,INT13));
        supposedNeighbours2.add(new Position(INT5,INT14));
        supposedNeighbours2.add(new Position(INT5,INT12));

        try {
            Method method = PathFindHunterUtils.class.
                    getDeclaredMethod(GET_NEIGHBOURS, Position.class, ArrayList.class);

            method.setAccessible(true);
            ArrayList<Position> neighbours1 = new ArrayList<>();
            ArrayList<Position> neighbours2 = new ArrayList<>();
            method.invoke(PathFindHunterUtils.class, start1, neighbours1);
            method.invoke(PathFindHunterUtils.class, start2, neighbours2);

            // Test

            Assert.assertEquals(supposedNeighbours1, neighbours1);
            Assert.assertEquals(supposedNeighbours2, neighbours2);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the getValidNeighbours method
     */
    public void testGetValidNeighbours() {
        Brain brain = Brain.getInstance();
        brain.setMapManager(MapManager.getInstance(brain));
        brain.setObjectManager(ObjectManager.getInstance(brain));
        brain.setCharacterManager(CharacterManager.getInstance(brain));

        brain.notifyInitAll();

        ArrayList<Position> usedPositions = new ArrayList<>();
        ArrayList<Position> supposedNewUsedPositions = new ArrayList<>();
        ArrayList<Position> supposedNewCurrentPositions = new ArrayList<>();
        ArrayList<Position> currentPositions = new ArrayList<>();
        HashMap<Position,Position> supposedNewParents = new HashMap<>();
        HashMap<Position,Position> parents = new HashMap<>();

        testGetValidNeighboursInit(usedPositions,supposedNewUsedPositions,
                currentPositions,supposedNewCurrentPositions,supposedNewParents, parents);

        try {
            Method method = PathFindHunterUtils.class.
                    getDeclaredMethod(GET_VALID_NEIGHBOURS, ArrayList.class, ArrayList.class, HashMap.class, Brain.class);

            method.setAccessible(true);
            method.invoke(PathFindHunterUtils.class, currentPositions, usedPositions, parents, brain);

            // Test
            Assert.assertEquals(supposedNewCurrentPositions, currentPositions);
            Assert.assertEquals(supposedNewUsedPositions, usedPositions);
            Assert.assertEquals(supposedNewParents, parents);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialisation function for the getValidNeighbours test lists, used to invoke and test the function
     * @param usedPositions list to initialise
     * @param supposedNewUsedPositions list to initialise
     * @param currentPositions list to initialise
     * @param supposedNewCurrentPositions list to initialise
     * @param supposedNewParents list to initialise
     * @param parents list to initialise
     */
    private void testGetValidNeighboursInit(ArrayList<Position> usedPositions,
                                           ArrayList<Position> supposedNewUsedPositions,
                                           ArrayList<Position> currentPositions,
                                           ArrayList<Position> supposedNewCurrentPositions,
                                           HashMap<Position,Position> supposedNewParents,
                                           HashMap<Position,Position> parents) {

        Position used1 = new Position(INT14,INT15);
        usedPositions.add(used1);
        supposedNewUsedPositions.add(used1);
        Position used2 = new Position(INT14,INT18);
        usedPositions.add(used2);
        supposedNewUsedPositions.add(used2);


        Position currentPosition1 = new Position(INT13,INT14);
        currentPositions.add(currentPosition1);
        usedPositions.add(currentPosition1);
        supposedNewUsedPositions.add(currentPosition1);
        Position currentPosition2 = new Position(INT14,INT14);
        currentPositions.add(currentPosition2);
        usedPositions.add(currentPosition2);
        supposedNewUsedPositions.add(currentPosition2);
        Position currentPosition3 = new Position(INT15,INT14);
        currentPositions.add(currentPosition3);
        usedPositions.add(currentPosition3);
        supposedNewUsedPositions.add(currentPosition3);
        Position currentPosition4 = new Position(INT13,INT15);
        currentPositions.add(currentPosition4);
        usedPositions.add(currentPosition4);
        supposedNewUsedPositions.add(currentPosition4);
        Position currentPosition5 = new Position(INT15,INT15);
        currentPositions.add(currentPosition5);
        usedPositions.add(currentPosition5);
        supposedNewUsedPositions.add(currentPosition5);
        Position currentPosition6 = new Position(INT13,INT16);
        currentPositions.add(currentPosition6);
        usedPositions.add(currentPosition6);
        supposedNewUsedPositions.add(currentPosition6);
        Position currentPosition7 = new Position(INT14,INT16);
        currentPositions.add(currentPosition7);
        usedPositions.add(currentPosition7);
        supposedNewUsedPositions.add(currentPosition7);
        Position currentPosition8 = new Position(INT15,INT16);
        currentPositions.add(currentPosition8);
        usedPositions.add(currentPosition8);
        supposedNewUsedPositions.add(currentPosition8);

        for (Position position : currentPositions) {
            parents.put(position, used1);
            supposedNewParents.put(position, used1);
        }

        Position currentPosition9 = new Position(INT13,INT18);
        currentPositions.add(currentPosition9);
        usedPositions.add(currentPosition9);
        supposedNewUsedPositions.add(currentPosition9);
        parents.put(currentPosition9, used2);
        supposedNewParents.put(currentPosition9, used2);


        Position newPosition1 = new Position(INT12,INT14);
        supposedNewCurrentPositions.add(newPosition1);
        supposedNewUsedPositions.add(newPosition1);
        supposedNewParents.put(newPosition1, currentPosition1);
        Position newPosition2 = new Position(INT13,INT13);
        supposedNewCurrentPositions.add(newPosition2);
        supposedNewUsedPositions.add(newPosition2);
        supposedNewParents.put(newPosition2, currentPosition1);
        Position newPosition3 = new Position(INT14,INT13);
        supposedNewCurrentPositions.add(newPosition3);
        supposedNewUsedPositions.add(newPosition3);
        supposedNewParents.put(newPosition3, currentPosition2);
        Position newPosition4 = new Position(INT16,INT14);
        supposedNewCurrentPositions.add(newPosition4);
        supposedNewUsedPositions.add(newPosition4);
        supposedNewParents.put(newPosition4, currentPosition3);
        Position newPosition5 = new Position(INT15,INT13);
        supposedNewCurrentPositions.add(newPosition5);
        supposedNewUsedPositions.add(newPosition5);
        supposedNewParents.put(newPosition5, currentPosition3);
        Position newPosition6 = new Position(INT12,INT15);
        supposedNewCurrentPositions.add(newPosition6);
        supposedNewUsedPositions.add(newPosition6);
        supposedNewParents.put(newPosition6, currentPosition4);
        Position newPosition7 = new Position(INT16,INT15);
        supposedNewCurrentPositions.add(newPosition7);
        supposedNewUsedPositions.add(newPosition7);
        supposedNewParents.put(newPosition7, currentPosition5);
        Position newPosition8 = new Position(INT12,INT16);
        supposedNewCurrentPositions.add(newPosition8);
        supposedNewUsedPositions.add(newPosition8);
        supposedNewParents.put(newPosition8, currentPosition6);
        Position newPosition9 = new Position(INT13,INT17);
        supposedNewCurrentPositions.add(newPosition9);
        supposedNewUsedPositions.add(newPosition9);
        supposedNewParents.put(newPosition9, currentPosition6);
        Position newPosition10 = new Position(INT14,INT17);
        supposedNewCurrentPositions.add(newPosition10);
        supposedNewUsedPositions.add(newPosition10);
        supposedNewParents.put(newPosition10, currentPosition7);
        Position newPosition11 = new Position(INT16,INT16);
        supposedNewCurrentPositions.add(newPosition11);
        supposedNewUsedPositions.add(newPosition11);
        supposedNewParents.put(newPosition11, currentPosition8);
        Position newPosition12 = new Position(INT15,INT17);
        supposedNewCurrentPositions.add(newPosition12);
        supposedNewUsedPositions.add(newPosition12);
        supposedNewParents.put(newPosition12, currentPosition8);
        Position newPosition13 = new Position(INT12,INT18);
        supposedNewCurrentPositions.add(newPosition13);
        supposedNewUsedPositions.add(newPosition13);
        supposedNewParents.put(newPosition13, currentPosition9);
    }

    /**
     * Test the containsPirate method
     */
    public void testContainsPirate() {
        Brain brain = Brain.getInstance();
        brain.setMapManager(MapManager.getInstance(brain));
        brain.setObjectManager(ObjectManager.getInstance(brain));
        brain.setCharacterManager(CharacterManager.getInstance(brain));

        brain.notifyInitAll();

        Position piratePosition = new Position(INT12,INT15);
        Pirate pirate = new Pirate(piratePosition,INIT_PORT_PIRATE,new Energy(INT15));
        brain.getCharacterManager().getPiratesList().add(pirate);

        Position position1 = new Position(INT4,INT12);

        ArrayList<Position> pirateList = new ArrayList<>();
        pirateList.add(position1);
        pirateList.add(piratePosition);
        ArrayList<Position> anotherPirateList = new ArrayList<>();
        Position position3 = new Position(INT14,INT11);
        Position samePosition = new Position(INT12,INT15);
        anotherPirateList.add(position3);
        anotherPirateList.add(samePosition);
        ArrayList<Position> nonPirateList = new ArrayList<>();
        nonPirateList.add(position1);
        nonPirateList.add(position3);

        try {
            Method method = PathFindHunterUtils.class.
                    getDeclaredMethod(CONTAINS_PIRATE, ArrayList.class, Brain.class);

            method.setAccessible(true);
            Position pos1 = (Position) method.invoke(PathFindHunterUtils.class, pirateList, brain);
            Position pos2 = (Position) method.invoke(PathFindHunterUtils.class, anotherPirateList, brain);
            Position pos3 = (Position) method.invoke(PathFindHunterUtils.class, nonPirateList, brain);

            // Test
            Assert.assertEquals(pos1, piratePosition);
            Assert.assertEquals(pos2, piratePosition);
            Assert.assertNotEquals(pos3, piratePosition);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the containsPirate method
     */
    public void testCalculateHunterMove() {
        Brain brain = Brain.getInstance();
        brain.setMapManager(MapManager.getInstance(brain));
        brain.setObjectManager(ObjectManager.getInstance(brain));
        brain.setCharacterManager(CharacterManager.getInstance(brain));

        brain.notifyInitAll();

        Position piratePosition = new Position(INT11,INT11);
        Pirate pirate = new Pirate(piratePosition,INIT_PORT_PIRATE,new Energy(INT15));
        brain.getCharacterManager().getPiratesList().add(pirate);

        ArrayList<Monkey> monkeyList = new ArrayList<>();

        Monkey monkey = new Monkey(new Position(INT13,INT14), Monkey.MonkeyType.ERRATIC);
        Monkey monkey2 = new Monkey(new Position(INT15,INT13), Monkey.MonkeyType.HUNTER);
        monkeyList.add(monkey);
        monkeyList.add(monkey2);

        brain.getCharacterManager().setMonkeyList(monkeyList);

        for (int i = 0; i < brain.getMapManager().getMap().getWidth(); i++) {
            for (int j = 0; j < brain.getMapManager().getMap().getHeight(); j++) {
                brain.getMapManager().getMap().getSquareList().get(i).get(j).setSquareType(Square.SquareType.GROUND);
            }
        }

        brain.getMapManager().getMap().getSquareList().get(INT12).get(INT12).setSquareType(Square.SquareType.SEA);
        brain.getMapManager().getMap().getSquareList().get(INT12).get(INT13).setSquareType(Square.SquareType.SEA);
        brain.getMapManager().getMap().getSquareList().get(INT12).get(INT14).setSquareType(Square.SquareType.SEA);
        brain.getMapManager().getMap().getSquareList().get(INT12).get(INT15).setSquareType(Square.SquareType.SEA);
        brain.getMapManager().getMap().getSquareList().get(INT13).get(INT12).setSquareType(Square.SquareType.SEA);
        brain.getMapManager().getMap().getSquareList().get(INT14).get(INT12).setSquareType(Square.SquareType.SEA);
        brain.getMapManager().getMap().getSquareList().get(INT15).get(INT12).setSquareType(Square.SquareType.SEA);
        brain.getMapManager().getMap().getSquareList().get(INT16).get(INT12).setSquareType(Square.SquareType.SEA);

        Position monkeyPosition = new Position(INT13,INT13);

        try {
            Method method = PathFindHunterUtils.class.
                    getDeclaredMethod(CALCULATE_HUNTER_MOVE, Position.class, Brain.class);

            Position pos1 = (Position) method.invoke(PathFindHunterUtils.class, monkeyPosition, brain);
            Position pos2 = (Position) method.invoke(PathFindHunterUtils.class, pos1, brain);
            Position pos3 = (Position) method.invoke(PathFindHunterUtils.class, pos2, brain);
            Position pos4 = (Position) method.invoke(PathFindHunterUtils.class, pos3, brain);
            Position pos5 = (Position) method.invoke(PathFindHunterUtils.class, pos4, brain);
            Position pos6 = (Position) method.invoke(PathFindHunterUtils.class, pos5, brain);
            Position pos7 = (Position) method.invoke(PathFindHunterUtils.class, pos6, brain);
            Position pos8 = (Position) method.invoke(PathFindHunterUtils.class, pos7, brain);
            Position pos9 = (Position) method.invoke(PathFindHunterUtils.class, pos8, brain);
            Position pos10 = (Position) method.invoke(PathFindHunterUtils.class, pos9, brain);
            Position pos11 = (Position) method.invoke(PathFindHunterUtils.class, pos10, brain);
            Position pos12 = (Position) method.invoke(PathFindHunterUtils.class, pos11, brain);
            Position pos13 = (Position) method.invoke(PathFindHunterUtils.class, pos12, brain);
            Position pos14 = (Position) method.invoke(PathFindHunterUtils.class, pos13, brain);

            // Test
            Assert.assertEquals(new Position(INT14, INT13), pos1);
            Assert.assertEquals(new Position(INT14, INT14), pos2);
            Assert.assertEquals(new Position(INT14, INT15), pos3);
            Assert.assertEquals(new Position(INT13, INT15), pos4);
            Assert.assertEquals(new Position(INT13, INT16), pos5);
            Assert.assertEquals(new Position(INT12, INT16), pos6);
            Assert.assertEquals(new Position(INT11, INT16), pos7);
            Assert.assertEquals(new Position(INT11, INT15), pos8);
            Assert.assertEquals(new Position(INT11, INT14), pos9);
            Assert.assertEquals(new Position(INT11, INT13), pos10);
            Assert.assertEquals(new Position(INT11, INT12), pos11);
            Assert.assertEquals(new Position(INT11, INT11), pos12);
            Assert.assertEquals(new Position(INT11, INT11), pos14);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method called at end of each test
     * @throws Exception exception
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
