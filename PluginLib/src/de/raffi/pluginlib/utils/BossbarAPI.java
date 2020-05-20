package de.raffi.pluginlib.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.raffi.pluginlib.compability.CompabilityHandler;
import de.raffi.pluginlib.main.PluginLib;

/**
 * 
 * @deprecated not completed
 */
public class BossbarAPI {
	
	private Player p;
	private String title;
	private Object dragonEntity;
	
	private int taskID;
	/**
	 * 
	 * @param p
	 * @param title
	 * @deprecated not completed
	 */
	public BossbarAPI(Player p, String title) {
		this.p = p;
		this.title = title;
	}
	public Player getPlayer() {
		return p;
	}
	public String getTitle() {
		return title;
	}
	public void create() {
		//TODO: initiate the entity
		startupdateTask();
	}
	public void remove() {
		Bukkit.getScheduler().cancelTask(taskID);
		try {
			int entityID=(int) dragonEntity.getClass().getMethod("getId").invoke(dragonEntity);
			CompabilityHandler.versionHandler.processPacketPlayOutEntityDestroy(p, entityID);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	private void startupdateTask() {
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginLib.getInstance(), new Runnable() {
			public void run() {
				moveEntityToPlayer();
			}
		}, 120, 120);
	}
	public void moveEntityToPlayer() {
		CompabilityHandler.versionHandler.processPacketPlayOutEntityTeleport(getPlayer(), dragonEntity);
	}
}
