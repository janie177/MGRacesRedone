package com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class AquaMan implements IAbility
{

    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player)
    {
        double speed = 1.25;
        int level = Races.getMGPlayer(player).getAbilityLevel(getType());
        if(level > 1) speed = 1.8;

        //Launch the player forward
        player.setVelocity(player.getLocation().getDirection().normalize().multiply(speed));
    }

    @Override
    public String getName() {
        return "AquaMan";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.AQUAMAN;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.RAW_FISH;
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
        return Lists.newArrayList(RaceType.AURORA);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level)
        {
            case 1: desc = new String[]{"Crouching in water will now launch you forward."};
                break;
            case 2: desc = new String[]{"You will be launched 50% faster."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
