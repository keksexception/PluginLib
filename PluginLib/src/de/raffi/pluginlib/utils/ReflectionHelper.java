package de.raffi.pluginlib.utils;

import de.raffi.pluginlib.compability.CompabilityHandler;

public class ReflectionHelper {
	
	public static final Class<?> packetPlayInUseEntity = findNMS("PacketPlayInUseEntity");
	
	
	static Class<?> findNMS(String s) {
		try {
			return CompabilityHandler.getNMSClass(s);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
