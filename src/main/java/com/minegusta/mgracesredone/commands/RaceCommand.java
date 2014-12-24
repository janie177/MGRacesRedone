package com.minegusta.mgracesredone.commands;

import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.RaceCheck;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RaceCommand implements CommandExecutor {
    private Player p;
    private RaceType race;

    //Help list
    private String[] help = {"/Race Help " + ChatColor.GRAY + "- Show this help menu.", "/Race Amount " + ChatColor.GRAY + "- Show how many people there are per race.", "/Race List " + ChatColor.GRAY + "- Display a list of races.", "/Race Info <Race> " + ChatColor.GRAY + "- Info on the given race.", "/Race Show " + ChatColor.GRAY + "- Show info about your race.", "/Race Cure " + ChatColor.GRAY + "- Display cure info.", "/Race Infect <Race> " + ChatColor.GRAY + "- Show how to become a race.", "/Race Recipes " + ChatColor.GRAY + "- Show all recipes related to races."};

    //Command

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args)
    {
        if (!cmd.getName().equalsIgnoreCase("race")) return true;
        if (!(s instanceof Player)) return true;

        this.p = (Player) s;

        race = RaceCheck.getRace(p);

        //Check for args here

        return true;
    }



}
