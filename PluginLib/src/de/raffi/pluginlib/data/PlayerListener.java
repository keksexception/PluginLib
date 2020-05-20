package de.raffi.pluginlib.data;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.raffi.pluginlib.event.ChannelReadEvent;
import de.raffi.pluginlib.event.PlayerInteractAtNPCEvent;
import de.raffi.pluginlib.main.PluginLib;
import de.raffi.pluginlib.npc.NPC;
import de.raffi.pluginlib.npc.NPCAction;
import de.raffi.pluginlib.npc.NPCManager;
import de.raffi.pluginlib.utils.ReflectionHelper;

public class PlayerListener implements Listener{
	

	@EventHandler
	public void handleJoin(PlayerJoinEvent e) {
		PluginLib.getInstance().getInjector().addPlayer(e.getPlayer());
		
		for(NPC npc : NPCManager.npcs) {
			if(npc.isAutoSpawn()) npc.spawn(e.getPlayer());
		}
		
	}
	@EventHandler
	public void handleQuit(PlayerQuitEvent e) {
		PluginLib.getInstance().getInjector().removePlayer(e.getPlayer());
	}
	@EventHandler
	public void handleRead(ChannelReadEvent e) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		if(e.getPacket().getClass().isAssignableFrom(ReflectionHelper.packetPlayInUseEntity)) {
			Object pack =e.getPacket();
			
			Field f = ReflectionHelper.packetPlayInUseEntity.getDeclaredField("action");
			Field i = ReflectionHelper.packetPlayInUseEntity.getDeclaredField("a");
			f.setAccessible(true);
			i.setAccessible(true);
			int id = i.getInt(pack);
			NPC interacted = null;
			for(NPC npc : NPCManager.npcs) {
				if(npc.getEntityID()==id) interacted=npc;
			}
			if(interacted!=null) {
				PlayerInteractAtNPCEvent ev = new PlayerInteractAtNPCEvent(e.getPlayer(), NPCAction.valueOf(f.get(pack).toString()), interacted);
				Bukkit.getPluginManager().callEvent(ev);
			}
		
		} 
	}
	

	

}
