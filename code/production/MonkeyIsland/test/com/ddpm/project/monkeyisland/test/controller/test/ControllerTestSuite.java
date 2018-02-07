/**
 * @Author : Guillaume Muret
 */

package com.ddpm.project.monkeyisland.test.controller.test;

import com.ddpm.project.monkeyisland.test.controller.test.CharacterManagerTest.MonkeyTest;
import com.ddpm.project.monkeyisland.test.controller.test.CharacterManagerTest.PirateTest;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;


@RunWith(Suite.class)
@PowerMockRunnerDelegate(PowerMockRunner.class)
@Suite.SuiteClasses({
    MonkeyTest.class,
    PirateTest.class,
    ObjectManagerTest.class,
    MapManagerTest.class
})

public class ControllerTestSuite extends TestSuite {
}
