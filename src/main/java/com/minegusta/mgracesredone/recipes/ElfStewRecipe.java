package com.minegusta.mgracesredone.recipes;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ElfStewRecipe implements IRecipe {
    private final MGItem[] INGREDIENTS;
    private final ItemStack RESULT;

    public ElfStewRecipe() {
        INGREDIENTS = new MGItem[]{new MGItem(Material.LEAVES, 2), new MGItem(Material.CARROT_ITEM, 2), new MGItem(Material.POTATO_ITEM, 2), new MGItem(Material.GLOWSTONE, 3)};

        RESULT = new ItemStack(Material.MUSHROOM_SOUP, 1);
        ItemMeta meta = RESULT.getItemMeta();
        List<String> lore = Lists.newArrayList();
        lore.add(ChatColor.GREEN + "Vegan stew, loved by elves..");
        meta.setDisplayName(ChatColor.DARK_GREEN + getName());
        meta.setLore(lore);
        RESULT.setItemMeta(meta);
    }

    @Override
    public MGItem[] getIngredients() {
        return INGREDIENTS;
    }

    @Override
    public String getName() {
        return "Elf Stew";
    }

    @Override
    public ItemStack getResult() {
        return RESULT.clone();
    }
}
