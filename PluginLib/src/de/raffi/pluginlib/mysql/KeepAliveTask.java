package de.raffi.pluginlib.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KeepAliveTask implements Runnable{
	
	private Connection connection;

	public KeepAliveTask(Connection connection) {
		this.connection = connection;
	}
	public KeepAliveTask(MySQLAPI api) {
		this(api.getConnection());
	}

	@Override
	public void run() {
		try {
			this.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE).execute("SELECT 1");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

}
