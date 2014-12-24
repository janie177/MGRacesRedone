package com.minegusta.mgracesredone.recipes;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public enum Recipe
{
    SHINYGEM(new ShinyGemRecipe());





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

    public RecipeItem[] getIngredients()
    {
        return recipe.getIngriedients();
    }

    public static void registerRecipes()
    {
        for(Recipe r : Recipe.values())
        {
            ShapelessRecipe recipe = new ShapelessRecipe(r.getResult());
            for(RecipeItem i : r.getIngredients())
            {
                recipe.addIngredient(i.getAmount(), i.getMaterial());
            }
            Bukkit.addRecipe(recipe);
        }
    }
}
