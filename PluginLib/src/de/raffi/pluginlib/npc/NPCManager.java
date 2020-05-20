package de.raffi.pluginlib.npc;

import java.util.ArrayList;
import java.util.List;

import de.raffi.pluginlib.utils.Logger;

public class NPCManager {
	
	public static List<NPC>npcs = new ArrayList<>();
	/**
	 * please use {@link NPC#register()}
	 * @param npc the npc that should be registered
	 */
	public static void registerNPC(NPC npc) {
		Logger.debug("Registered NPC " + npc.getProfile().getName());
		npcs.add(npc);
	}
	/**
	 * please use {@link NPC#unregister()}
	 * @param npc the npc that should be unregistered
	 */
	public static void unregisterNPC(NPC npc) {
		Logger.debug("Unregistered NPC " + npc.getProfile().getName());
		npcs.remove(npc);
	}

}
