package de.raffi.pluginlib.builder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.codec.binary.Base64;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class SkullBuilder {
	
	private String skullOwner;
	private ItemStack item;
	private SkullMeta skullMeta;
	

	public SkullBuilder(Player skullOwner) {
		this.skullOwner = skullOwner.getName();
		this.item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		this.skullMeta = (SkullMeta) item.getItemMeta();
	}
	public SkullBuilder(String skullOwner) {
		this.skullOwner = skullOwner;
		this.item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
		this.skullMeta = (SkullMeta) item.getItemMeta();
	}
	public SkullBuilder setLore(String... lore) {
		skullMeta.setLore(Arrays.asList(lore));
		return this;
	}
	public SkullBuilder setName(String name) {
		skullMeta.setDisplayName(name);
		return this;
	}
	public ItemStack build() {
		skullMeta.setOwner(skullOwner);
		item.setItemMeta(skullMeta);
		return item;
	}
	public SkullBuilder setAmount(int amount) {
		item.setAmount(amount);
		return this;				
	}
	public ItemStack getSkull(String url) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        if(url.isEmpty())return head;
     
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }
}
