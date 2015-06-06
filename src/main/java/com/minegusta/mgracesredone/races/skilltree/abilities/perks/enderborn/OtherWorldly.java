package com.minegusta.mgracesredone.races.skilltree.abilities.perks.enderborn;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class OtherWorldly implements IAbility {
    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Otherworldly";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.OTHERWORLDLY;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.ENDER_STONE;
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
        return 5;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"Inside the end, you gain a speed boost."};
                break;
            case 2:
                desc = new String[]{"When in the end, you have a strength 1 boost."};
                break;
            case 3:
                desc = new String[]{"You obtain a regeneration boost in the end."};
                break;
            case 4:
                desc = new String[]{"You will gain a jump boost in end biomes."};
                break;
            case 5:
                desc = new String[]{"In the end, it doesn't even matter.", "You will also obtain a defence boost."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
