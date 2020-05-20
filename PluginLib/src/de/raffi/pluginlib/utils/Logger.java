package de.raffi.pluginlib.utils;

import org.bukkit.Bukkit;

import de.raffi.pluginlib.configuration.ConfigurationPluginLib;

public class Logger {
	
	public void log(String s) {
		System.out.println(s);
	}
	public static void debug(String s) {
		if(ConfigurationPluginLib.DEBUG) {
			System.out.println(s);
			if(ConfigurationPluginLib.DEBUG_PRINT_CHAT) Bukkit.broadcastMessage(s);
		}
			
	}
	public static void debug(Exception e) {
		if(ConfigurationPluginLib.DEBUG) {
			String s = e.getMessage();
			System.out.println("Exception: " + s);
			if(ConfigurationPluginLib.DEBUG_PRINT_CHAT) Bukkit.broadcastMessage("§4Exception oncurred: §f" + s);
		}
			
	}

}
