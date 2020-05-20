package de.raffi.pluginlib.compability;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutAnimation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

public class Handler_v1_8_R3 implements VersionHandler{


	@Override
	public void processPacketPlayOutEquippment(Player p, int entityID, int slot, ItemStack item) {
		sendPacket(p, new PacketPlayOutEntityEquipment(entityID, slot, CraftItemStack.asNMSCopy(item)));
	}
	@Override
	public void processPacketPlayOutEntityDestroy(Player p, int... entityID) {
		sendPacket(p, new PacketPlayOutEntityDestroy(entityID));
	}

	@Override
	public void processPacketPlayOutEntityTeleport(Player p, Object entity) {
		sendPacket(p, new PacketPlayOutEntityTeleport((Entity) entity));
		
	}

	@Override
	public void processPacketPlayOutEntityTeleport(Player p, int entityID, Location target) {
		sendPacket(p, new PacketPlayOutEntityTeleport(entityID, target.getBlockX(), target.getBlockY(), target.getBlockZ(), (byte)target.getYaw(), (byte)target.getPitch(), false));
	}

	@Override
	public void processPacketPlayOutEntityHeadRotation(Player p, Object entity, byte yaw) {
		sendPacket(p, new PacketPlayOutEntityHeadRotation((Entity) entity, yaw));
		
	}

	@Override
	public void processPacketPlayOutEntityMetadata(Player p, int entityID, Object dataWatcher, boolean b) {
		sendPacket(p, new PacketPlayOutEntityMetadata(entityID, (DataWatcher) dataWatcher, b));	
	}

	@Override
	public void processPacketPlayOutAnimation(Player p, Object entity, int animation) {
		sendPacket(p, new PacketPlayOutAnimation((Entity) entity, animation));
	}


	@Override
	public void processPacketPlayOutNamedEntitySpawn(Player p, Object entity) {
		sendPacket(p, new PacketPlayOutNamedEntitySpawn((EntityHuman) entity));
		
	}

	@Override
	public void processPacketPlayOutPlayerInfoAddPlayer(Player p, Object... entity) {
		sendPacket(p, new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, (EntityPlayer[]) entity));
		
	}


}
