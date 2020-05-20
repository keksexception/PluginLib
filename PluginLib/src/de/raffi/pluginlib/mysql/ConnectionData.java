package de.raffi.pluginlib.mysql;

public class ConnectionData {
	
	
	private String host, port, database, username, passwort;
	public ConnectionData(String host, String port, String database, String username, String passwort) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.passwort = passwort;
	}
	public ConnectionData(String host, int port, String database, String username, String passwort) {
		this(host, String.valueOf(port), database, username, passwort);
	}
	public String getHost() {
		return host;
	}
	public String getPort() {
		return port;
	}
	public String getDatabase() {
		return database;
	}
	public String getUsername() {
		return username;
	}
	public String getPasswort() {
		return passwort;
	}
	
	
	
	

}
