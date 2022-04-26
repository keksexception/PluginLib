package de.raffi.pluginlib.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.Bukkit;

import de.raffi.pluginlib.main.PluginLib;

public class SpigotUpdater {

	/**
	 * 
	 * @param id spigot resource id
	 * @param uc
	 * @since 1.1-b5
	 */
	public static void checkForUpdates(String id, UpdateCallback uc) {	
		try {
			URL versionURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + id);
			BufferedReader reader = new BufferedReader(new InputStreamReader(versionURL.openStream()));
			uc.onVersionReceived(reader.readLine());
			reader.close();
		} catch (IOException e) {
			uc.onFail();
		}
	}
	/**
	 * 
	 * @param id spigot resource id
	 * @param uc
	 * @since 1.1-b5
	 */
	public static void asyncCheckForUpdates(String id, UpdateCallback uc) {	
		Bukkit.getScheduler().runTaskAsynchronously(PluginLib.getInstance(), ()->{
			try {
				URL versionURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + id);
				BufferedReader reader = new BufferedReader(new InputStreamReader(versionURL.openStream()));
				uc.onVersionReceived(reader.readLine());
				reader.close();
			} catch (IOException e) {
				uc.onFail();
			}
		});
		
	}
	/**
	 * 
	 * @param id spigot resource id
	 * @return the newest version
	 * @since 1.1-b5
	 */
	public static String checkLatest(String id) {	
		try {
			URL versionURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + id);
			BufferedReader reader = new BufferedReader(new InputStreamReader(versionURL.openStream()));
			String s = reader.readLine();
			reader.close();
			return s;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
