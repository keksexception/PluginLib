package de.raffi.pluginlib.compability.npchandler;

import de.raffi.pluginlib.main.PluginLib;
import de.raffi.pluginlib.utils.Logger;

public class NPCHandlerManager {
	
	public static final String DEFAULT_PATH = "de.raffi.pluginlib.compability.npchandler";
	public static NPCHandler npcHandler;
	
	/**
	 * example name of versionahandler: {@code NPCHandler_v1_8_R3}
	 * @param packagePath the package where a npchandler is
	 * @return {@code true} if success <br> {@code false} if no versionhandler was found
	 */
	public static boolean findHandler(String packagePath) {
		Logger.debug("Searching for NPCHandler in " + packagePath);
		try {
			npcHandler = (NPCHandler)Class.forName(packagePath + ".NPCHandler_" + PluginLib.getServerVersion()).newInstance();
			Logger.debug("Found NPCHandler: " + npcHandler.getClass().getName());
			return true;
		} catch (Exception e) {
			Logger.debug("Error: Could not find any working NPCHandler! [NO_HANDLER_FOUND]");
			return false;
		}
	}

}
