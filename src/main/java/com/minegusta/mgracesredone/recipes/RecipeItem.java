package com.minegusta.mgracesredone.recipes;

import org.bukkit.Material;

public class RecipeItem
{
    private Material material;
    private int amount;

    public RecipeItem(Material material, int amount)
    {
        this.material = material;
        this.amount = amount;
    }


    public int getID()
    {
        return material.getId();
    }

    public Material getMaterial()
    {
        return material;
    }

    public int getAmount()
    {
        return amount;
    }
}
