package de.raffi.pluginlib.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLAPI {
	
	private ConnectionData data;
	private Connection con;
	public MySQLAPI(ConnectionData data) {
		this(data, false);
	}
	public MySQLAPI(ConnectionData data,boolean connectImmediately) {
		this.data = data;
		if(connectImmediately) connect();
	}
	public MySQLAPI(String host, String port, String database,String username, String passwort,boolean connectImmediately) {
		this(new ConnectionData(host, port, database, username, passwort),connectImmediately);
	}
	public MySQLAPI(String host, String port, String database,String username, String passwort) {
		this(new ConnectionData(host, port, database, username, passwort),false);
	}
	/**
	 * 
	 * @return true if connected successfully
	 */
	public boolean connect() {
		if(!isConnected()) {
			try {
				this.con = DriverManager.getConnection("jdbc:mysql://" + data.getHost() + ":" + data.getPort() + "/" + data.getDatabase() + "?autoReconnect=true",data.getUsername(),data.getPasswort());
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				throw new AlreadyConnectedException("Already connected!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;	
	}
	/**
	 * prepares the following statement <pre>SELECT get FROM table WHERE row = value</pre>
	 * @param table
	 * @param row
	 * @param value
	 * @param get
	 * @return
	 */
	public String getString(String table, String row, String value, String get) {
		try {
			PreparedStatement ps = getConnection().prepareStatement("SELECT `" + get + "` FROM `" + table + "` WHERE `" + row + "` = ?");
			ps.setString(1, value);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getString(get);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	public void executeQuery(String sql) throws SQLException {
		con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).execute(sql);
	}
	public void prepareStatement(String sql) throws SQLException {
		PreparedStatement ps = getConnection().prepareStatement(sql);
		ps.executeUpdate();
	}
	public void prepareStatement(PreparedStatement ps) throws SQLException {
		ps.executeUpdate();
	}
	/**
	 * 
	 * @return true if disconnect successfully
	 */
	public boolean disconnect() {
		if(isConnected()) {
			try {
				con.close();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	public boolean isConnected() {
		return con!=null;
	}
	public Connection getConnection() {
		return con;
	}
	public ConnectionData getData() {
		return data;
	}

}
