package com.minegusta.mgracesredone.races.skilltree.abilities.perks.dwarf;

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

public class ProjectileProtection implements IAbility
{

    @Override
    public void run(Event event)
    {
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
        return "Projectile Protection";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.PROJECTILEPROTECTION;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.ARROW;
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
        return Lists.newArrayList(RaceType.DWARF);
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
            case 1: desc = new String[]{"Projectiles do 1 less damage."};
                break;
            case 2: desc = new String[]{"Projectiles do 1 less damage."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
