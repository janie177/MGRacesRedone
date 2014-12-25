package com.minegusta.mgracesredone.recipes;

import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public enum Recipe
{
    SHINYGEM(new ShinyGemRecipe()),
    ENDEREYE(new EnderCrystalRecipe()),
    ELFSTEW(new ElfStewRecipe()),
    WOLFBONE(new WolfBoneRecipe()),
    ICECRYSTAL(new IceCrystalRecipe());





    private IRecipe recipe;

    private Recipe(IRecipe recipe)
    {
        this.recipe = recipe;
    }

    public String getRecipeName()
    {
        return recipe.getName();
    }

    public IRecipe getRecipe()
    {
        return recipe;
    }

    public ItemStack getResult()
    {
        return recipe.getResult();
    }

    public MGItem[] getIngredients()
    {
        return recipe.getIngriedients();
    }

    public static void registerRecipes()
    {
        for(Recipe r : Recipe.values())
        {
            ShapelessRecipe recipe = new ShapelessRecipe(r.getResult());
            for(MGItem i : r.getIngredients())
            {
                recipe.addIngredient(i.getAmount(), i.getMaterial());
            }
            Bukkit.addRecipe(recipe);
        }
    }
}
