/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package org.shininet.bukkit.playerheads.events;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class BlockDropHeadEvent extends BlockEvent implements Cancellable,DropHeadEvent{
   private static final HandlerList handlers = new HandlerList();
    private boolean canceled = false;
    private final ItemStack drop;

    /**
     * Construct the event
     * @param block the block dropping the head
     * @param drop the head item being dropped
     */
    public BlockDropHeadEvent(Block block, ItemStack drop) {
        super(block);
        this.drop = drop;
    }

    /**
     * Gets the item that will drop from the beheading.
     * 
     * @return mutable ItemStack that will drop into the world once this event is over
     */
    @SuppressWarnings("unused")
    public ItemStack getDrop() {
        return drop;
    }

    /**
     * Whether the event has been cancelled.
     * @return Whether the event has been cancelled.
     */
    @Override
    public boolean isCancelled() {
        return canceled;
    }

    /**
     * Sets whether the event should be cancelled.
     * @param cancel whether the event should be cancelled.
     */
    @Override
    public void setCancelled(boolean cancel) {
        canceled = cancel;
    }

    /**
     * Get a list of handlers for the event.
     * @return a list of handlers for the event
     */
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Get a list of handlers for the event.
     * @return a list of handlers for the event
     */
    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
