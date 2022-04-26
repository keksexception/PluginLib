package de.raffi.pluginlib.bungeecord;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.raffi.pluginlib.main.PluginLib;

public class BungeeAPI{
	
	/**
	 * @param server name of the Bungeecordserver. Example: {@code Citybuild-1}
	 * @param player the player that should be send to the server.
	 */
	public static void sendToServer(String server, Player player) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		player.sendPluginMessage(PluginLib.getInstance(), "BungeeCord", out.toByteArray());
	}
	
}
