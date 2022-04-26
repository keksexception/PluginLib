package de.raffi.pluginlib.main;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.raffi.pluginlib.compability.CompabilityHandler;
import de.raffi.pluginlib.compability.npchandler.NPCHandlerManager;
import de.raffi.pluginlib.configuration.ConfigurationPluginLib;
import de.raffi.pluginlib.configuration.Configurator;
import de.raffi.pluginlib.data.PacketInjector;
import de.raffi.pluginlib.data.PlayerListener;
import de.raffi.pluginlib.event.PluginLibStartupEvent;
import de.raffi.pluginlib.test.InputHandler;
import de.raffi.pluginlib.utils.AsyncUpdateTask;
import de.raffi.pluginlib.utils.Logger;

public class PluginLib extends JavaPlugin {

    protected static PluginLib instance;
    protected static String serverVersion;
    private PacketInjector injector;
    /**
     * higher plugin version = higher number
     * @since 1.0-b2
     */
    public static final int API_VERSION = 4;

    @Override
    public void onEnable() {
        instance = this;
        Configurator.load(ConfigurationPluginLib.class, "plugins/PluginLib");
        serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        CompabilityHandler.findVersionHandler(CompabilityHandler.DEFAULT_PATH);
        NPCHandlerManager.findHandler(NPCHandlerManager.DEFAULT_PATH);
        injector = new PacketInjector();
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListener(), this);
        pm.registerEvents(new InputHandler(), this);
        
        
        try {
        	getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		} catch (Exception e) {
			Logger.debug("Unable to register outgoing plugin channel: BungeeCord");
		}
        Logger.debug("Creating PluginLibStartupEvent!");
        pm.callEvent(new PluginLibStartupEvent(this));
    }
    public static PluginLib getInstance() {
        return instance;
    }
    public static String getServerVersion() {
		return serverVersion;
	}
    public PacketInjector getInjector() {
		return injector;
	}
    public static void dispatchConsoleCommand(String command) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
	}
    /**
     * 
     * @since 1.1-b5
     */
	public static String locationToString(Location loc) {
		Objects.requireNonNull(loc, "Location is null!");
		return loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch();
	}
	  /**
     * 
     * @since 1.1-b5
     */
	public static Location locationFromString(String s) {
		Objects.requireNonNull(s, "String is null!");
		String[] s2 = s.split(";");
		World world = Bukkit.getWorld(s2[0]);
		double x = Double.valueOf(s2[1]);
		double y = Double.valueOf(s2[2]);
		double z = Double.valueOf(s2[3]);		
		float yaw = Float.valueOf(s2[4]);
		float pitch = Float.valueOf(s2[5]);
		return new Location(world, x, y, z, yaw, pitch);
	}
	/**
	 * 
	 * @param size
	 * @param title
	 * @param task
	 * @return
	 * @since 1.1-b5
	 */
	@SuppressWarnings("deprecation")
	public static Inventory createInventory(int size, String title, AsyncUpdateTask task) {
		Objects.requireNonNull(task, "AsyncUpdateTask is null!");
		Objects.requireNonNull(title, "Title is null!");
		Inventory inv =  Bukkit.createInventory(null, size, title);
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(instance, new BukkitRunnable() {
			
			@Override
			public void run() {
				if(!task.update(inv)) cancel();
			}
		}, task.getUpdateDelay(),task.getUpdateDelay());
		return inv;
	}
	
   
}