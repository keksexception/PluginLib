package de.raffi.pluginlib.npc;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.mojang.authlib.GameProfile;

public class NPCData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6716069173670013664L;
	private double x,y,z;
	private float yaw,pitch;
	private String gameProfileName,world,skinName;
	private UUID gameProfileUUID;
	private boolean autoSpawn,removeFromTab;
	
	public NPCData(String world,double x, double y, double z, float yaw, float pitch, String gameProfileName, UUID gameProfileUUID, String skinName,
			boolean autoSpawn, boolean removeFromTab) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.gameProfileName = gameProfileName;
		this.gameProfileUUID = gameProfileUUID;
		this.skinName = skinName;
		this.autoSpawn = autoSpawn;
		this.removeFromTab = removeFromTab;
	}
	public NPCData(NPC npc) {
		this(npc.getLocation().getWorld().getName(),npc.getLocation().getX(), npc.getLocation().getY(), npc.getLocation().getZ(),npc.getLocation().getYaw(), npc.getLocation().getPitch(), npc.getProfile().getName(), npc.getProfile().getId(), npc.getSkinName(), npc.isAutoSpawn(), npc.isRemovedFromTablist());
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
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
	public String getGameProfileName() {
		return gameProfileName;
	}
	public UUID getGameProfileUUID() {
		return gameProfileUUID;
	}
	public boolean isAutoSpawn() {
		return autoSpawn;
	}
	public boolean isRemoveFromTab() {
		return removeFromTab;
	}
	public NPC toNPC(boolean register) {
		GameProfile gp = new GameProfile(getGameProfileUUID(), getGameProfileName());
		NPC npc = new NPC(new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch), gp, skinName);
		if(register) npc.register();
		npc.setRemovedFromTablist(removeFromTab);
		if(autoSpawn) npc.enableAutoSpawn();
		else npc.disableAutoSpawn();
		return npc;
	}
	
	
}
