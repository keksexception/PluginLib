package de.raffi.pluginlib.utils;

import org.bukkit.inventory.Inventory;

public abstract interface AsyncUpdateTask {
	
	/**
	 * called asynchrounly every {@link AsyncUpdateTask#getUpdateDelay() x} ticks
	 * @param inv
	 * @return return {@code true} when the scheduler should continue, 
	 * 		   {@code false} when the scheduler should be stopped.
	 */
	boolean update(Inventory inv);
	/**
	 * 
	 * @return the scheduler delay in ticks
	 */
	int getUpdateDelay();
}
