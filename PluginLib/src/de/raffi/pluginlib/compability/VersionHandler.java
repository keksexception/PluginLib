package de.raffi.pluginlib.compability;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract interface VersionHandler {
	
	public default void sendParticle(String particleName, Player to,Location loc, float xOffset, float yOffset, float zOffset,float speed, int count) {
		float x = (float) loc.getX();
		float y = (float) loc.getY();
		float z = (float) loc.getZ();	
		try {
			Class<?> particleClass = CompabilityHandler.getNMSClass("PacketPlayOutWorldParticles");
			Class<?> enumClass = CompabilityHandler.getNMSClass("EnumParticle");
			Object flame = CompabilityHandler.getEnumValue(enumClass, particleName);
			Constructor<?> con = particleClass.getConstructors()[1];
			Object send = con.newInstance(flame,true,x,y,z, xOffset,yOffset,zOffset,speed,count,null);
			for (Player p : Bukkit.getOnlinePlayers()) {
				CompabilityHandler.sendPacket(p, send);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public default Sound getSound(String s) {
		return Sound.valueOf(s);
	}
	public default void sendPacket(Player p, Object packet) {
		try {
			CompabilityHandler.sendPacket(p, packet);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @deprecated
	 * @param entity
	 * @return
	 */
	public default int getEntityID(Object entity) {
		return 0;
	}
	/**
	 * @deprecated
	 * @param entity
	 * @return
	 */
	public default boolean getEntitySneaking(Object entity) {
		return false;
	}
	/**
	 * @deprecated
	 * @param entity
	 * @return
	 */
	public default double[] getEntityPositionData(Object entity) {
		return new double[0];
	}
	/**
	 * @deprecated
	 * @param entity
	 * @return
	 */
	public default float[] getEntityDirectionData(Object entity) {
		return new float[0];
	}
	public default void setEntityLocation(Object entity,Location loc) {
		try {
			Method m = entity.getClass().getMethod("setLocation", double.class,double.class,double.class,float.class,float.class);
			m.invoke(entity, loc.getX(),loc.getY(),loc.getZ(),loc.getYaw(),loc.getPitch());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public void processPacketPlayOutEquippment(Player p,int entityID, int slot, ItemStack item);
	public void processPacketPlayOutEntityDestroy(Player p,int... entityID);
	public void processPacketPlayOutEntityTeleport(Player p, Object entity);
	public void processPacketPlayOutEntityTeleport(Player p, int entityID, Location target);
	public void processPacketPlayOutEntityHeadRotation(Player p, Object entity, byte yaw);
	public void processPacketPlayOutEntityMetadata(Player p, int entityID, Object dataWatcher, boolean b);
	public void processPacketPlayOutAnimation(Player p, Object entity, int animation);
	public void processPacketPlayOutNamedEntitySpawn(Player p, Object humanEntity);
	public void processPacketPlayOutPlayerInfoAddPlayer(Player p,Object... entity);
	
}
