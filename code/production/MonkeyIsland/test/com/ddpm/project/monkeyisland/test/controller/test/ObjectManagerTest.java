/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.test.controller.test;

import junit.framework.TestCase;
import org.junit.Test;

public class ObjectManagerTest extends TestCase {
    /**
     * method setUp() called before each tests
     * @throws Exception an unexpected exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    /**
     * Test the process todooooo
     */
    @Test
    public void testToDoooo() {
        assertEquals(1,1);
        /*
        processOutTest=ProcessOut.BRAIN_ASK_READY;
        assertEquals(processOutTest.process,"askReady");
        assertEquals(processOutTest.numOwner,1);
        assertEquals(processOutTest.owner,"Brain");
        */
    }

    /**
     * Test Treasure discovery
     */
    @Test
    public void testTreasureDiscovery () {
        assertEquals(1,1);
    }

    /**
     * method tearDown of ObjectManagerTest
     * @throws Exception an unexpected exception
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
