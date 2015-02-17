package com.deltadrivedevelopment.bendingloadout;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.projectkorra.ProjectKorra.BendingPlayer;

public class BendingLoadoutExecutor implements CommandExecutor {

	private final BendingLoadout plugin;
	
	public BendingLoadoutExecutor(BendingLoadout plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		if(args[0].equalsIgnoreCase("saveloadout")){
			Player player;
			if(!(sender instanceof Player)){
				sender.sendMessage("You must be a player to use this command");
				return true;
			} else {
				player = (Player) sender;
			}
			
			BendingPlayer bPlayer = new BendingPlayer(player.getUniqueId(), player.getName(), null, null, false);
			
		}
		
		
		
		return false;
	}

}
