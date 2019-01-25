/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility;

import com.github.crashdemons.playerheads.compatibility.plugins.BountyHuntersHelper;

/**
 * Class providing methods and information for inter-plugin compatibility
 * @author crashdemons (crashenator at gmail.com)
 */
public class CompatiblePlugins {
    private CompatiblePlugins(){}
    public static BountyHuntersHelper BountyHunters = null;
    public static void init(){
        BountyHunters = new BountyHuntersHelper();
    }
}
