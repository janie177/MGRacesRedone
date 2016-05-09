package com.minegusta.mgracesredone.races.skilltree.abilities.perks.werewolf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class Luna implements IAbility {


    @Override
    public void run(Event event) {

    }

    @Override
    public boolean run(Player player) {
        return false;
    }

    @Override
    public String getName() {
        return "Luna";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.LUNA;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.SPONGE;
    }

    @Override
    public int getPrice(int level) {
        return 2;
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
        return Lists.newArrayList(RaceType.WEREWOLF);
    }

    @Override
    public boolean canBind() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"You get a strong speed boost during full moons."};
                break;
            case 2:
                desc = new String[]{"You get a strong jump boost during full moons."};
                break;
            case 3:
                desc = new String[]{"You get a strong strength boost during full moons."};
                break;
            case 4:
                desc = new String[]{"You regenerate health during full moons."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;
        }
        return desc;
    }
}