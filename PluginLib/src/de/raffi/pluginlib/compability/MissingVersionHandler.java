package de.raffi.pluginlib.compability;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.raffi.pluginlib.utils.Logger;

public class MissingVersionHandler implements VersionHandler{

	

	@Override
	public int getEntityID(Object entity) {
		Logger.debug("Could not get entityID [INVALID VERSIONHANDLER]");
		return -1;
	}

	@Override
	public boolean getEntitySneaking(Object entity) {
		Logger.debug("Could not get entity sneaking [INVALID VERSIONHANDLER]");
		return false;
	}

	@Override
	public void processPacketPlayOutEquippment(Player p, int entityID, int slot, ItemStack item) {
		Logger.debug("Could not process packet PacketPlayOutEquippment [INVALID VERSIONHANDLER]");
	}

	@Override
	public void processPacketPlayOutEntityDestroy(Player p, int... entityID) {
		Logger.debug("Could not process packet PacketPlayOutEntityDestroy [INVALID VERSIONHANDLER]");
	}

	@Override
	public void processPacketPlayOutEntityTeleport(Player p, Object entity) {
		Logger.debug("Could not process packet PacketPlayOutEntityTeleport [INVALID VERSIONHANDLER]");
	}

	@Override
	public void processPacketPlayOutEntityMetadata(Player p, int entityID, Object dataWatcher, boolean b) {
		Logger.debug("Could not process packet PacketPlayOutEntityMetadata [INVALID VERSIONHANDLER]");		
	}

	@Override
	public void processPacketPlayOutNamedEntitySpawn(Player p, Object entity) {
		Logger.debug("Could not process packet PacketPlayOutNamedEntitySpawn [INVALID VERSIONHANDLER]");
	}

	@Override
	public void processPacketPlayOutEntityTeleport(Player p, int entityID, Location target) {
		Logger.debug("Could not process packet PacketPlayOutEntityTeleportt [INVALID VERSIONHANDLER]");
	}


	@Override
	public void processPacketPlayOutEntityHeadRotation(Player p, Object entity, byte yaw) {
		Logger.debug("Could not process packet PacketPlayOutEntityHeadRotation [INVALID VERSIONHANDLER]");
	}

	@Override
	public void processPacketPlayOutAnimation(Player p, Object entity, int animation) {
		Logger.debug("Could not process packet PacketPlayOutAnimation [INVALID VERSIONHANDLER]");
	}

	@Override
	public void processPacketPlayOutPlayerInfoAddPlayer(Player p, Object... entity) {
		Logger.debug("Could not process packet PacketPlayOutPlayerInfoAddPlayer [INVALID VERSIONHANDLER]");
	}

}
