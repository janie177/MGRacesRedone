package com.minegusta.mgracesredone.races.skilltree.abilities.perks.angel;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class Nyctophobia implements IAbility {
    @Override
    public void run(Event event) {
        // Do nothing
    }

    @Override
    public void run(Player player) {
        // Do nothing
    }

    @Override
    public String getName() {
        return "Nyctophobia";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.NYCTOPHOBIA;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.BONE;
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
        return Lists.newArrayList(RaceType.ANGEL);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"Your weakness is halved in dark areas."};
                break;
            case 2:
                desc = new String[]{"When in the nether or end, you wont get nausea."};
                break;
            case 3:
                desc = new String[]{"You are immune to poison."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}