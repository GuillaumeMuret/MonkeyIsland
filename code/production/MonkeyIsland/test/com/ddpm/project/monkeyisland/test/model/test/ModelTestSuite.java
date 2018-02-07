/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.test.model.test;

import com.ddpm.project.monkeyisland.test.model.constant.test.ConstantTestSuite;
import junit.framework.TestSuite;
import org.junit.Test;

public class ModelTestSuite extends TestSuite {
    
    /**
     * Test suite for package model of monkey Island
     *
     * @return test suite
     */
    @Test
    public static TestSuite suite() {
        final TestSuite suite = new TestSuite("Test suite for model package");
        
        suite.addTest(ConstantTestSuite.suite());
        
        return suite;
    }
}
