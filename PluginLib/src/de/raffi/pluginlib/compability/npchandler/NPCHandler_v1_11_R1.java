package de.raffi.pluginlib.compability.npchandler;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_11_R1.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.mojang.authlib.GameProfile;

import de.raffi.pluginlib.compability.CompabilityHandler;
import de.raffi.pluginlib.npc.Animation;
import de.raffi.pluginlib.npc.NPC;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntity.PacketPlayOutEntityLook;
import net.minecraft.server.v1_11_R1.DataWatcher;
import net.minecraft.server.v1_11_R1.DataWatcherObject;
import net.minecraft.server.v1_11_R1.DataWatcherRegistry;
import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.EnumGamemode;
import net.minecraft.server.v1_11_R1.EnumItemSlot;
import net.minecraft.server.v1_11_R1.MinecraftServer;
import net.minecraft.server.v1_11_R1.PacketPlayOutAnimation;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_11_R1.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_11_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_11_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_11_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_11_R1.PlayerConnection;
import net.minecraft.server.v1_11_R1.PlayerInteractManager;
import net.minecraft.server.v1_11_R1.WorldServer;

public class NPCHandler_v1_11_R1 implements NPCHandler{

	@Override
	public Object createEntityPlayerAndTeleport(GameProfile profile, Location loc) {
		MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();

		EntityPlayer entity = new EntityPlayer(nmsServer, nmsWorld, profile,	new PlayerInteractManager(nmsWorld));
		entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		return entity;
	}

	@Override
	public void removeFromTab(GameProfile profile, Player p) {
		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
		PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(profile, 1, EnumGamemode.NOT_SET,CraftChatMessage.fromString(profile.getName())[0]);
		@SuppressWarnings("unchecked")
		List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) CompabilityHandler.getValue(packet, "b");
		players.add(data);
		CompabilityHandler.setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER);
		CompabilityHandler.setValue(packet, "b", players);
		sendPacket(p, packet);
		
	}

	@Override
	public void addToTab(GameProfile profile, Player p) {
		PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo();
		PacketPlayOutPlayerInfo.PlayerInfoData data = packet.new PlayerInfoData(profile, 1, EnumGamemode.NOT_SET,CraftChatMessage.fromString(profile.getName())[0]);
		@SuppressWarnings("unchecked")
		List<PacketPlayOutPlayerInfo.PlayerInfoData> players = (List<PacketPlayOutPlayerInfo.PlayerInfoData>) CompabilityHandler.getValue(packet, "b");
		players.add(data);
		CompabilityHandler.setValue(packet, "a", PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER);
		CompabilityHandler.setValue(packet, "b", players);
		sendPacket(p, packet);
		
	}

	@Override
	public void setHandItem(Object entity, ItemStack set) {
		EntityPlayer ep = (EntityPlayer) entity;
		net.minecraft.server.v1_11_R1.ItemStack stack = CraftItemStack.asNMSCopy(set);
		ep.inventory.setItem(0, stack);
		Bukkit.getOnlinePlayers().forEach(p->sendPacket(p, new PacketPlayOutEntityEquipment(ep.getId(), EnumItemSlot.MAINHAND, stack)));
	}

	@Override
	public void destroy(Object entity, Player p) {
		EntityPlayer ep = (EntityPlayer) entity;
		sendPacket(p, new PacketPlayOutEntityDestroy(ep.getId()));
	}

	@Override
	public void spawn(Object entity, Player p) {
		EntityPlayer ep = (EntityPlayer) entity;
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, ep));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(ep));
        connection.sendPacket(new PacketPlayOutEntityEquipment(ep.getId(), EnumItemSlot.MAINHAND, ep.getItemInMainHand()));
	}

	@Override
	public void setSneaking(Object entity, boolean b, Player p) {
		EntityPlayer ep = (EntityPlayer) entity;
		DataWatcher w =ep.getDataWatcher();
        if (b) 
        	w.set(new DataWatcherObject<>(0, DataWatcherRegistry.a), (byte)2);
         else 
        	w.set(new DataWatcherObject<>(0, DataWatcherRegistry.a), (byte)0);
        sendPacket(p, new PacketPlayOutEntityMetadata(ep.getId(), w, false));
	}
	@Override
	public void setSneaking(Object entity, boolean b) {
		EntityPlayer ep = (EntityPlayer) entity;
		ep.setSneaking(b);
		Bukkit.getOnlinePlayers().forEach(p->sendPacket(p, new PacketPlayOutEntityMetadata(ep.getId(), ep.getDataWatcher(), false)));
	}
	@Override
	public void setAnimation(Object entity, Animation animation, Player p) {
		sendPacket(p, new PacketPlayOutAnimation((EntityPlayer) entity, animation.getId()));
	}

	@Override
	public void setLocation(Object entity, Location loc, Player p) {
		EntityPlayer ep = (EntityPlayer) entity;
		Location tmp = loc.clone();
    	ep.setLocation(loc.getX(), loc.getY(), loc.getZ(), getFixRotation(loc.getYaw()), loc.getPitch());
    	PacketPlayOutEntityTeleport tp = new PacketPlayOutEntityTeleport(ep);
    	PacketPlayOutEntityHeadRotation rot = new PacketPlayOutEntityHeadRotation(ep, getFixRotation(loc.getYaw()));
			try {
				CompabilityHandler.sendPacket(p, tp);
				CompabilityHandler.sendPacket(p, rot);
			} catch (Exception e) {
				e.printStackTrace();
			}
		ep.setLocation(tmp.getX(), tmp.getY(), tmp.getZ(), tmp.getYaw(), tmp.getPitch());
	}
	@Override
	public void setLocation(Object entity, Location loc) {
		EntityPlayer ep = (EntityPlayer) entity;
		ep.setLocation(loc.getX(), loc.getY(), loc.getZ(), getFixRotation(loc.getYaw()), getFixRotation(loc.getPitch()));
    	PacketPlayOutEntityTeleport tp = new PacketPlayOutEntityTeleport(ep);
    	PacketPlayOutEntityHeadRotation rot = new PacketPlayOutEntityHeadRotation(ep, getFixRotation(loc.getYaw()));
		Bukkit.getOnlinePlayers().forEach(p -> {
			sendPacket(p, tp);
			sendPacket(p, rot);
		});
		
	}

	@Override
	public void rotateTo(Object e, Location other, Player p,NPC npc) {
		Entity entity =(Entity)e;
		double dirx = entity.locX - other.getX();
	    double diry = entity.locY - other.getY();
	    double dirz = entity.locZ- other.getZ();

	    double len = Math.sqrt(dirx*dirx + diry*diry + dirz*dirz);

	    dirx /= len;
	    diry /= len;
	    dirz /= len;

	    double pitch = Math.asin(diry);
	    double yaw = Math.atan2(dirz, dirx);

	    //to degree
	    pitch = pitch * 180.0 / Math.PI;
	    yaw = yaw * 180.0 / Math.PI;

	    yaw += 90f;
	    entity.pitch = (float)pitch;
	   	entity.yaw = (float)yaw;
	   	npc.setRotation(p,(float)yaw, (float)pitch);
		
	}

	@Override
	public boolean isSneaking(Object entity) {
		return ((Entity)entity).isSneaking();
	}
	@Override
	public int getID(Object entity) {
		return ((Entity)entity).getId();
	}

	@Override
	public void setRotation(Object entity, float yaw, float pitch, Player p) {
		EntityPlayer ep = (EntityPlayer) entity;
	    PacketPlayOutEntityLook packetPlayOutEntityLook = new PacketPlayOutEntityLook(ep.getId(), this.getFixRotation(yaw), this.getFixRotation(pitch), true);
        PacketPlayOutEntityHeadRotation packetPlayOutEntityHeadRotation = new PacketPlayOutEntityHeadRotation();
        CompabilityHandler.setValue((Object)packetPlayOutEntityHeadRotation, "a", ep.getId());
        CompabilityHandler.setValue((Object)packetPlayOutEntityHeadRotation, "b", this.getFixRotation(yaw));
		sendPacket(p, packetPlayOutEntityLook);
		sendPacket(p, packetPlayOutEntityHeadRotation);
	}

}
