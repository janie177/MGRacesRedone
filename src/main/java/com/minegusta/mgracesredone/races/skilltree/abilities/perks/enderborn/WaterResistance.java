package com.minegusta.mgracesredone.races.skilltree.abilities.perks.enderborn;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class WaterResistance implements IAbility {
    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Water Resistance";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.WATERRESISTANCE;
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
        return Lists.newArrayList(RaceType.ENDERBORN);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"Rain does 1 less damage."};
                break;
            case 2:
                desc = new String[]{"Solid water does 1 less damage."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
