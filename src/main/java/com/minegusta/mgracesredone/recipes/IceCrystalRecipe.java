package com.minegusta.mgracesredone.recipes;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class IceCrystalRecipe implements IRecipe {
    @Override
    public MGItem[] getIngriedients() {
        return new MGItem[]{new MGItem(Material.DIAMOND, 1), new MGItem(Material.SNOW_BLOCK, 4), new MGItem(Material.SNOW_BALL, 4)};
    }

    @Override
    public String getName() {
        return "Ice Crystal";
    }

    @Override
    public ItemStack getResult()
    {
        return new ItemStack(Material.DIAMOND, 1)
        {
            {
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.AQUA + "The essence of Aurora contained within...");
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.DARK_AQUA + getName());
                meta.setLore(lore);
                setItemMeta(meta);
            }
        };
    }
}
