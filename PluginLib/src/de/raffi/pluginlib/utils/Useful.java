package de.raffi.pluginlib.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.raffi.pluginlib.main.PluginLib;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;

public class Useful {
	
	  public static float getLookAtYaw(Vector motion) {
	        double dx = motion.getX();
	        double dz = motion.getZ();
	        double yaw = 0;
	        // Set yaw
	        if (dx != 0) {
	            // Set yaw start value based on dx
	            if (dx < 0) {
	                yaw = 1.5 * Math.PI;
	            } else {
	                yaw = 0.5 * Math.PI;
	            }
	            yaw -= Math.atan(dz / dx);
	        } else if (dz < 0) {
	            yaw = Math.PI;
	        }
	        return (float) (-yaw * 180 / Math.PI - 90);
	  }
	  public static String getClientLang(Player p) {
		  return p.spigot().getLocale();
	  }
	  public static IChatBaseComponent toIChatBaseComponent(String message) {
			return ChatSerializer.a("{\"text\": \"" + message + "\"}");
	  }
	  public static void sendYesNoChoice(Player p, String yesTxt, String noTxt, String yesCmd, String noCmd) {
		  Bukkit.getScheduler().runTask(PluginLib.getInstance(), ()->Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + p.getName() + " [\"\",{\"text\":\"" + yesTxt +"\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + yesCmd + "\"}},{\"text\":\"" + noTxt + "\",\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + noCmd + "\"}}]"));
	  }

}
