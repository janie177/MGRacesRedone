package com.minegusta.mgracesredone.recipes;

import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.inventory.ItemStack;

public interface IRecipe
{
    public MGItem[] getIngriedients();

    public String getName();

    public ItemStack getResult();
}
