package de.raffi.pluginlib.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.raffi.pluginlib.main.PluginLib;

public class PluginLibStartupEvent extends Event{

	private PluginLib pluginLib;
	
	public PluginLibStartupEvent(PluginLib pluginLib) {
		this.pluginLib = pluginLib;
	}
	public PluginLib getPluginLib() {
		return pluginLib;
	}
	
	private static final HandlerList handlers = new HandlerList();
	public static HandlerList getHandlerList() {
		return handlers;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
