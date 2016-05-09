package com.minegusta.mgracesredone.races.skilltree.abilities.perks.angel;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class Purge implements IAbility {
    @Override
    public void run(Event event) {

    }

    @Override
    public boolean run(Player player) {
        return true;
    }

    @Override
    public String getName() {
        return "Purge";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.PURGE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.IRON_SWORD;
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
    public boolean canBind() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"You will now do 2 extra damage to unholy mobs."};
                break;
            case 2:
                desc = new String[]{"Against unholy races, you have a 10% chance for a critical hit.", "This does +5% damage."};
                break;
            case 3:
                desc = new String[]{"You will now do 4 extra damage to unholy mobs."};
                break;
            case 4:
                desc = new String[]{"Against unholy races, you have a 10% chance for a critical hit.", "This does +10% damage."};
                break;
            case 5:
                desc = new String[]{"You do 6 extra damage against unholy mobs."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}