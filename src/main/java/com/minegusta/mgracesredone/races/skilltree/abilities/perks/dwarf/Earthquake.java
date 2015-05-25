package com.minegusta.mgracesredone.races.skilltree.abilities.perks.dwarf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class Earthquake implements IAbility
{

    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Earthquake";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.EARTQUAKE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.NETHER_STAR;
    }

    @Override
    public int getPrice(int level) {
        return 2;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.ACTIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 80;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.DWARF);
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
            case 1: desc = new String[]{"Cause an earthquake, unbalancing entities around you.", "Activate by right clicking a pickaxe.", "Lasts 6 seconds."};
                break;
            case 2: desc = new String[]{"The floor around enemies will distort."};
                break;
            case 3: desc = new String[]{"The duration is increased to 10 seconds."};
                break;
            case 4: desc = new String[]{"The disorienting effect is twice as strong."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
