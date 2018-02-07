/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.test.model.constant.test;

import com.ddpm.project.monkeyisland.model.constant.GameConfig;
import com.ddpm.project.monkeyisland.model.game.map.Square;
import com.ddpm.project.monkeyisland.utils.FileUtils;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameConfigTest extends TestCase {
    
    @Override
    @Before
    protected void setUp() throws Exception {
        super.setUp();
        FileUtils.loadGameConfiguration();
    }
    
    /**
     * Test the map square list
     */
    @Test
    public void testMapSquareList() {
        assertNotNull(GameConfig.getInstance().getSquaresList());
        assertEquals(GameConfig.getInstance().getMapWidthX(), GameConfig.getInstance().getSquaresList().size());
        assertEquals(GameConfig.getInstance().getMapHeightY(), GameConfig.getInstance().getSquaresList().get(0).size());
        for (int i = 0; i < GameConfig.getInstance().getMapWidthX(); i++) {
            for (int j = 0; j < GameConfig.getInstance().getMapHeightY(); j++) {
                Square square = GameConfig.getInstance().getSquaresList().get(i).get(j);
                assertTrue(
                    square.getSquareType() == Square.SquareType.GROUND
                    || square.getSquareType() == Square.SquareType.SEA
                );
            }
        }
    }
    
    /**
     * Test the rum list
     */
    @Test
    public void testRumList() {
        assertNotNull(GameConfig.getInstance().getRumList());
    }
    
    /**
     * Test the monkey list
     */
    @Test
    public void testMonkeyList() {
        assertNotNull(GameConfig.getInstance().getRumList());
    }
    
    /**
     * Test the rum energy value
     */
    @Test
    public void testRumEnergyValue() {
        assertTrue(GameConfig.getInstance().getRumEnergyValue() > 0);
    }
    
    /**
     * Test the monkey erratic speed
     */
    @Test
    public void testMonkeyErraticSpeed() {
        assertTrue(GameConfig.getInstance().getMonkeyErraticSpeed() > 0);
    }
    
    /**
     * Test the money hunter speed
     */
    @Test
    public void testMonkeyHunterSpeed() {
        assertTrue(GameConfig.getInstance().getMonkeyHunterSpeed() > 0);
    }
    
    @Override
    @After
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}