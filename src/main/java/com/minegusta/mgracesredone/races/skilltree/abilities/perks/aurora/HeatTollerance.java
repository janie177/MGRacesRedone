package com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class HeatTollerance implements IAbility
{

    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Heat Tolerance";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.HEATTOLLERANCE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.DEAD_BUSH;
    }

    @Override
    public int getPrice(int level) {
        return 1;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.PASSIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 0;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.AURORA);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level)
        {
            case 1: desc = new String[]{"When in hot biomes, you will not gain nausea."};
                break;
            case 2: desc = new String[]{"When in hot biomes, you will only be half as slow."};
                break;
            case 3: desc = new String[]{"Weakness in hot areas is less."};
                break;
            case 4: desc = new String[]{"Digging will not be affected in hot biomes."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
