package de.raffi.pluginlib.utils;

public interface UpdateCallback {
	
	public void onVersionReceived(String s);
	public void onFail();
	
}
