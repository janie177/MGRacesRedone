package com.minegusta.mgracesredone.commands;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.recipes.Recipe;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class RaceCommand implements CommandExecutor {
    private Player p;

    //Help list
    private String[] help = {"/Race Help " + ChatColor.GRAY + "- Show this help menu.", "/Race List " + ChatColor.GRAY + "- Display a list of races.", "/Race Info <Race> " + ChatColor.GRAY + "- Info on the given race.", "/Race Show " + ChatColor.GRAY + "- Show info about your race.", "/Race Info <Race> " + ChatColor.GRAY + "- Info on the given race.", "/Race Reset-Perks " + ChatColor.GRAY + "- Reset all your perks.", "/Race Show " + ChatColor.GRAY + "- Show info about your race.", "/Race Cure " + ChatColor.GRAY + "- Display cure info.", "/Race Infection <Race> " + ChatColor.GRAY + "- Show how to become a race.", "/Race Recipes " + ChatColor.GRAY + "- Show all recipes related to races.", "/Perk " + ChatColor.GRAY + "- Show your race's perk tree.", "/Perk Info <Perk> " + ChatColor.GRAY + "- Show info about a specific perk.", "/Perk List <Race> " + ChatColor.GRAY + "- Show all perks for a specific race."};

    //Command

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!(s instanceof Player)) return true;
        this.p = (Player) s;


        //Check for args here

        if (args.length == 0) {
            sendList(help);
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                sendList(help);
                return true;
            }
            if (args[0].equalsIgnoreCase("list")) {
                sendList(RaceType.values());
                return true;
            }
            if (args[0].equalsIgnoreCase("show")) {
                sendOwnInfo();
                return true;
            }
            if (args[0].equalsIgnoreCase("perks")) {
                p.chat("/perk");
                return true;
            }
            if (args[0].equalsIgnoreCase("reset-perks")) {
                p.chat("/perkreset");
                return true;
            }
            if (args[0].equalsIgnoreCase("cure")) {
                sendList(RaceType.HUMAN.getInfectionInfo());
                return true;
            }
            if (args[0].equalsIgnoreCase("recipes") || args[0].equalsIgnoreCase("recipe")) {
                sendList(Recipe.values());
                return true;
            }
            sendList(help);
            return true;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("info")) {
                try {
                    RaceType race = RaceType.valueOf(args[1].toUpperCase());
                    sendInfo(race);
                } catch (Exception ignored) {
                    ChatUtil.sendString(p, "That is not a valid race! Use /Race List for a list of races.");
                }
                return true;
            }
            if ((args[0].equalsIgnoreCase("infect") || args[0].equalsIgnoreCase("infection")) && RaceType.valueOf(args[1].toUpperCase()) != null) {
                RaceType race = RaceType.valueOf(args[1].toUpperCase());
                sendInfectList(race.getInfectionInfo());
                return true;
            }
            sendList(help);
            return true;
        }
        sendList(help);
        return true;
    }


    private void sendList(String[] list) {
        ChatUtil.sendFancyBanner(p);
        for (String s : list) {
            p.sendMessage(ChatColor.LIGHT_PURPLE + s);
        }
        ChatUtil.sendFooter(p);
    }

    private void sendInfectList(String[] list) {
        boolean first = true;
        ChatUtil.sendFancyBanner(p);
        p.sendMessage(ChatColor.LIGHT_PURPLE + list[0]);
        for (String s : list) {
            if (!first) p.sendMessage(ChatColor.LIGHT_PURPLE + " - " + ChatColor.GRAY + s);
            first = false;
        }
        ChatUtil.sendFooter(p);
    }

    private void sendList(RaceType[] list) {
        ChatUtil.sendFancyBanner(p);
        for (RaceType type : list) {
            p.sendMessage(ChatColor.LIGHT_PURPLE + type.getName());
        }
        ChatUtil.sendFooter(p);
    }

    private void sendList(Recipe[] list) {
        ChatUtil.sendFancyBanner(p);
        for (Recipe recipe : list) {
            p.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + recipe.getRecipeName() + ":");
            for (MGItem item : recipe.getRecipe().getIngredients()) {
                p.sendMessage(ChatColor.YELLOW + " - " + ChatColor.DARK_PURPLE + item.getAmount() + " " + ChatColor.LIGHT_PURPLE + item.getMaterial().toString());
            }
        }
        ChatUtil.sendFooter(p);
    }

    private void sendMessage(String[] text) {
        ChatUtil.sendFancyBanner(p);
        for (String s : text) {
            p.sendMessage(ChatColor.LIGHT_PURPLE + s);
        }
        ChatUtil.sendFooter(p);
    }

    private void sendInfo(RaceType race) {
        ChatUtil.sendFancyBanner(p);
        p.sendMessage(ChatColor.LIGHT_PURPLE + "Race: " + ChatColor.YELLOW + race.getName());
        p.sendMessage(ChatColor.LIGHT_PURPLE + "Health: " + ChatColor.DARK_RED + (race.getHealth() / 2));
        p.sendMessage("");
        for (String s : race.getInfo()) {
            p.sendMessage(ChatColor.LIGHT_PURPLE + s);
        }
        p.sendMessage(ChatColor.YELLOW + "Perks you can unlock: ");
        List<String> perks = Lists.newArrayList();
        boolean gray = true;
        for (AbilityType type : AbilityType.values()) {
            if (type.getRaces().contains(race)) {
                if (gray) {
                    gray = false;
                    perks.add(ChatColor.GRAY + type.getName() + ChatColor.LIGHT_PURPLE + ",");
                } else {
                    gray = true;
                    perks.add(ChatColor.DARK_GRAY + type.getName() + ChatColor.LIGHT_PURPLE + ",");
                }
            }
        }
        for (String s : perks) {
            p.sendMessage(ChatColor.LIGHT_PURPLE + "- " + s);
        }
        p.sendMessage(ChatColor.LIGHT_PURPLE + "Use " + ChatColor.YELLOW + " /Perk Info <Perk> " + ChatColor.LIGHT_PURPLE + "for more info on a perk.");
        ChatUtil.sendFooter(p);
    }

    private void sendOwnInfo() {
        RaceType race = Races.getRace(p);
        ChatUtil.sendFancyBanner(p);
        p.sendMessage(ChatColor.LIGHT_PURPLE + "You are a(n) " + ChatColor.YELLOW + race.getName());
        p.sendMessage(ChatColor.LIGHT_PURPLE + "Your maximum health is " + ChatColor.DARK_RED + (race.getHealth() / 2));
        p.sendMessage("");
        for (String s : race.getInfo()) {
            p.sendMessage(ChatColor.LIGHT_PURPLE + s);
        }
        ChatUtil.sendFooter(p);
    }
}
