package com.minegusta.mgracesredone.races.skilltree.abilities.perks.demon;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class HellSpawn implements IAbility{
    @Override
    public void run(Event event)
    {
        if(event instanceof EntityDamageEvent)
        {
            EntityDamageEvent e = (EntityDamageEvent) event;
            e.setCancelled(true);
        }

    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Hell Spawn";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.HELLSPAWN;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.FIREBALL;
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
        return Lists.newArrayList(RaceType.DEMON);
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
