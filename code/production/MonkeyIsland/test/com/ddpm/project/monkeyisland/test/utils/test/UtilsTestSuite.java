/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.test.utils.test;


import junit.framework.TestSuite;
import org.junit.Test;

public class UtilsTestSuite extends TestSuite {

    /**
     * Test for the utils
     * @return test suite
     */
    @Test
    public static TestSuite suite() {
        final TestSuite suite = new TestSuite("Test suite for utils package");

        suite.addTest(new TestSuite(PathFindHunterUtilsTest.class));

        return suite;
    }
}
