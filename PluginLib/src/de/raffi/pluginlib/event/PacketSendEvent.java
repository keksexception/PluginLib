package de.raffi.pluginlib.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class PacketSendEvent extends Event implements Cancellable{
	
	private Player player;
	private ChannelHandlerContext context;
	private Object message;
	private ChannelPromise channelPromise;
	private boolean cancelled=false;
	
	
	/**
	 * called when the server sends a packet to a player
	 * @param player
	 * @param context
	 * @param message
	 * @param channelPromise
	 */
	public PacketSendEvent(Player player, ChannelHandlerContext context, Object message,
			ChannelPromise channelPromise) {
		super();
		this.player = player;
		this.context = context;
		this.message = message;
		this.channelPromise = channelPromise;
	}
	public Player getTo() {
		return player;
	}
	public ChannelHandlerContext getChannelHandlerContext() {
		return context;
	}
	/**
	 * 
	 * @return the packet
	 */
	public Object getPacket() {
		return message;
	}
	public ChannelPromise getChannelPromise() {
		return channelPromise;
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
