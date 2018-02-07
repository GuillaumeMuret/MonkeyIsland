/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.test.model.constant.test;

import junit.framework.TestSuite;
import org.junit.Test;

public class ConstantTestSuite extends TestSuite {
    
    /**
     * Test suite for package constant for monkey Island
     *
     * @return test suite
     */
    @Test
    public static TestSuite suite() {
        final TestSuite suite = new TestSuite("Test suite for constant package");
        
        suite.addTest(new TestSuite(GameConfigTest.class));
        
        return suite;
    }
}