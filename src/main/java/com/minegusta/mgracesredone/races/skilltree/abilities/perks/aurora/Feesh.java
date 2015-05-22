package com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora;


import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class Feesh implements IAbility
{

    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Feesh";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.FEESH;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.WATER_BUCKET;
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
        return 5;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level)
        {
            case 1: desc = new String[]{"In the nether, you will no longer take fall damage."};
                break;
            case 2: desc = new String[]{"When standing on obsidian, you will gain a defence boost."};
                break;
            case 3: desc = new String[]{"In the nether, you will gain a strength boost."};
                break;
            case 4: desc = new String[]{"In the nether, you will gain a massive speed boost."};
                break;
            case 5: desc = new String[]{"In the nether, you will gain a massive jump and defence boost."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
