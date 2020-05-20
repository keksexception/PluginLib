package de.raffi.pluginlib.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import io.netty.channel.ChannelHandlerContext;

public class ChannelReadEvent extends Event implements Cancellable{
	
	private Player player;
	private ChannelHandlerContext context;
	private Object packetListener;
	private boolean cancelled=false;
	
	
	/**
	 * called when the player sends a packet to the server
	 * @param player
	 * @param context
	 * @param packetListener
	 */
	public ChannelReadEvent(Player player, ChannelHandlerContext context, Object packetListener) {
		this.player = player;
		this.context = context;
		this.packetListener = packetListener;
	}
	public Player getPlayer() {
		return player;
	}
	public ChannelHandlerContext getChannelHandlerContext() {
		return context;
	}
	public Object getPacket() {
		return packetListener;
	}
	private static final HandlerList handlers = new HandlerList();
	public static HandlerList getHandlerList() {
		return handlers;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
		
	}

}
