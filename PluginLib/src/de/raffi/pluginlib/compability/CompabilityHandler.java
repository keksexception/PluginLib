package de.raffi.pluginlib.compability;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;

import de.raffi.pluginlib.main.PluginLib;
import de.raffi.pluginlib.utils.Logger;

public class CompabilityHandler {
	
	public static final String DEFAULT_PATH = "de.raffi.pluginlib.compability";
	public static VersionHandler versionHandler;
	
	/**
	 * example name of versionhandler: {@code Handler_v1_8_R3}
	 * @param packagePath the package where a versionhandler is
	 * @return {@code true} if success <br> {@code false} if no versionhandler was found
	 */
	public static boolean findVersionHandler(String packagePath) {
		Logger.debug("Using serverversion " + PluginLib.getServerVersion());
		Logger.debug("Searching for VersionHandler in " + packagePath);
		try {
			versionHandler = (VersionHandler)Class.forName(packagePath + ".Handler_" + PluginLib.getServerVersion()).newInstance();
			Logger.debug("Found VersionHandler: " + versionHandler.getClass().getName());
			return true;
		} catch (Exception e) {
			versionHandler = new MissingVersionHandler();
			Logger.debug("Error: Could not found any working VersionHandler! [NO_HANDLER_FOUND] Set handler to default: " + versionHandler.getClass().getName());
			return false;
		}
	}
	/**
	 * 
	 * @param p player the packet should be send to
	 * @param packet packet object
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws ClassNotFoundException
	 */
	public static void sendPacket(Player p, Object packet) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, 	SecurityException, NoSuchFieldException, ClassNotFoundException  {
	    Object nmsPlayer = p.getClass().getMethod("getHandle").invoke(p);
	    Object plrConnection = nmsPlayer.getClass().getField("playerConnection").get(nmsPlayer);
	    plrConnection.getClass().getMethod("sendPacket", getPacketClass()).invoke(plrConnection, packet);
	}
	/**
	 * 
	 * @return nms packet class
	 * @throws ClassNotFoundException
	 */
	public static Class<?> getPacketClass() throws ClassNotFoundException {
		return Class.forName("net.minecraft.server."+PluginLib.getServerVersion()+".Packet");
	}
	/**
	 * 
	 * @param name
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class<?> getNMSClass(String name) throws ClassNotFoundException {
		return Class.forName("net.minecraft.server."+PluginLib.getServerVersion()+"." + name);
	}
	
	public static Object getEnumValue(String enumClass, String constant) {
		try {
			for(Object o : Class.forName(enumClass).getEnumConstants()) {
				if(o.toString().equals(constant)) return o;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public static Object getEnumValue(Class<?> enumClass, String constant) {
		return getEnumValue(enumClass.getName(), constant);	
	}

	public static void setValue(Object obj, String fieldName, Object value) {
		try {
			Field f = obj.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(obj, value);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static Object getValue(Object obj, String s) {
		try {
			Field f = obj.getClass().getDeclaredField(s);
			f.setAccessible(true);
			return f.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
