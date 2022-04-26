package de.raffi.pluginlib.utils;

import java.math.BigDecimal;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
		Bukkit.getScheduler().runTask(PluginLib.getInstance(),
				() -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
						"tellraw " + p.getName() + " [\"\",{\"text\":\"" + yesTxt
								+ "\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\""
								+ yesCmd + "\"}},{\"text\":\"" + noTxt
								+ "\",\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\""
								+ noCmd + "\"}}]"));
	}
	/**
	 * @return returns min if value < min; <br> returns max if value > max <br> returns value if value > min and value < max
	 * @since 1.1-b5
	 */
	public static float beetween(float value, float min, float max) {
		return (float) max(min(value, min), max);
	}
	/**
	 * @return returns min if value < min; <br> returns max if value > max <br> returns value if value > min and value < max
	 * @since 1.1-b5
	 */
	public static int beetween(int value, int min, int max) {
		return  max(min(value, min), max);
	}
	/**
	 * @return random integer beetween min and max
	 * @since 1.1-b5
	 */
	public static int randomInt(int min, int max)
	{
	     return (int) (Math.random()*(max-min))+min;
	}
	/**
	 * @return a when a < max; max when max < a
	 * @since 1.1-b5
	 */
	public static double max(double a, double max) {
		return a > max ? max : a;
	}

	/**
	 * @return a when a < max; max when max < a
	 * @since 1.1-b5
	 */
	public static int max(int a, int max) {
		return a > max ? max : a;
	}

	/**
	 * @return a when a > min; min when a < min
	 * @since 1.1-b5
	 */
	public static double min(double a, double min) {
		return a < min ? min : a;
	}

	/**
	 * @return a when a > min; min when a < min
	 * @since 1.1-b5
	 */
	public static int min(int a, int min) {
		return a < min ? min : a;
	}
	/**
	 * @since 1.1-b5
	 * @return
	 */
	public static long gc() {
		  Runtime rt = Runtime.getRuntime();
		  long used = rt.totalMemory()-rt.freeMemory();
		  System.gc();
		  return used-(rt.totalMemory()-rt.freeMemory());
	  }

	/**
	 * 
	 * @param unrounded
	 * @return rounded
	 * @since 1.1-b5
	 */
	public static double roundDouble(double unrounded) {
		BigDecimal a = new BigDecimal("" + unrounded);
		BigDecimal roundOff = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return roundOff.doubleValue();
	}

	/**
	 * 
	 * @param unrounded
	 * @return rounded
	 * @since 1.1-b5
	 */
	public static float roundFloat(float unrounded) {
		BigDecimal a = new BigDecimal("" + unrounded);
		BigDecimal roundOff = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return roundOff.floatValue();
	}

	/**
	 * 
	 * @param bytes
	 * @return rounded value in mb
	 * @since 1.1-b5
	 */
	public static double getMBRounded(long bytes) {
		double r = bytes / (1024.0 * 1024.0);
		r = roundDouble(r);
		return r;
	}
	/**
	 * 
	 * @param bytes
	 * @return rounded value in mb
	 * @since 1.1-b5
	 */
	public static double getMB(long bytes) {
		return bytes / (1024.0 * 1024.0);
	}
	/**
	 * more speed is more efficient
	 * @param e
	 * @param cb
	 * @param speed recommended 0.3F
	 * @param range
	 * @since 1.1-b5
	 */
	public static void traceLivingEntityAsync(LivingEntity e, CallbackTrace<LivingEntity> cb, float range, float speed, boolean continueWhenEntityFound) {
		Location start = e.getLocation().clone();
		Bukkit.getScheduler().runTaskAsynchronously(PluginLib.getInstance(), ()->{
			Location trace = e.getEyeLocation().add(e.getEyeLocation().getDirection().clone().multiply(speed));
			while ((trace = trace.add(e.getEyeLocation().getDirection().clone().multiply(speed))).distance(start)<range) {
				Iterator<Entity> it = trace.getWorld().getNearbyEntities(trace, 0.1, 0.1, 0.1).iterator();
				if(it.hasNext()) {
					Entity i = it.next();
					if(cb.validate(e, i, trace)) {
						cb.onFinish((LivingEntity) i);
						if(!continueWhenEntityFound)
							break;
					}
				}
			}
		});
	}
}
