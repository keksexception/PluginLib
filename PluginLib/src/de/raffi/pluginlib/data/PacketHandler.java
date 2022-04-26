package de.raffi.pluginlib.data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.raffi.pluginlib.event.ChannelReadEvent;
import de.raffi.pluginlib.event.PacketSendEvent;
import de.raffi.pluginlib.main.PluginLib;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class PacketHandler extends ChannelDuplexHandler {
	private Player p;

	public PacketHandler(final Player p) {
		this.p = p;
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		PacketSendEvent e = new PacketSendEvent(p, ctx, msg, promise);
		if(PluginLib.getInstance().isEnabled()) {
			Bukkit.getScheduler().runTask(PluginLib.getInstance(), ()->Bukkit.getPluginManager().callEvent(e));
			if(!e.isCancelled())
				super.write(ctx, e.getPacket(), promise);
		}
	
	}

	@Override
	  public void channelRead(ChannelHandlerContext c, Object m) throws Exception {
		ChannelReadEvent e = new ChannelReadEvent(getPlayer(), c, m);
		if(PluginLib.getInstance().isEnabled())
			Bukkit.getScheduler().runTask(PluginLib.getInstance(), ()->Bukkit.getPluginManager().callEvent(e));
		if(!e.isCancelled())
			super.channelRead(c, m);
	  }
	
	public Player getPlayer() {
		return p;
	}
	
}
