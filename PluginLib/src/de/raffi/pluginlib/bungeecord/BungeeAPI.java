package de.raffi.pluginlib.bungeecord;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeAPI implements Listener{
	
	/**
	 * please make sure, that you {@link BungeeAPI#registerChannel(JavaPlugin) register the channel} 
	 * in your {@code onEnable} method <b>before</b> you are using this method
	 * @param plugin your plugin
	 * @param server name of the Bungeecordserver. Example: {@code Citybuild-1}
	 * @param player the player that should be send to the server.
	 */
	@EventHandler
	public static final void sendToServer(JavaPlugin plugin,String server, Player player) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
	}
	/**
	 * registers the bungeecord channel. call this method in your {@code onEnable} method
	 * @param plugin
	 */
	public static void registerChannel(JavaPlugin plugin) {
		Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
	}
}
