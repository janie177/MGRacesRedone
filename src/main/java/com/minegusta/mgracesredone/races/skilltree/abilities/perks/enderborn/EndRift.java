package com.minegusta.mgracesredone.races.skilltree.abilities.perks.enderborn;


import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class EndRift implements IAbility
{
    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "End Rift";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.ENDRIFT;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.ENDER_PORTAL_FRAME;
    }

    @Override
    public int getPrice(int level) {
        if(level == 3)return 3;
        return 2;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.ACTIVE;
    }

    @Override
    public int getCooldown(int level)
    {
        if(level > 2) return 45;
        return 60;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.ENDERBORN);
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
                desc = new String[]{"Open two portals that teleport entities to eachother.", "Your portal will transport players.", "Activate by ", "Will stay open for 5 seconds."};
                break;
            case 2:
                desc = new String[]{"Your portal will transport mobs and itemstacks.", "The portals stay open for 9 seconds."};
                break;
            case 3:
                desc = new String[]{"The cooldown is reduced to 45 seconds.", "The portals stay open for 15 seconds."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
