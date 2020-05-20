package de.raffi.pluginlib.test;

public interface MessageHandler {
	

	/**
	 * if the message is not accepted the player should send the message again<br>
	 * <b>please note: you can NOT use {@link InputHandler#getInputFrom(org.bukkit.entity.Player, String, MessageHandler)} here, because
	 * the input get reseted when the condition is true! Pleas use {@link MessageHandler#onHandlerRemoved()} instead.</b>
	 * @param message the message which the player has send
	 * @return accept the response?
	 */
	public boolean handleMessage(String message);
	/**
	 * executed when the response is not accepted
	 * @param message the message which the player has send
	 */
	public void onMessageDenied(String message);
	/**
	 * called <b>after</b> the response is accepted and after the input got reseted<br>
	 * thats a good point to get another input
	 * 
	 */
	public void onHandlerRemoved();

}
