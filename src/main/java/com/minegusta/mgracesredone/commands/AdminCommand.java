package com.minegusta.mgracesredone.commands;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.SpawnLocationUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor

{
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!s.isOp()) {
            s.sendMessage(ChatColor.RED + "You cannot use that :( Boohoo. -cri-");
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("setspawn") && s instanceof Player) {
            Player p = (Player) s;

            if (!WorldCheck.isEnabled(p.getWorld())) {
                p.sendMessage(ChatColor.RED + "This world does not have races enabled.");
                return true;
            }

            String race = args[1];
            try {
                RaceType raceType = RaceType.valueOf(race.toUpperCase());
                SpawnLocationUtil.setSpawn(raceType, ((Player) s).getLocation());
                if (raceType == RaceType.HUMAN) {
                    p.getWorld().setSpawnLocation((int) p.getLocation().getX(), (int) p.getLocation().getY(), (int) p.getLocation().getZ());
                }
                s.sendMessage(ChatColor.GREEN + "Spawn for " + raceType.name() + " is now set at your location.");
            } catch (Exception ignored) {
            }
            return true;
        }

        if (args.length < 3) {
            s.sendMessage(ChatColor.RED + "Invalid command. Usage: ");
            s.sendMessage(ChatColor.GRAY + "/RA Set <Player> <Race>");
            s.sendMessage(ChatColor.GRAY + "/RA PerkPoints <add/set/remove> <Player> <amount>");
            s.sendMessage(ChatColor.GRAY + "/RA SetSpawn <Race>");
            return true;
        }

        if (args[0].equalsIgnoreCase("Set")) {
            try {
                Player p = Bukkit.getPlayer(args[1]);
                RaceType race = RaceType.valueOf(args[2].toUpperCase());

                Races.setRace(p, race);

                s.sendMessage(ChatColor.GREEN + p.getName() + " is now the race: " + race.getName() + ".");
                p.sendMessage(ChatColor.DARK_GREEN + "You are now a(n) " + race.getName() + "!");
            } catch (Exception ignored) {
                s.sendMessage(ChatColor.RED + "Invalid race or player. Players have to be online.");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("perkpoints")) {
            if (args[1].equalsIgnoreCase("add")) {
                try {
                    Player p = Bukkit.getPlayer(args[2]);
                    int amount = Integer.parseInt(args[3]);

                    MGPlayer mgp = Races.getMGPlayer(p);
                    if (amount < 0) amount = 0;
                    mgp.addPerkPoints(amount);

                    s.sendMessage(ChatColor.GREEN + p.getName() + " now has " + mgp.getPerkPoints() + " PerkPoints.");
                    p.sendMessage(ChatColor.DARK_GREEN + "You now have " + mgp.getPerkPoints() + " PerkPoints!");
                } catch (Exception ignored) {
                    s.sendMessage(ChatColor.RED + "Invalid command. Usage: ");
                    s.sendMessage(ChatColor.GRAY + "/RA Set <Player> <Race>");
                    s.sendMessage(ChatColor.GRAY + "/RA PerkPoints <add/set/remove> <Player> <amount>");
                    s.sendMessage(ChatColor.GRAY + "/RA SetSpawn <Race>");
                }
                return true;
            } else if (args[1].equalsIgnoreCase("set")) {
                try {
                    Player p = Bukkit.getPlayer(args[2]);
                    int amount = Integer.parseInt(args[3]);

                    MGPlayer mgp = Races.getMGPlayer(p);
                    if (amount < 0) amount = 0;
                    mgp.setPerkPoints(amount);

                    s.sendMessage(ChatColor.GREEN + p.getName() + " now has " + mgp.getPerkPoints() + " PerkPoints.");
                    p.sendMessage(ChatColor.DARK_GREEN + "You now have " + mgp.getPerkPoints() + " PerkPoints!");
                } catch (Exception ignored) {
                    s.sendMessage(ChatColor.RED + "Invalid command. Usage: ");
                    s.sendMessage(ChatColor.GRAY + "/RA Set <Player> <Race>");
                    s.sendMessage(ChatColor.GRAY + "/RA PerkPoints <add/set/remove> <Player> <amount>");
                    s.sendMessage(ChatColor.GRAY + "/RA SetSpawn <Race>");
                }
                return true;
            } else if (args[1].equalsIgnoreCase("remove")) {
                try {
                    Player p = Bukkit.getPlayer(args[2]);
                    int amount = Integer.parseInt(args[3]);

                    MGPlayer mgp = Races.getMGPlayer(p);
                    if (amount < 0) amount = 0;
                    mgp.removePerkPoints(amount);

                    s.sendMessage(ChatColor.GREEN + p.getName() + " now has " + mgp.getPerkPoints() + " PerkPoints.");
                    p.sendMessage(ChatColor.DARK_GREEN + "You now have " + mgp.getPerkPoints() + " PerkPoints!");
                } catch (Exception ignored) {
                    s.sendMessage(ChatColor.RED + "Invalid command. Usage: ");
                    s.sendMessage(ChatColor.GRAY + "/RA Set <Player> <Race>");
                    s.sendMessage(ChatColor.GRAY + "/RA PerkPoints <add/set/remove> <Player> <amount>");
                    s.sendMessage(ChatColor.GRAY + "/RA SetSpawn <Race>");
                }
                return true;
            }
            return true;
        }

        s.sendMessage(ChatColor.RED + "Invalid command. Usage: ");
        s.sendMessage(ChatColor.GRAY + "/RA Set <Player> <Race>");
        s.sendMessage(ChatColor.GRAY + "/RA SetSpawn <Race>");
        return true;
    }
}
