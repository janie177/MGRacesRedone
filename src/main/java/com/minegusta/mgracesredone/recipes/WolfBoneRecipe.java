package com.minegusta.mgracesredone.recipes;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class WolfBoneRecipe implements IRecipe {
    private final MGItem[] INGREDIENTS;
    private final ItemStack RESULT;

    public WolfBoneRecipe() {
        INGREDIENTS = new MGItem[]{new MGItem(Material.BONE, 1), new MGItem(Material.GLOWSTONE, 4), new MGItem(Material.GOLD_INGOT, 4)};

        RESULT = new ItemStack(Material.BONE, 1);
        List<String> lore = Lists.newArrayList();
        lore.add(ChatColor.GRAY + "This bone seems drawn to the moon...");
        ItemMeta meta = RESULT.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + getName());
        meta.setLore(lore);
        RESULT.setItemMeta(meta);
    }

    @Override
    public MGItem[] getIngredients() {
        return INGREDIENTS;
    }

    @Override
    public String getName() {
        return "Wolf Bone";
    }

    @Override
    public ItemStack getResult() {
        return RESULT.clone();
    }
}
