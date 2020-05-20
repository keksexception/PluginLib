package de.raffi.pluginlib.test;

public interface YesNoCallback {
	public void accept();
	public void decline();
	/**
	 * called <b>after</b> the click and after the input got reseted<br>
	 * thats a good point to get another input
	 * 
	 */
	public void onHandlerRemoved(boolean state);
}
