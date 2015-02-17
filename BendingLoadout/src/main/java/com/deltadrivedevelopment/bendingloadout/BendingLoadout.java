package com.deltadrivedevelopment.bendingloadout;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.plugin.java.JavaPlugin;

public class BendingLoadout extends JavaPlugin {

	public static HashMap<UUID, HashMap<Integer, HashMap<Integer, String>>> loadouts = new HashMap<UUID, HashMap<Integer, HashMap<Integer, String>>>();
	private final String loadoutsPath = getDataFolder().getAbsolutePath()
			+ File.separator + "loadouts.bin";

	public void onEnable() {
		registerListeners();
		registerCommands();
		loadFiles();
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
	}

	public void onDisable() {
		saveFiles();
	}

	public void registerListeners() {
		getServer().getPluginManager().registerEvents(new BendingLoadoutListener(this), this);
	}

	public void registerCommands() {
		getCommand("bendingloadout").setExecutor(new BendingLoadoutExecutor(this));
	}

	public void saveFiles() {
		try {
			SLAPI.save(loadouts, loadoutsPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getLogger()
					.severe("Failed to save player loadouts. All changes made since last restart are LOST.");
		}
	}

	public void loadFiles() {
		try {
			loadouts = SLAPI.load(loadoutsPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getLogger()
					.severe("Failed to load player loadouts. Try Reloading.");
		}
	}

}
