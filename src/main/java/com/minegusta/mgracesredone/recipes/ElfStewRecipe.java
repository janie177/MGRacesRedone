package com.minegusta.mgracesredone.recipes;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ElfStewRecipe implements IRecipe {
    @Override
    public MGItem[] getIngriedients() {
        return new MGItem[]{new MGItem(Material.LEAVES, 2), new MGItem(Material.CARROT_ITEM, 2), new MGItem(Material.POTATO_ITEM, 2), new MGItem(Material.GLOWSTONE, 3)};
    }

    @Override
    public String getName() {
        return "Elf Stew";
    }

    @Override
    public ItemStack getResult() {
        return new ItemStack(Material.MUSHROOM_SOUP, 1)
        {
            {
                ItemMeta meta = getItemMeta();
                List<String> lore = Lists.newArrayList();
                lore.add(ChatColor.GREEN + "Vegan stew, loved by elves..");
                meta.setDisplayName(ChatColor.DARK_GREEN + getName());
                meta.setLore(lore);
                setItemMeta(meta);
            }
        };
    }
}
