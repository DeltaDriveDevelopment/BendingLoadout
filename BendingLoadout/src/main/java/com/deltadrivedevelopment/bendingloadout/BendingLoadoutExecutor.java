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

	private final BendingLoadout plugin;
	
	public BendingLoadoutExecutor(BendingLoadout plugin) {
		this.plugin = plugin;
	}
	
	//************************************************************
	//
	//  HashMap<UUID, HashMap<Integer, HashMap<Integer, String>>>
	//
	//************************************************************
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		if(args[0].equalsIgnoreCase("save")){
			Player player;
			if(!(sender instanceof Player)){
				sender.sendMessage(BendingLoadout.getPrefix() + "You must be a player to use this command");
				return true;
			} else {
				player = (Player) sender;
			}
			
			if(args.length != 2){
				player.sendMessage(BendingLoadout.getPrefix() + "Incorrect amount of arguments, usage /bl save <name>");
				return true;
			}
			
			BendingPlayer bPlayer = Methods.getBendingPlayer(player.getName());
			
			if(bPlayer == null){
				player.sendMessage(BendingLoadout.getPrefix() + "Internal error. Try again");
				return true;
			}
			
			HashMap<Integer, String> abilities = bPlayer.getAbilities(); //Get the currently set abilities
			
			HashMap<String, HashMap<Integer, String>> loadouts = BendingLoadout.loadouts.get(player.getUniqueId()); //Get the saved loadouts
			
			loadouts.put(args[1], abilities); //add the current loadout to the loadout sets
			
			BendingLoadout.loadouts.replace(player.getUniqueId(), loadouts); //put the modified set into the saved set
			player.sendMessage(BendingLoadout.getPrefix() + "Loadout saved, use /bl load " + args[1] + " To load it!");
			return true;
			
		}
		
		if(args[0].equalsIgnoreCase("load")){
			Player player;
			if(!(sender instanceof Player)){
				sender.sendMessage("You must be a player to use this command!");
				return true;
			} else {
				player = (Player) sender;
			}
			
			if(args.length != 2){
				player.sendMessage(BendingLoadout.getPrefix() + "Incorrect amount of arguments, usage /bl load <name>");
				return true;
			}
			
			
			BendingPlayer bPlayer = Methods.getBendingPlayer(player.getName());
			HashMap<String, HashMap<Integer, String>> loadouts = BendingLoadout.loadouts.get(player.getUniqueId());
			
			for(String loadout: loadouts.keySet()){
				if(loadout.equalsIgnoreCase(args[1])){
					bPlayer.setAbilities(loadouts.get(loadout));
					player.sendMessage(BendingLoadout.getPrefix() + "Set binds to loadout " + loadout);
					return true;
				}
			}
			
			player.sendMessage(BendingLoadout.getPrefix() + "Loadout not found. Use /bl list to list your saved loadouts");
			return true;
		}
		
		if(args[0].equalsIgnoreCase("list")){
			Player player;
			if(!(sender instanceof Player)){
				sender.sendMessage("You must be a player to use this command!");
				return true;
			} else {
				player = (Player) sender;
			}
			
			if(args.length != 1){
				player.sendMessage(plugin.getPrefix() + "Too many arguments, usage /bl list");
				return true;
			}
			
			HashMap<String, HashMap<Integer, String>> loadouts = plugin.loadouts.get(player.getUniqueId());
			String message = "";
			
			for(String name: loadouts.keySet()){
				message = message + name + ", ";
			}
			
			player.sendMessage(plugin.getPrefix() + "Saved Loadouts:");
			player.sendMessage(ChatColor.GOLD + message);
			player.sendMessage(ChatColor.GOLD + "Use /bl load <name> to change loadouts");
			return true;
		}
		return false;
	}

}
