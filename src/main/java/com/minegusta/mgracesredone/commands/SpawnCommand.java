package com.minegusta.mgracesredone.commands;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.util.SpawnLocationUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 1 && sender.hasPermission("minegusta.spawn.other")) {
			String player = args[0];
			try {
				Player p = Bukkit.getPlayer(player);
				p.sendMessage(ChatColor.YELLOW + "You have been teleported to the spawn by " + sender.getName() + ".");
				sender.sendMessage(ChatColor.YELLOW + "You teleported " + p.getDisplayName() + ChatColor.YELLOW + " to the spawn.");
				if (WorldCheck.isEnabled(p.getWorld())) p.teleport(SpawnLocationUtil.getSpawn(Races.getRace(p)));
				else p.teleport(p.getWorld().getSpawnLocation());
				return true;
			} catch (Exception ignored) {
				sender.sendMessage(ChatColor.YELLOW + "That player could not be found.");
				return true;
			}
		}
		if (sender instanceof Player && sender.hasPermission("minegusta.spawn")) {
			if (!WorldCheck.isEnabled(((Player) sender).getWorld())) {
				((Player) sender).teleport(((Player) sender).getWorld().getSpawnLocation());
			} else {
				((Player) sender).teleport(SpawnLocationUtil.getSpawn(Races.getRace((Player) sender)));
			}
			sender.sendMessage(ChatColor.YELLOW + "You teleported to the spawn.");
		}
		return true;
	}
}
