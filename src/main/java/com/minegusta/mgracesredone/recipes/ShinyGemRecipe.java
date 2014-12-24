package com.minegusta.mgracesredone.recipes;

import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.inventory.ItemStack;

public class ShinyGemRecipe implements IRecipe {
    @Override
    public MGItem[] getIngriedients() {
        return new MGItem[0];
    }

    @Override
    public String getName() {
        return "Shiny Gem";
    }

    @Override
    public ItemStack getResult() {
        return null;
    }
}
