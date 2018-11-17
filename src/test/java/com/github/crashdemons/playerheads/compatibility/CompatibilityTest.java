/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility;

import com.github.crashdemons.playerheads.testutils.Mocks;
import com.github.crashdemons.playerheads.testutils.TestOutput;
import org.bukkit.Bukkit;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
@RunWith(PowerMockRunner.class)
public class CompatibilityTest {
    private final TestOutput out=new TestOutput(this);
    public CompatibilityTest() {
        Mocks.setupFakeServerVersion();
    }

    @Test
    @PrepareForTest({Compatibility.class,Bukkit.class})
    public void testInit() {
        out.println("init");
        Compatibility.init();
    }

    @Test
    @PrepareForTest({Compatibility.class,Bukkit.class})
    public void testIsProviderAvailable() {
        out.println("isProviderAvailable");
        assertEquals(false, Compatibility.isProviderAvailable());
        Compatibility.init();
        assertEquals(true, Compatibility.isProviderAvailable());
    }
/*
    @Test
    public void testRegisterProvider() {
        System.out.println("registerProvider");
        CompatibilityProvider obj = null;
        Compatibility.registerProvider(obj);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetProvider() {
        System.out.println("getProvider");
        CompatibilityProvider expResult = null;
        CompatibilityProvider result = Compatibility.getProvider();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetRecommendedProviderType() {
        System.out.println("getRecommendedProviderType");
        String expResult = "";
        String result = Compatibility.getRecommendedProviderType();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetRecommendedProviderVersion() {
        System.out.println("getRecommendedProviderVersion");
        String expResult = "";
        String result = Compatibility.getRecommendedProviderVersion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
}
