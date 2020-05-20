package de.raffi.pluginlib.data;

import java.lang.reflect.Field;

import org.bukkit.entity.Player;

import de.raffi.pluginlib.utils.Logger;
import io.netty.channel.Channel;

public class PacketInjector {

	private Field EntityPlayer_playerConnection;
	private Class<?> PlayerConnection;
	private Field PlayerConnection_networkManager;

	private Class<?> NetworkManager;
	/**
	 * channel
	 */
	private Field k;
	/**
	 * PacketListener
	 */
	private Field m;

	public PacketInjector() {
		Logger.debug("Loading PacketInjector");
		try {
			EntityPlayer_playerConnection = Reflection.getField(Reflection.getClass("{nms}.EntityPlayer"),
					"playerConnection");

			PlayerConnection = Reflection.getClass("{nms}.PlayerConnection");
			PlayerConnection_networkManager = Reflection.getField(PlayerConnection, "networkManager");

			NetworkManager = Reflection.getClass("{nms}.NetworkManager");
			Logger.debug("Preparing channel and packetlistener");
			k = Reflection.getField(NetworkManager, "channel"); //channel
			m = Reflection.getField(NetworkManager, "m"); //PacketListener
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void addPlayer(Player p) {
		try {
			Channel ch = getChannel(getNetworkManager(Reflection.getNmsPlayer(p)));
			if (ch.pipeline().get("PacketInjector") == null) {
				PacketHandler h = new PacketHandler(p);
				ch.pipeline().addBefore("packet_handler", "PacketInjector", h);
			}
			Logger.debug("Added " + p.getName() + " to PacketInjector");
		} catch (Throwable t) {
			t.printStackTrace();
			Logger.debug("Could not add " + p.getName() + " to PacketInjector");
		}
	}

	public void removePlayer(Player p) {
		try {
			Channel ch = getChannel(getNetworkManager(Reflection.getNmsPlayer(p)));
			if (ch.pipeline().get("PacketInjector") != null) {
				ch.pipeline().remove("PacketInjector");
			}
			Logger.debug("Removed " + p.getName() + " from PacketInjector");
		} catch (Throwable t) {
			t.printStackTrace();
			Logger.debug("Could not remove " + p.getName() + " from PacketInjector");
		}
	}

	private Object getNetworkManager(Object ep) {
		Object o =Reflection.getFieldValue(EntityPlayer_playerConnection, ep);
		return Reflection.getFieldValue(PlayerConnection_networkManager,o);
	}

	private Channel getChannel(Object networkManager) {
		Channel ch = null;
		try {
			ch = Reflection.getFieldValue(k, networkManager);
		} catch (Exception e) {
			ch = Reflection.getFieldValue(m, networkManager);
		}
		return ch;
	}
}
