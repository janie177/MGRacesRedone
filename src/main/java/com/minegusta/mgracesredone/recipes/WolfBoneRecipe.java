package com.minegusta.mgracesredone.recipes;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class WolfBoneRecipe implements IRecipe {
    @Override
    public MGItem[] getIngriedients() {
        return new MGItem[]{new MGItem(Material.BONE, 1), new MGItem(Material.GLOWSTONE, 4), new MGItem(Material.GOLD_INGOT, 4)};
    }

    @Override
    public String getName() {
        return "Wolf Bone";
    }

    @Override
    public ItemStack getResult() {
        return new ItemStack(Material.BONE, 1)
        {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                meta.setDisplayName(ChatColor.DARK_GRAY + getName());
                lore.add(ChatColor.GRAY + "This bone seems drawn to the moon...");
                meta.setLore(lore);
                setItemMeta(meta);
            }
        };
    }
}
