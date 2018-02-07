/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.test;

import com.ddpm.project.monkeyisland.test.controller.test.ControllerTestSuite;
import com.ddpm.project.monkeyisland.test.utils.test.UtilsTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

@RunWith(Suite.class)
@PowerMockRunnerDelegate(PowerMockRunner.class)
@Suite.SuiteClasses({
    ControllerTestSuite.class,
    UtilsTestSuite.class
})

public class MonkeyIslandTestSuite {
}