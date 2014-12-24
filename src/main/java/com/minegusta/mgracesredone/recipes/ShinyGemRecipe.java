package com.minegusta.mgracesredone.recipes;

import org.bukkit.inventory.ItemStack;

public class ShinyGemRecipe implements IRecipe {
    @Override
    public RecipeItem[] getIngriedients() {
        return new RecipeItem[0];
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
