package de.raffi.pluginlib.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.raffi.pluginlib.compability.CompabilityHandler;
import de.raffi.pluginlib.compability.npchandler.NPCHandlerManager;
import de.raffi.pluginlib.configuration.ConfigurationPluginLib;
import de.raffi.pluginlib.configuration.Configurator;
import de.raffi.pluginlib.data.PacketInjector;
import de.raffi.pluginlib.data.PlayerListener;
import de.raffi.pluginlib.test.InputHandler;

public class PluginLib extends JavaPlugin {

    protected static PluginLib instance;
    protected static String serverVersion;
    private PacketInjector injector;

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
   
   
}