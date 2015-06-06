package com.minegusta.mgracesredone.recipes;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class AngelFeatherRecipe implements IRecipe {
    private final MGItem[] INGREDIENTS;
    private final ItemStack RESULT;

    public AngelFeatherRecipe() {
        INGREDIENTS = new MGItem[]{new MGItem(Material.FEATHER, 8), new MGItem(Material.DIAMOND_CHESTPLATE, 1)};

        RESULT = new ItemStack(Material.FEATHER, 1);
        List<String> lore = Lists.newArrayList(ChatColor.AQUA + "Angle.");
        ItemMeta meta = RESULT.getItemMeta();
        meta.setDisplayName(getName());
        meta.setLore(lore);
        RESULT.setItemMeta(meta);
    }

    @Override
    public MGItem[] getIngredients() {
        return INGREDIENTS;
    }

    @Override
    public String getName() {
        return "Angel Feather";
    }

    @Override
    public ItemStack getResult() {
        return RESULT.clone();
    }
}
