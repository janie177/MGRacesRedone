package com.minegusta.mgracesredone.recipes;

public enum Recipe
{
    SHINYGEM("Shiny Gem", new ShinyGemRecipe());

    private String name;
    private IRecipe recipe;

    private Recipe(String name, IRecipe recipe)
    {
        this.name = name;
        this.recipe = recipe;
    }

    public String getRecipeName()
    {
        return name;
    }

    public IRecipe getRecipe()
    {
        return recipe;
    }
}
