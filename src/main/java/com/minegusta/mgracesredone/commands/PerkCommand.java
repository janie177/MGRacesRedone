package com.minegusta.mgracesredone.commands;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.racemenu.AbilityMenu;
import com.minegusta.mgracesredone.recipes.Recipe;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PerkCommand implements CommandExecutor
{
    private String[] help = {"/Perk " + ChatColor.GRAY + "- Open the perk interface.", "/Perk List <Race> " + ChatColor.GRAY + "- Display all perks for a race.", "/Perk info <Perk> " + ChatColor.GRAY + "- Show info on a perk."};


    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args)
    {
        if(!(s instanceof Player))return true;

        Player p = (Player) s;

        if(args.length == 0)
        {
            ChatUtil.sendString(p, "You opened the perk interface.");
            AbilityMenu.buildInventory(p);
            return true;
        }

        else if(args.length > 1)
        {
            if(args[0].equalsIgnoreCase("info"))
            {
                try
                {
                    AbilityType a = null;

                    String name = "";

                    for(int i = 1; i < args.length; i++)
                    {
                        name = name + args[i];
                        if(i < args.length - 1)
                        {
                            name = name + " ";
                        }
                    }

                    for(AbilityType type : AbilityType.values())
                    {
                        if(type.getName().equalsIgnoreCase(name))
                        {
                            a = type;
                            break;
                        }
                    }

                    if(a == null)
                    {
                        ChatUtil.sendString(p, "That ability cannot be found. Did you spell it right?");
                        return true;
                    }

                    sendInfo(a, p);
                }
                catch (Exception ignored)
                {
                    sendList(help, p);
                }
                return true;
            }
            else if(args[0].equalsIgnoreCase("list"))
            {
                try
                {
                    RaceType race = RaceType.valueOf(args[1].toUpperCase());

                    ChatUtil.sendFancyBanner(p);

                    p.sendMessage(ChatColor.YELLOW + " ");
                    p.sendMessage(ChatColor.YELLOW + race.getName() + " has the following perks:");

                    List<String> perks = Lists.newArrayList();
                    boolean gray = true;
                    for(AbilityType type : AbilityType.values())
                    {
                        if(type.getRaces().contains(race))
                        {
                            if(gray)
                            {
                                gray = false;
                                perks.add(ChatColor.GRAY + type.getName() + ChatColor.LIGHT_PURPLE + ",");
                            }
                            else
                            {
                                gray = true;
                                perks.add(ChatColor.DARK_GRAY + type.getName() + ChatColor.LIGHT_PURPLE + ",");
                            }
                        }
                    }
                    for(String string : perks)
                    {
                        p.sendMessage(ChatColor.LIGHT_PURPLE + "- " + string);
                    }
                    p.sendMessage(ChatColor.YELLOW + " ");
                    p.sendMessage(ChatColor.YELLOW + "Use " + ChatColor.LIGHT_PURPLE + "/Perk Info <Perk> " + ChatColor.YELLOW + "for more info.");

                    ChatUtil.sendFooter(p);
                }
                catch (Exception ignored)
                {
                    sendList(help, p);
                }
            }
            return true;
        }

        sendList(help, p);
        return true;
    }

    private void sendInfo(AbilityType a, Player p)
    {
        String races = "";
        for(RaceType race : a.getRaces())
        {
            races = races + race.getName() + ", ";
        }
        ChatUtil.sendFancyBanner(p);
        p.sendMessage(ChatColor.LIGHT_PURPLE + "Ability: " + ChatColor.YELLOW + a.getName());
        p.sendMessage(ChatColor.LIGHT_PURPLE + "Type: " + ChatColor.GREEN + (a.getGroup()));
        p.sendMessage(ChatColor.LIGHT_PURPLE + "Cooldown: " + ChatColor.DARK_RED + a.getCooldown(1));
        p.sendMessage(ChatColor.LIGHT_PURPLE + "Races: " + ChatColor.YELLOW + races);
        p.sendMessage("");

        for(int i = 1; i <= a.getMaxLevel(); i++)
        {
            p.sendMessage(ChatColor.YELLOW + "Level " + ChatColor.LIGHT_PURPLE + i + ChatColor.YELLOW + ":");
            for(String string : a.getDescriontion(i))
            {
                p.sendMessage(ChatColor.GRAY + " - " + ChatColor.LIGHT_PURPLE + string);
            }
        }
        ChatUtil.sendFooter(p);
    }

    private void sendList(String[] list, Player p)
    {
        ChatUtil.sendFancyBanner(p);
        for(String s : list)
        {
            p.sendMessage(ChatColor.LIGHT_PURPLE + s);
        }
        ChatUtil.sendFooter(p);
    }
}
