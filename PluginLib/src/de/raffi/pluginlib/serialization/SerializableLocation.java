package de.raffi.pluginlib.serialization;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import de.raffi.pluginlib.utils.Logger;

public class SerializableLocation implements Serializable{

	/**
	 * zero
	 */
	public static final SerializableLocation DEFAULT = new SerializableLocation(Bukkit.getWorld("world"), 0, 0, 0, 0, 0);
	
	private static final long serialVersionUID = 5923332077346942418L;
	
	private double x,y,z;
	private float yaw,pitch;
	private String worldName;
	private transient Location bukkitLocation;

	
	public SerializableLocation(World world, double x, double y, double z, float yaw, float pitch) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.worldName = world.getName(); Logger.debug("Set worldName to " + worldName);
		this.bukkitLocation = new Location(world, getX(), getY(), getZ(), getYaw(), getPitch());
	}
	public static SerializableLocation toSerializable(Location loc) {
		return new SerializableLocation(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(),loc.getYaw(),loc.getPitch());
	}
	/**
	 * 
	 * @return the converted {@link SerializableLocation} to a {@link Location}
	 */
	public Location toNormal() {
		return bukkitLocation==null?(bukkitLocation= new Location(Bukkit.getWorld(getWorldName()), getX(), getY(), getZ(), getYaw(), getPitch())):bukkitLocation;
	}
	public Location createNew() {
		return new Location(Bukkit.getWorld(getWorldName()), getX(), getY(), getZ(), getYaw(), getPitch());
	}
	public World getWorld() {
		if(bukkitLocation==null) bukkitLocation = createNew();
		return bukkitLocation.getWorld();
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public String getWorldName() {
		return worldName==null?"world":worldName;
	}
	public double getZ() {
		return z;
	}
	public float getYaw() {
		return yaw;
	}
	public float getPitch() {
		return pitch;
	}
	public int getBlockX() {
		return (int)x;
	}
	public int getBlockY() {
		return (int)y;
	}
	public int getBlockZ() {
		return (int)z;
	}
	
	
	
}
