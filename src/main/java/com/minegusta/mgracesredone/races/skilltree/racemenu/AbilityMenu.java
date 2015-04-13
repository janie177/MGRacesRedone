package com.minegusta.mgracesredone.races.skilltree.racemenu;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AbilityMenu
{
    public static List<AbilityType> getAbilities(RaceType type)
    {
        List<AbilityType> abilities = Lists.newArrayList();

        for(AbilityType a : AbilityType.values())
        {
            if(a.getRaces().contains(type))abilities.add(a);
        }

        return abilities;
    }

    public static void buildInventory(Player p)
    {
        String name = ChatColor.YELLOW + "Perk Shop. " + ChatColor.RED + "Perk-Points: " + Races.getMGPlayer(p).getPerkPoints();
        Inventory inv = Bukkit.createInventory(null, 45, name);
        MGPlayer mgp = Races.getMGPlayer(p);

        int count = 0;

        for(AbilityType type : getAbilities(mgp.getRaceType()))
        {
            for(int level = 1; level <= type.getMaxLevel(); level++)
            {
                ItemStack is = new ItemStack(type.getAbility().getDisplayItem());
                ItemMeta meta = is.getItemMeta();
                List<String> lore = Lists.newArrayList();

                if(mgp.hasAbility(type) && level <= mgp.getAbilityLevel(type))
                {
                    meta.setDisplayName(ChatColor.DARK_RED + type.getName());
                    lore.add(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "UNLOCKED");
                    lore.add(ChatColor.GRAY + "Level: " + level);
                    for(String s : type.getAbility().getDescription(level))
                    {
                        lore.add(ChatColor.DARK_GRAY + s);
                    }
                }
                else
                {
                    meta.setDisplayName(ChatColor.DARK_RED + type.getName());
                    lore.add(ChatColor.YELLOW + "Cost: " + ChatColor.LIGHT_PURPLE + type.getCost(level));
                    lore.add(ChatColor.YELLOW + "Level: " + ChatColor.LIGHT_PURPLE + level);
                    for(String s : type.getAbility().getDescription(level))
                    {
                        lore.add(ChatColor.GREEN + s);
                    }
                }


                meta.setLore(lore);
                is.setItemMeta(meta);
                inv.setItem(count + (level - 1) * 9, is);

            }
            count++;
        }
        p.openInventory(inv);
    }
}
