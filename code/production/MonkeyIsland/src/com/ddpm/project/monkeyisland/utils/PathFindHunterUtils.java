/**
 * @Author : Cailyn Davies
 */

package com.ddpm.project.monkeyisland.utils;

import com.ddpm.project.monkeyisland.controller.Brain;
import com.ddpm.project.monkeyisland.model.constant.GameConfig;
import com.ddpm.project.monkeyisland.model.game.character.Character;
import com.ddpm.project.monkeyisland.model.game.map.Square;
import com.ddpm.project.monkeyisland.model.game.position.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;


public class PathFindHunterUtils {

    /**
     * Calculates the position a hunter monkey must go to in order to get closer to the pirate
     * @param start : monkeys starting position
     * @param brain : used to get square types and character positions
     * @return the square the monkey must move to
     */
    public static Position calculateHunterMove(Position start, Brain brain) {
        ArrayList<Position> list = new ArrayList<>();
        ArrayList<Position> used = new ArrayList<>();
        HashMap<Position, Position> parents = new HashMap<>();
        list.add(start);
        used.add(start);
        getValidNeighbours(list, used, parents, brain);
        ArrayList<Position> movableSpaces = new ArrayList<>();
        for (Position position : list) {
            movableSpaces.add(new Position(position.getPositionX(),position.getPositionY()));
        }
        Position goTo;

        if (movableSpaces.size() == 0) { // If the monkey can't move, stay in the same place
            goTo = start;
        } else if (movableSpaces.size() == 1) { // If the monkey can only go to one place, go there
            goTo = list.get(0);
        } else { // If the monkey must make a choice between paths
            Boolean found = false;
            Position pos = null;
            while ((!found) && (list.size() != 0)) {
                pos = containsPirate(list, brain);
                if (pos == null) { // If no pirate was found, keep going
                    getValidNeighbours(list, used, parents, brain);
                } else { // If a pirate was found
                    found = true;
                    //climb back up the parental tree to find the position the monkey must move to
                    while ((!movableSpaces.contains(pos)) && (pos != null)) {
                        pos =  parents.get(pos);
                    }
                }

            }
            if (!movableSpaces.contains(pos)) {
                // If a path can't be found, do a random move in any direction the monkey can go to
                int rnd = ThreadLocalRandom.current().nextInt(0, movableSpaces.size());
                pos = movableSpaces.get(rnd);
            }
            goTo = pos;
        }
        return goTo;
    }

    /**
     * method used to find a pirate in the given squares. Returns null if no pirate in the given squares
     * @param list list of squares to look in
     * @param brain used to see if a pirate is on the position
     * @return position of a pirate or null if none available
     */
    private static Position containsPirate(ArrayList<Position> list, Brain brain) {
        for (Position pos : list) {
            if (brain.getCharacterManager().getPirateOnPosition(pos) != null) {
                return pos;
            }
        }
        return null;
    }

    /**
     * calls other functions to find the new positions using a breadth search first algorithm
     * @param currentPositions : the positions we have all ready searched and will search from
     * @param usedPositions : positions all ready used by the path find (including currentPositions)
     * @param parents : the parenting tree hash map to add the valid positions to
     * @param brain : used to find and verify the types of squares in the newPositions
     */
    private static void getValidNeighbours(
            ArrayList<Position> currentPositions,
            ArrayList<Position> usedPositions,
            HashMap<Position, Position> parents,
            Brain brain) {
        ArrayList<Position> newPositions = new ArrayList<>();
        ArrayList<Position> neighbours = new ArrayList<>();

        for (Position position : currentPositions) {
            getNeighbours(position, neighbours);
            removeChecked(neighbours, usedPositions);
            removeImpossibleMonkeyPositions(neighbours, brain);
            setParents(position, neighbours, parents);
            for (Position newPosition : neighbours) {
                newPositions.add(newPosition);
                usedPositions.add(newPosition);
            }
        }
        ArrayList<Position> tmpList = new ArrayList<>();
        for (Position oldPosition : currentPositions) {
            tmpList.add(oldPosition);
        }
        for (Position oldPosition : tmpList) {
            currentPositions.remove(oldPosition);
        }
        for (Position newPosition : newPositions) {
            currentPositions.add(newPosition);
        }
    }

    /**
     * Returns a list of positions neighbouring position
     * @param position : the central point
     * @param neighbours : the arrayList of neighbours
     */
    private static void getNeighbours(Position position, ArrayList<Position> neighbours) {
        Position left = new Position(position.getPositionX() - 1, position.getPositionY());
        Position right = new Position(position.getPositionX() + 1, position.getPositionY());
        Position up = new Position(position.getPositionX(), position.getPositionY() + 1);
        Position down = new Position(position.getPositionX(), position.getPositionY() - 1);
        neighbours.add(left);
        neighbours.add(right);
        neighbours.add(up);
        neighbours.add(down);
    }

    /**
     * removes all the positions in usedPositions from neighbours
     * used to take away all ready checked spaces for the pathfind
     * @param neighbours : a list containing positions
     * @param usedPositions : a list containing positions
     */
    private static void removeChecked(ArrayList<Position> neighbours, ArrayList<Position> usedPositions) {
        ArrayList<Position> removePositions = new ArrayList<>();
        for (Position used : usedPositions) {
            for (Position position : neighbours) {
                if (used.isEquals(position)) {
                    removePositions.add(position);
                }
            }
        }
        neighbours.removeAll(removePositions);
    }


    /**
     * Removes positions where the monkey cannot move (other monkey positions or sea squares)
     * from the neighbours list
     * @param neighbours : list of neighbours to remove squares from
     * @param brain : used to get the square types in the map
     */
    private static void removeImpossibleMonkeyPositions(ArrayList<Position> neighbours, Brain brain) {
        ArrayList<Position> removePositions = new ArrayList<>();
        for (Position position : neighbours) {
            if ((position.getPositionX() < 0) || (position.getPositionX() > GameConfig.getInstance().getMapWidthX())) {
                removePositions.add(position);
            } else if ((position.getPositionY() < 0)
                    || (position.getPositionY() > GameConfig.getInstance().getMapHeightY())) {
                removePositions.add(position);
            } else if (PositionUtils.isMyTypeOnThisPosition(brain, Character.CharacterType.MONKEY, position)) {
                removePositions.add(position);
            } else if (Square.SquareType.SEA == brain.getMapManager().getSquareType(position)) {
                removePositions.add(position);
            }
        }
        neighbours.removeAll(removePositions);
    }

    /**
     * Adds the list of neighbours as children to the position start in the parents hashmap
     * @param start : parent position
     * @param neighbours : child positions
     * @param parents : hashmap to add the new relations to
     */
    private static void setParents(Position start, ArrayList<Position> neighbours, HashMap<Position, Position> parents) {
        for (Position position : neighbours) {
            parents.put(position, start);
        }
    }
}
