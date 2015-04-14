package com.minegusta.mgracesredone.races.skilltree.abilities.perks;

import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;

import java.util.List;

public class ForestFriend implements IAbility
{
    @Override
    public String getName() {
        return null;
    }

    @Override
    public AbilityType getType() {
        return null;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return null;
    }

    @Override
    public int getPrice(int level) {
        return 0;
    }

    @Override
    public List<RaceType> getRaces() {
        return null;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public String[] getDescription(int level) {
        return new String[0];
    }
}
