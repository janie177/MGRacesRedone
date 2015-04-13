package com.minegusta.mgracesredone.races.skilltree.abilities;

import com.minegusta.mgracesredone.races.RaceType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IAbility
{
    /**
     * Apply the boosts for this ability. This can vary alot per perk. Also make use of levels: Exponential growth or switch/case/if/else.
     * @param level
     */
    public void run(int level);

    /**
     * Get the string for this perk. This is used in the perk shop.
     * @return
     */
    public String getName();

    /**
     * Get the AbilityType representing this perk.
     * @return
     */
    public AbilityType getType();

    /**
     * Get the ID for this ability. This is currently never used but may prove useful in the future.
     * @return
     */
    public int getID();

    /**
     * Get the item used to display this perk in the perk shop.
     * @return
     */
    public ItemStack getDisplayItem();

    /**
     * Get the price for this perk at a specific level. Make use of switch/case or just return 1.
     * @param level
     * @return
     */
    public int getPrice(int level);

    /**
     * Get all races this perk applies to. Some races will have shared perks.
     * @return
     */
    public List<RaceType> getRaces();

    /**
     * A method to retrieve the max level of this perk.
     * @return The maxium level possible. May NEVER be over 9.
     */
    public int getMaxLevel();

    /**
     * A method for getting the description per level. Use a switch/case in here to achieve this.
     * @param level The level aimed at. Make sure the level is lower than the specified level in this class.
     * @return The strings that for the description for this level of the item.
     */
    public String[] getDescription(int level);
}
