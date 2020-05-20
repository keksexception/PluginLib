package de.raffi.pluginlib.utils;

public class ServerData {
	
	
	private String MOTD;
	private int playerCount;
	private int maximumPlayers;
	public ServerData(String MOTD, int playerCount, int maximumPlayers) {
		this.MOTD = MOTD;
		this.playerCount = playerCount;
		this.maximumPlayers = maximumPlayers;
	}
	public String getMOTD() {
		return MOTD;
	}
	public int getPlayerCount() {
		return playerCount;
	}
	public int getMaximumPlayers() {
		return maximumPlayers;
	}
	
	
	
	
	
}
