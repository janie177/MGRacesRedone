package com.minegusta.mgracesredone.races.skilltree.abilities.perks.demon;

import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class LavaLover implements IAbility {
    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player) {

    }

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
        String[] desc;

        switch (level)
        {
            case 1: desc = new String[]{"You gain a speed I and jump I boost permanently."};
                break;
            case 2: desc = new String[]{"You regenerate health in water."};
                break;
            case 3: desc = new String[]{"You regenerate health in the rain."};
                break;
            case 4: desc = new String[]{"When nearly dead, you absorb life from nearby animals."};
                break;
            case 5: desc = new String[]{"Right-clicking grass with your hands acts as bone meal."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
