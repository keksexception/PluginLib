package de.raffi.pluginlib.test;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import de.raffi.pluginlib.utils.Logger;
import de.raffi.pluginlib.utils.Useful;

public class InputHandler implements Listener {
	
	private static HashMap<Player, MessageHandler> handlers = new HashMap<>();
	private static HashMap<Player, YesNoCallback> ynfeedback = new HashMap<>();
	/**
	 * null the message if you dont want to send a message
	 * @param msg the message will be displayed to the player by calling this method
	 * @param p the player from that the input should be get from
	 * @param handler
	 */
	public static void getInputFrom(Player p, String msg, MessageHandler handler) {
		if(msg!=null)p.sendMessage(msg);
		if(handlers.get(p)!=null) Logger.debug("Warn: Already getting chat-input from " + p.getName()+ "! Overriding MessageHandler.");
		handlers.put(p, handler);
	}
	public static void getYesNoFeedback(Player p, String msg, String yesTxt, String noTxt,YesNoCallback callback) {
		p.sendMessage(msg);
		Useful.sendYesNoChoice(p, yesTxt, noTxt, "/pluginlibchoice yes", "/pluginlibchoice no");
		if(ynfeedback.get(p)!=null) Logger.debug("Warn: Already getting yes-no-input from " + p.getName()+ "! Overriding YesNoCallback.");
		ynfeedback.put(p, callback);
	}
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if(e.getMessage().contains(" ")) {
			if(e.getMessage().startsWith("/pluginlibchoice ")) {
				e.setCancelled(true);
				YesNoCallback callback = ynfeedback.get(e.getPlayer());
				if(callback!=null) {
					String s = e.getMessage().split(" ")[1];
					boolean b=true;
					if(s.equals("yes"))  {
						callback.accept();
						b = true;
					} else if(s.equals("no")) {
						callback.decline();
						b=false;
					}
					ynfeedback.remove(e.getPlayer());
					callback.onHandlerRemoved(b);
				}
			}
		}
	}
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		
	}
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		MessageHandler handler = handlers.get(p);
		if(handler!=null) {
			if(handler.handleMessage(e.getMessage().trim())) {
				handlers.remove(p);	
				handler.onHandlerRemoved();
			} else handler.onMessageDenied(e.getMessage().trim());
			e.setCancelled(true);
		}
	}
}
