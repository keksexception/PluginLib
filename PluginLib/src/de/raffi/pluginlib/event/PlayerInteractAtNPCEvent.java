package de.raffi.pluginlib.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import de.raffi.pluginlib.npc.NPC;
import de.raffi.pluginlib.npc.NPCAction;

public class PlayerInteractAtNPCEvent extends Event{
	
	private Player player;
	private NPCAction action;
	private NPC interacted;
	
	
	
	public PlayerInteractAtNPCEvent(Player player, NPCAction action, NPC interacted) {
		this.player = player;
		this.action = action;
		this.interacted = interacted;
	}
	public NPC getNPC() {
		return interacted;
	}
	public Player getPlayer() {
		return player;
	}
	public NPCAction getAction() {
		return action;
	}
	
	private static final HandlerList handlers = new HandlerList();
	public static HandlerList getHandlerList() {
		return handlers;
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
