package com.minegusta.mgracesredone.recipes;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EnderCrystalRecipe implements IRecipe {
    private final MGItem[] INGREDIENTS;
    private final ItemStack RESULT;

    public EnderCrystalRecipe() {
        INGREDIENTS = new MGItem[]{new MGItem(Material.EYE_OF_ENDER, 1), new MGItem(Material.DIAMOND, 4), new MGItem(Material.ENDER_PEARL, 4)};

        RESULT = new ItemStack(Material.EYE_OF_ENDER, 1);
        List<String> lore = Lists.newArrayList();
        lore.add(ChatColor.BLUE + "A strange force is locked in this crystal...");
        ItemMeta meta = RESULT.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + getName());
        meta.setLore(lore);
        RESULT.setItemMeta(meta);
    }

    @Override
    public MGItem[] getIngredients() {
        return INGREDIENTS;
    }

    @Override
    public String getName() {
        return "Ender Crystal";
    }

    @Override
    public ItemStack getResult() {
        return RESULT.clone();
    }
}
