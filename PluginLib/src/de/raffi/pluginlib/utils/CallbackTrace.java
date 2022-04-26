package de.raffi.pluginlib.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public interface CallbackTrace<T> {
	
	void onFinish(T result);
	/**
	 * 
	 * @param shooter the entity from which the trace got started
	 * @param check the entity to check
	 * @param current the current location 
	 * @return true when {@code check} is valid. 
	 */
	boolean validate(LivingEntity shooter, Entity check, Location current);

}
