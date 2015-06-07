package com.minegusta.mgracesredone.races.skilltree.abilities.perks.werewolf;


import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class GoldResistance implements IAbility {


    @Override
    public void run(Event event) {
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        Player p = (Player) e.getEntity();
        MGPlayer mgp = Races.getMGPlayer(p);

        int level = mgp.getAbilityLevel(getType());

        e.setDamage(e.getDamage() - level);

    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Gold Resistance";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.GOLDRESISTANCE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.GOLD_SWORD;
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
        return Lists.newArrayList(RaceType.WEREWOLF);
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
                desc = new String[]{"Gold does 1 less damage."};
                break;
            case 2:
                desc = new String[]{"Gold does 2 less damage."};
                break;
            case 3:
                desc = new String[]{"Gold does 3 less damage."};
                break;
            case 4:
                desc = new String[]{"Gold does 4 less damage."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;
        }
        return desc;
    }
}