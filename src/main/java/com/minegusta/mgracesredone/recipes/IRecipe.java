package com.minegusta.mgracesredone.recipes;

import org.bukkit.inventory.ItemStack;

public interface IRecipe
{
    public RecipeItem[] getIngriedients();

    public String getName();

    public ItemStack getResult();
}
