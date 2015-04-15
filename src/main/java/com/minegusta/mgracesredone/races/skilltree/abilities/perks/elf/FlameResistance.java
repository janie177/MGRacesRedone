package com.minegusta.mgracesredone.races.skilltree.abilities.perks.elf;


import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class FlameResistance implements IAbility {
    @Override
    public void run(Event event) {

        EntityDamageEvent e = (EntityDamageEvent) event;
        Player p = (Player) e.getEntity();
        MGPlayer mgp = Races.getMGPlayer(p);

        e.setDamage(e.getDamage() - mgp.getAbilityLevel(getType()));



    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Flame Resistance";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.FLAMERESISTANCE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.FLINT_AND_STEEL;
    }

    @Override
    public int getPrice(int level) {

        int cost = 1;
        switch (level)
        {
            case 1: cost = 1;
                break;
            case 2: cost = 2;
                break;
            case 3: cost = 2;
                break;
        }

        return cost;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.ELF);
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
                desc = new String[]{"You take 1 damage less from fire."};
                break;
            case 2:
                desc = new String[]{"You take 2 damage less from fire."};
                break;
            case 3:
                desc = new String[]{"You take 3 damage less from fire."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}