package de.raffi.pluginlib.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.naming.OperationNotSupportedException;

import net.minecraft.server.v1_8_R3.MinecraftServer;

public class ServerManager {
	
	public ServerManager() throws OperationNotSupportedException {
		throw new OperationNotSupportedException();
	}
	
	
	public static ServerData getServerData(String ip, int port) throws IOException {
		String serverMotd = "?";
		int onlinePlayers = -1;
		int maxPlayers = -1;
		Socket sock = new Socket();
		sock.setSoTimeout(100);
		sock.connect(new InetSocketAddress(ip, port), 100);

		DataOutputStream out = new DataOutputStream(sock.getOutputStream());
		DataInputStream in = new DataInputStream(sock.getInputStream());

		out.write(0xFE);

		int b;
		StringBuffer str = new StringBuffer();
		while ((b = in.read()) != -1) {
			if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
				str.append((char) b);
			}
		}

		String[] data = str.toString().split("§");
		serverMotd = data[0];
		onlinePlayers = Integer.parseInt(data[1]);
		maxPlayers = Integer.parseInt(data[2]);

		serverMotd = String.format(serverMotd);

		sock.close();

	
		return new ServerData(serverMotd, onlinePlayers, maxPlayers);
	}
	public static void setSocketMotd(String motd) {
        try {
            Field f = MinecraftServer.class.getDeclaredField("motd");
            f.setAccessible(true);
            f.set(MinecraftServer.getServer(), motd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
