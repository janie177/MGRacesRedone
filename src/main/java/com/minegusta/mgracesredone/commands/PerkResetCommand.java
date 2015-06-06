package com.minegusta.mgracesredone.commands;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.ChatUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentMap;

public class PerkResetCommand implements CommandExecutor {
    public static ConcurrentMap<String, Integer> commandCount = Maps.newConcurrentMap();

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) return false;

        Player p = (Player) s;

        if (!commandCount.containsKey(p.getUniqueId().toString())) {
            ChatUtil.sendString(p, "Are you absolutely sure? You will lose alot of spent points.");
            p.sendMessage(ChatColor.RED + "Use the command again to confirm.");
            commandCount.put(p.getUniqueId().toString(), 1);
            return true;
        }
        if (commandCount.containsKey(p.getUniqueId().toString()) && commandCount.get(p.getUniqueId().toString()) == 1) {
            ChatUtil.sendString(p, "You will only get 25% of your spent points back.");
            p.sendMessage(ChatColor.RED + "Are you ENTIRELY sure?");
            p.sendMessage(ChatColor.RED + "Use the command one last time to reset all your perks.");
            commandCount.put(p.getUniqueId().toString(), 2);
            return true;
        } else if (commandCount.containsKey(p.getUniqueId().toString()) && commandCount.get(p.getUniqueId().toString()) == 2) {
            ChatUtil.sendString(p, "Your perks have been reset.");
            ChatUtil.sendString(p, "You have been returned 25% of your spent points.");

            MGPlayer mgp = Races.getMGPlayer(p);

            int totalAbilities = 0;
            for (AbilityType t : mgp.getAbilities().keySet()) {
                for (int levels = 1; levels <= mgp.getAbilityLevel(t); levels++) {
                    totalAbilities = totalAbilities + t.getCost(levels);
                }
            }

            mgp.addPerkPoints(totalAbilities / 4);
            mgp.clearAbilities();
            mgp.saveFile();
            commandCount.remove(p.getUniqueId().toString());
            return true;
        }
        return true;
    }
}
