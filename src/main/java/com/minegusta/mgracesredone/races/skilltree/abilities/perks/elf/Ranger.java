package com.minegusta.mgracesredone.races.skilltree.abilities.perks.elf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.RandomUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class Ranger implements IAbility {
    @Override
    public void run(Event event) {
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;

        Player p = (Player) e.getDamager();

        MGPlayer mgp = Races.getMGPlayer(p);
        int level = mgp.getAbilityLevel(getType());

        double damage = e.getDamage();

        //Boost for level 1;
        damage = damage + 1;

        //Crit calculation
        if (level > 4) {
            if (RandomUtil.chance(20)) damage = damage + (damage / 10);
        } else if (level > 3) {
            if (RandomUtil.chance(10)) damage = damage + (damage / 10);
        } else if (level > 1) {
            if (RandomUtil.chance(5)) damage = damage + (damage / 20);
        }

        e.setDamage(damage);
    }

    @Override
    public boolean run(Player player) {
        return false;
    }

    @Override
    public String getName() {
        return "Ranger";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.RANGER;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.BOW;
    }

    @Override
    public int getPrice(int level) {
        int cost = 0;
        switch (level) {
            case 1:
                cost = 1;
                break;
            case 2:
                cost = 1;
                break;
            case 3:
                cost = 2;
                break;
            case 4:
                cost = 1;
                break;
            case 5:
                cost = 2;
                break;
        }
        return cost;
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
        return Lists.newArrayList(RaceType.ELF);
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
                desc = new String[]{"Bows do 1 more damage."};
                break;
            case 2:
                desc = new String[]{"5% Chance for a critical hit, doing 5% extra damage."};
                break;
            case 3:
                desc = new String[]{"Bows do 2 more damage."};
                break;
            case 4:
                desc = new String[]{"10% Chance for a critical hit, doing 10% extra damage."};
                break;
            case 5:
                desc = new String[]{"20% Chance for a critical hit, doing 10% extra damage."};
                break;
            default:
                desc = new String[]{"This is an error.", "Report it to Jan!"};
                break;

        }
        return desc;
    }
}
