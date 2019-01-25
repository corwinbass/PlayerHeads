/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.plugins;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;

/**
 * Helper class for Indyuce's BountyHunters plugin methods.
 * 
 * If you want to compile the program without support, you can make each method empty / return false
 * @author crashdemons (crashenator at gmail.com)
 */
public class BountyHuntersHelper {
    private final Plugin bountyhunters;
    
    /**
     * Construct the class object
     */
    public BountyHuntersHelper(){
        PluginManager pm =  Bukkit.getPluginManager();
        if(pm!=null){//check that init wasn't loaded too early / allow tests without PM support.
            bountyhunters = Bukkit.getPluginManager().getPlugin("BountyHunters");
        }else bountyhunters=null;
    }
    
    /**
     * Check if the plugin is available / was detected.
     * @return whether the plugin is available / was detected.
     */
    public boolean isAvailable(){
        return bountyhunters != null;
    }
    
    /**
     * Check whether the specified player has an attached bounty
     * @param op the player
     * @return whether the specified player has an attached bounty
     */
    public boolean hasBounty(OfflinePlayer op){
        if(!isAvailable()) return false;
        net.Indyuce.bountyhunters.manager.BountyManager bm = net.Indyuce.bountyhunters.BountyHunters.getBountyManager();
        return bm.hasBounty(op);
    }
}
