package com.minegusta.mgracesredone.recipes;

import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.inventory.ItemStack;

public interface IRecipe {
    MGItem[] getIngredients();

    String getName();

    ItemStack getResult();
}
