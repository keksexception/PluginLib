package de.raffi.pluginlib.compability.npchandler;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.mojang.authlib.GameProfile;

import de.raffi.pluginlib.compability.CompabilityHandler;
import de.raffi.pluginlib.npc.Animation;
import de.raffi.pluginlib.npc.NPC;

public abstract interface NPCHandler {
	
	public Object createEntityPlayerAndTeleport(GameProfile profile,Location loc);
	public void removeFromTab(GameProfile profile, Player p);
	public void addToTab(GameProfile profile,Player p);
	public void setHandItem(Object entity, ItemStack set);
	public void destroy(Object entity, Player p);
	public void spawn(Object entity, Player p);
	public void setSneaking(Object entity, boolean b,Player p);
	public void setSneaking(Object entity, boolean b);
	public void setAnimation(Object entity, Animation animation,Player p);
	public void setLocation(Object entity, Location loc,Player p);
	public void setLocation(Object entity, Location loc);
	public void rotateTo(Object entity, Location loc,Player p,NPC npc);
	public boolean isSneaking(Object entity);
	public int getID(Object entity);
	
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

	public default byte getFixRotation(float yawpitch) {
		return (byte)(yawpitch * 256.0f / 360.0f);
	}
}
