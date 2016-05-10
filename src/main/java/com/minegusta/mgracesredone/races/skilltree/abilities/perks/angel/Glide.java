package com.minegusta.mgracesredone.races.skilltree.abilities.perks.angel;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class Glide implements IAbility {
    @Override
    public void run(Event event) {

    }

    @Override
    public boolean run(Player player) {

        int level = Races.getMGPlayer(player).getAbilityLevel(getType());
        double speedY = -0.18;
        if (level > 1) {
            speedY = -0.12;
        }
        player.setVelocity(player.getLocation().getDirection().multiply(0.6).setY(speedY));
        return true;
    }

    @Override
    public String getName() {
        return "Glide";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.GLIDE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.FEATHER;
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
        return Lists.newArrayList(RaceType.ANGEL);
    }

    @Override
    public boolean canBind() {
        return true;
    }

    @Override
    public String getBindDescription() {
        return "Hold the bound item when falling to glide.";
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
                desc = new String[]{"When holding a bound item you will glide down instead of falling.", "Bind using /Bind."};
                break;
            case 2:
                desc = new String[]{"Gliding lasts twice as long."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;
        }
        return desc;
    }
}