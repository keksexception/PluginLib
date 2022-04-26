package de.raffi.pluginlib.math;

import org.bukkit.Location;

public class Circle { 
	
	private Location loc;
	private double radius;
	private float degree;
	private int count;
	public Circle(Location loc, double radius, float degree) {
		this.loc = loc;
		this.radius = radius;
		this.degree = degree;
	}
	public Location next() {
		count++;
		double x = Math.cos(degree * count)*radius + loc.getX();
		double z = Math.sin(degree * count)*radius + loc.getZ();
		return new Location(loc.getWorld(), x, loc.getY(), z);
	}

}
