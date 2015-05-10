package com.minegusta.mgracesredone.races.skilltree.abilities.perks.angel;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class Justice implements IAbility {
    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Justice";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.JUSTICE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.DIAMOND_SWORD;
    }

    @Override
    public int getPrice(int level) {
        return 1;
    }

    @Override
    public AbilityGroup getGroup()
    {
        return AbilityGroup.ACTIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 90;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.ANGEL);
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
                desc = new String[]{"To activate justice, .", "You will be launched into the air."};
                break;
            case 2:
                desc = new String[]{"When activating justice, you push back enemies."};
                break;
            case 3:
                desc = new String[]{"Justice launches you 50% faster."};
                break;
            case 4:
                desc = new String[]{"An explosion is caused when activating Justice."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}