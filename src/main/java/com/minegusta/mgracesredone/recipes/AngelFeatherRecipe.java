package com.minegusta.mgracesredone.recipes;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AngelFeatherRecipe implements IRecipe
{
    @Override
    public MGItem[] getIngriedients() {
        return new MGItem[]{new MGItem(Material.FEATHER, 8), new MGItem(Material.DIAMOND_CHESTPLATE, 1)};
    }

    @Override
    public String getName() {
        return ChatColor.DARK_PURPLE + "Angel Feather";
    }

    @Override
    public ItemStack getResult() {
        return new ItemStack(Material.FEATHER, 1)
        {
            {
                List<String> lore = Lists.newArrayList(ChatColor.AQUA + "Angle.");
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(getName());
                meta.setLore(lore);
                setItemMeta(meta);
            }
        };
    }
}
