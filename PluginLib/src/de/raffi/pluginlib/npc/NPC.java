package de.raffi.pluginlib.npc;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.UUID;

import javax.naming.OperationNotSupportedException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import de.raffi.pluginlib.compability.npchandler.NPCHandlerManager;
import de.raffi.pluginlib.converter.ConverterLocation;
import de.raffi.pluginlib.main.PluginLib;
import de.raffi.pluginlib.utils.Logger;
import de.raffi.pluginlib.utils.UUIDFetcher;



public class NPC {
	
	private Location loc;
	private Object entity;
	private GameProfile profile;
	private boolean registered;
	private boolean autoSpawn=false;
	private String skinName,skinTexture,skinSignature;
	private boolean removedFromTablist=false;

	public NPC(Location loc,GameProfile profile,String skinName) {
		this.loc = loc;
		this.profile = profile;
		this.skinName = skinName;
	
		entity = NPCHandlerManager.npcHandler.createEntityPlayerAndTeleport(profile, loc);
		String[] properties = getPropertiesFromName(skinName);
		this.skinTexture = properties[0];
		this.skinSignature = properties[1];
		setSkin(skinTexture, skinSignature);
	}
	public NPC(Location loc, UUID uuid, String displayname,String skinName) {
		this(loc, new GameProfile(uuid, displayname),skinName);
	}
	public NPC(Location loc, String name, boolean fetchUUID,String skinName) {
		this(loc, new GameProfile(fetchUUID?UUIDFetcher.getUUID(name):UUID.randomUUID(), name),skinName);
	}
	public NPC() throws OperationNotSupportedException {
		Logger.debug("Illegal Operation");
		throw new OperationNotSupportedException();
	}
	public boolean isRemovedFromTablist() {
		return removedFromTablist;
	}
	public String getIdentification() {
		return loc.toString()+profile.getId();
	}
	/**
	 * the method also updates the change. You don't have to call {@link NPC#removedFromTablist} or {@link NPC#addToTablist(Player)}
	 * @param removedFromTablist
	 * @return this
	 */
	public NPC setRemovedFromTablist(boolean removedFromTablist) {
		this.removedFromTablist = removedFromTablist;
		Bukkit.getOnlinePlayers().forEach(p->{
			if(removedFromTablist) {
				removeFromTablist(p);
			} else {
				addToTablist(p);
			}
		});
		return this;
	}
	/**
	 * converts object to Json
	 * @since 1.1-b5
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJson() {
		JSONObject npc = new JSONObject();
		npc.put("location", new ConverterLocation().stringify(loc));
		npc.put("uuid", profile.getId().toString());
		npc.put("displayname", profile.getName());
		npc.put("skinname", skinName);
		return npc;
	}
	/**
	 * creates npc from json object
	 * @since 1.1-b5
	 */
	public static NPC fromJson(JsonObject obj, boolean register) {
		Location loc = new ConverterLocation().create(obj.get("location").getAsString());
		UUID uuid = UUID.fromString(obj.get("uuid").getAsString());
		String displayName = obj.get("displayname").getAsString();
		String skinName = obj.get("skinname").getAsString();
		NPC npc = new NPC(loc, uuid, displayName, skinName);
		if(register) npc.register();
		return npc;
	}
	/**
	 * that happens automaticly if {@link NPC#isRemovedFromTablist()} is true
	 * @param p the player that should see the change
	 */
	public void removeFromTablist(Player p) {
		NPCHandlerManager.npcHandler.removeFromTab(profile, p);
	}
	/**
	 * performs hurt animation with sound for all players
	 */
	public void hurt() {
		setAnimation(Animation.HURT);
		getLocation().getWorld().playSound(getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
	}
	/**
	 * performs hurt animation for the specified player
	 * @param p the player who should see the animation
	 */
	public void hurt(Player p) {
		setAnimation(p,Animation.HURT);
		p.playSound(getLocation(), Sound.HURT_FLESH, 1.0f, 1.0f);
	}
	/**
	 * that happens automaticly if {@link NPC#isRemovedFromTablist()} is false
	 * @param p
	 */
	public void addToTablist(Player p) {
		NPCHandlerManager.npcHandler.addToTab(profile, p);
	}
	public void setHandItem(ItemStack item) {
		NPCHandlerManager.npcHandler.setHandItem(entity, item);
	}
	/**
	 * when autospawn is enabled, the method {@link NPC#spawn(Player)} is automaticly called when a player joins
	 * @return boolean
	 */
	public boolean isAutoSpawn() {
		return autoSpawn;
	}
	public void setNameAboveHead(String name) {
		try {
			Field ff = profile.getClass().getDeclaredField("name");
			ff.setAccessible(true);
			ff.set(profile, name);
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}
	public String getSkinName() {
		return skinName;
	}
	public String getSkinSignature() {
		return skinSignature;
	}
	public String getSkinTexture() {
		return skinTexture;
	}
	/**
	 * causes {@link NPCException} when the NPC is not registered
	 * @return this
	 */
	public NPC enableAutoSpawn() {
		if(registered) 
			autoSpawn = true;
		else {
			try {
				throw new NPCException("NPC is not registered");
			} catch (NPCException e) {
				e.printStackTrace();
			}
		}
		return this;
	}
	public void disableAutoSpawn() {
		autoSpawn = false;
	}
	public GameProfile getProfile() {
		return profile;
	}
	public Object getEntity() {
		return entity;
	}
	public Location getLocation() {
		return loc;
	}
	
	/**
	 * removes the npc from the player
	 * @param p
	 */
	public void destroy(Player p) {
        NPCHandlerManager.npcHandler.destroy(entity, p);
	}	
	/**
	 * removes the npc from all online players
	 * 
	 */
	public void destroyForAll() {
		Bukkit.getOnlinePlayers().forEach(p->destroy(p));
	}	
	/**
	 * 
	 * @param value texture
	 * @param signature signature
	 */
	public void setSkin(String value, String signature) {
		profile.getProperties().put("textures", new Property("textures", value, signature));
		this.skinTexture = value;
		this.skinSignature = signature;
	}
	
	public NPC setSkin(String playerName) {
		String[] s = getPropertiesFromName(playerName);
		setSkin(s[0], s[1]);
		return this;
	}
	/**
	 * gets the properties from mojang.
	 * @param name the name of the player
	 * @return null if error; index 0: texture; index 1: signature
	 */
	public String[] getPropertiesFromName(String name) {
		try {
			URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
			InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());
			String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();

			URL url_1 = new URL(
					"https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
			InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
			JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties")
					.getAsJsonArray().get(0).getAsJsonObject();
			String texture = textureProperty.get("value").getAsString();
			String signature = textureProperty.get("signature").getAsString();

			return new String[] { texture, signature };
		} catch (IOException e) {
			Logger.debug("Could not get skin data from session servers!");
			e.printStackTrace();
			return null;
		}
	}
    public byte getFixRotation(float yawpitch) {
        return (byte)(yawpitch * 256.0f / 360.0f);
    }
    /**
     * @deprecated 1.8 only
     * @param yaw
     * @param pitch
     */
    public void setRotation(float yaw, float pitch) {
    	getLocation().setYaw(yaw);
    	getLocation().setPitch(pitch);
    	setLocation(getLocation());
    }
    /**
     * sets the location of the NPC
     * @param loc the new location 
     */
    public void setLocation(Location loc) {
    	NPCHandlerManager.npcHandler.setLocation(entity, loc);
    	this.loc.setX(loc.getX());
    	this.loc.setY(loc.getY());
    	this.loc.setZ(loc.getZ());
    	this.loc.setYaw(loc.getYaw());
    	this.loc.setPitch(loc.getPitch());
    }
    public void setRotation(Player p, float yaw, float pitch) {
    	NPCHandlerManager.npcHandler.setRotation(entity, yaw, pitch,p);
    }
    /**
     * sets the location of the NPC
     * @param p the player that should see the change
     * @param loc the new location 
     */
    public void setLocation(Player p, Location loc) {
    	NPCHandlerManager.npcHandler.setLocation(entity, loc, p);
    }
    /**
     * plays an animation
     * @param animation the animation which should be performed
     */
    public void setAnimation(Animation animation) {    	
    	Bukkit.getOnlinePlayers().forEach(p->setAnimation(p,animation));
    }
    /**
     * plays an animation
     * @param p the player who should see the change
     * @param animation the animation which should be performed
     */
    public void setAnimation(Player p,Animation animation) {    	
    	NPCHandlerManager.npcHandler.setAnimation(entity, animation, p);
    }
    /**
     * sets the NPC sneaking; only for one Player; the other players will not see any change
     * @param p the player who should see the change
     * @param b new sneak state
     */
    public void setSneaking(Player p, boolean b) {
    	NPCHandlerManager.npcHandler.setSneaking(entity, b, p);
    	
    }
    /**
     * lets the NPC look to the location
     * @param other the location the NPC should look to
     */
    public void rotateTo(Location other) {
    	Bukkit.getOnlinePlayers().forEach(p->rotateTo(p, other));
    	
    }
    /**
     * lets the NPC look to the location
     * @param p the player who should see the change
     * @param other the location the NPC should look to
     */
    public void rotateTo(Player p,Location other) {
    	NPCHandlerManager.npcHandler.rotateTo(entity, other, p, this);
    	
    }
    /**
     * set the NPC sneaking. don't works allways idk why
     * @param b new state
     */
    public void setSneaking(boolean b) {
    	NPCHandlerManager.npcHandler.setSneaking(entity, b);
    }
    /**
     * 
     * @return true when the NPC is currently sneaking
     */
    public boolean isSneaking() {
    	return NPCHandlerManager.npcHandler.isSneaking(entity);
    }
    /**
     * 
     * @return the entity ID of the fakeplayer
     */
    public int getEntityID() {
    	return NPCHandlerManager.npcHandler.getID(entity);
    }
    /**
     * spawns the NPC to the specified player. also removes/adds the NPC from/to the tablist.
     * @param p the player that should see the NPC
     * @return this
     */
	public NPC spawn(Player p) {
		NPCHandlerManager.npcHandler.spawn(entity, p);
        if(isRemovedFromTablist()) {
        	Bukkit.getScheduler().scheduleSyncDelayedTask(PluginLib.getInstance(), new Runnable() {
				public void run() {
					removeFromTablist(p);
				}
			}, 10);
        } else addToTablist(p);
        return this;
	}
	/**
	 * spawns the npc for all players online
	 * @return
	 */
	public NPC spawnForAll() {
		Bukkit.getOnlinePlayers().forEach(p->spawn(p));
		return this;
	}
	/**
	 * use this to register the NPC to the manager.
	 * only registered NPCs can be used in events!
	 * @return this
	 */
	public NPC register() {
		if(!registered)
			NPCManager.registerNPC(this);
		registered = true;
		return this;
	}
	public NPC unregister() {
		if(registered)
			NPCManager.unregisterNPC(this);
		registered = false;
		return this;
	}
	public NPCData toNPCData() {
		return new NPCData(this);
	}
}
