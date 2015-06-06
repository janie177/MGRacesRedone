package com.minegusta.mgracesredone.recipes;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ShinyGemRecipe implements IRecipe {
    private final MGItem[] INGREDIENTS;
    private final ItemStack RESULT;

    public ShinyGemRecipe() {
        INGREDIENTS = new MGItem[]{new MGItem(Material.NETHER_STAR, 1), new MGItem(Material.GOLD_BLOCK, 4), new MGItem(Material.GOLD_INGOT, 4)};
        ;

        RESULT = new ItemStack(Material.NETHER_STAR, 1);
        List<String> lore = Lists.newArrayList();
        lore.add(ChatColor.WHITE + "The Arkenstone... Heart of the mountain...");
        ItemMeta meta = RESULT.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + getName());
        meta.setLore(lore);
        RESULT.setItemMeta(meta);
    }

    @Override
    public MGItem[] getIngredients() {
        return INGREDIENTS;
    }

    @Override
    public String getName() {
        return "Shiny Gem";
    }

    @Override
    public ItemStack getResult() {
        return RESULT.clone();
    }
}
