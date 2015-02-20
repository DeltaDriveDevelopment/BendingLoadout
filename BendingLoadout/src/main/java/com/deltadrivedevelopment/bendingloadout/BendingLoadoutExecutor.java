package com.deltadrivedevelopment.bendingloadout;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.projectkorra.ProjectKorra.BendingPlayer;
import com.projectkorra.ProjectKorra.Methods;

public class BendingLoadoutExecutor implements CommandExecutor {

	public BendingLoadoutExecutor() {
	}

	// ************************************************************
	//
	// HashMap<UUID, HashMap<Integer, HashMap<Integer, String>>>
	//
	// ************************************************************

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		
		if(args.length == 0){
			sender.sendMessage(BendingLoadout.getPrefix() + "Bending Loadouts Commands:");
			sender.sendMessage(ChatColor.GOLD + "/bl list");
			sender.sendMessage(ChatColor.GOLD + "/bl [save, s] <name>");
			sender.sendMessage(ChatColor.GOLD + "/bl [load, l] <name>");
			sender.sendMessage(ChatColor.GOLD + "/bl [delete, del, d] <name>");
			return true;
		}

		if (args[0].equalsIgnoreCase("save") || args[0].equalsIgnoreCase("s")) {
			Player player;
			if (!(sender instanceof Player)) {
				sender.sendMessage(BendingLoadout.getPrefix()
						+ "You must be a player to use this command");
				return true;
			} else {
				player = (Player) sender;
			}

			if (args.length != 2) {
				player.sendMessage(BendingLoadout.getPrefix()
						+ "Incorrect amount of arguments, usage /bl [save,s] <name>");
				return true;
			}

			BendingPlayer bPlayer = Methods.getBendingPlayer(player.getName());

			if (bPlayer == null) {
				player.sendMessage(BendingLoadout.getPrefix()
						+ "Internal error. Try again");
				return true;
			}

			HashMap<Integer, String> abilities = new HashMap<Integer, String>(bPlayer.getAbilities()); // Get the currently set abilities
			
			HashMap<String, HashMap<Integer, String>> loadouts;
			
			if (!BendingLoadout.loadouts.containsKey(player.getUniqueId())) {
				loadouts = new HashMap<String, HashMap<Integer,String>>();
			} else {
				loadouts = BendingLoadout.loadouts
						.get(player.getUniqueId()); // Get the saved loadouts
			}

			loadouts.put(args[1], abilities); // add the current loadout to the
												// loadout sets

			BendingLoadout.loadouts.put(player.getUniqueId(), loadouts); // put the modified set into the saved set
			player.sendMessage(BendingLoadout.getPrefix()
					+ "Loadout saved, use /bl load " + args[1] + " To load it!");
			return true;

		}

		if (args[0].equalsIgnoreCase("load") || args[0].equalsIgnoreCase("l")) {
			Player player;
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to use this command!");
				return true;
			} else {
				player = (Player) sender;
			}

			if (args.length != 2) {
				player.sendMessage(BendingLoadout.getPrefix()
						+ "Incorrect amount of arguments, usage /bl [load,l] <name>");
				return true;
			}

			
			if (!BendingLoadout.loadouts.containsKey(player.getUniqueId())) {
				player.sendMessage(BendingLoadout.getPrefix()
						+ "You haven't saved any loadouts yet!");
				return true;
			}

			BendingPlayer bPlayer = Methods.getBendingPlayer(player.getName());
			HashMap<String, HashMap<Integer, String>> loadouts = new HashMap<String, HashMap<Integer, String>>(BendingLoadout.loadouts
					.get(player.getUniqueId()));

			for (String loadout : loadouts.keySet()) {
				if (loadout.equalsIgnoreCase(args[1])) {
					bPlayer.setAbilities(new HashMap<Integer, String>(loadouts.get(loadout)));
					player.sendMessage(BendingLoadout.getPrefix()
							+ "Set binds to loadout " + loadout);
					return true;
				}
			}

			player.sendMessage(BendingLoadout.getPrefix()
					+ "Loadout not found. Use /bl list to list your saved loadouts");
			return true;
		}

		if (args[0].equalsIgnoreCase("list")) {
			Player player;
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to use this command!");
				return true;
			} else {
				player = (Player) sender;
			}

			if (args.length != 1) {
				player.sendMessage(BendingLoadout.getPrefix()
						+ "Too many arguments, usage /bl list");
				return true;
			}

			if (!BendingLoadout.loadouts.containsKey(player.getUniqueId())) {
				player.sendMessage(BendingLoadout.getPrefix()
						+ "You haven't saved any loadouts yet!");
				return true;
			}

			HashMap<String, HashMap<Integer, String>> loadouts = BendingLoadout.loadouts.get(player.getUniqueId());
			String message = "";

			int i = 1;
			for (String name : loadouts.keySet()) {
				if(i == loadouts.size()){
					message = message + name;
					break;
				}
				message = message + name + ", ";
				i++;
			}
			
			player.sendMessage(BendingLoadout.getPrefix() + "Saved Loadouts:");
			player.sendMessage(ChatColor.GOLD + message);
			player.sendMessage(ChatColor.GOLD
					+ "Use /bl load <name> to change loadouts");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("del") || args[0].equalsIgnoreCase("d")){
			Player player;
			
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to use this command!");
				return true;
			} else {
				player = (Player) sender;
			}
			
			if (args.length != 2){
				player.sendMessage(BendingLoadout.getPrefix()
						+ "Incorrect format, usage /bl [delete, del, d] [name]");
				return true;
			}
			
			HashMap<String, HashMap<Integer, String>> loadouts = BendingLoadout.loadouts.get(player.getUniqueId());
			for (String name : loadouts.keySet()) {
				if(name.equalsIgnoreCase(args[1])){
					loadouts.remove(name);
					player.sendMessage(BendingLoadout.getPrefix() + "Successfully removed loadout " + name);
					return true;
				}
			}
			
			player.sendMessage(BendingLoadout.getPrefix() + "Loadout " + args[1] + " not found. Use /bl list to list your currently saved loadouts");
			
			
		}
		
		
		return false;
	}

}
