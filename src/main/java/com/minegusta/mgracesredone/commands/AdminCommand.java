package com.minegusta.mgracesredone.commands;

import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.main.Races;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor

{
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(!s.isOp() || s instanceof ConsoleCommandSender)
        {
            s.sendMessage(ChatColor.RED + "You cannot use that :( Boohoo. -cri-");
            return true;
        }

        if(args.length != 3)
        {
            s.sendMessage(ChatColor.RED + "Invalid command. Usage: ");
            s.sendMessage(ChatColor.GRAY + "/RA Set <Player> <Race>");
            return true;
        }

        if(args[0].equalsIgnoreCase("Set"))
        {
            try
            {
                Player p = Bukkit.getPlayer(args[1]);
                RaceType race = RaceType.valueOf(args[2].toUpperCase());

                Races.setRace(p, race);

                s.sendMessage(ChatColor.GREEN + p.getName() + " is now the race: " + race.getName() + ".");
                p.sendMessage(ChatColor.DARK_GREEN + "You are now a(n) " + race.getName() + "!");
            }
            catch (Exception ignored)
            {
                s.sendMessage(ChatColor.RED + "Invalid race or player. Players have to be online.");
            }
            return true;
        }

        s.sendMessage(ChatColor.RED + "Invalid command. Usage: ");
        s.sendMessage(ChatColor.GRAY + "/RA Set <Player> <Race>");
        return true;
    }
}
