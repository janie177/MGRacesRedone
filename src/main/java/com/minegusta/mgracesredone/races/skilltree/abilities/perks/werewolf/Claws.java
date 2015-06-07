package com.minegusta.mgracesredone.races.skilltree.abilities.perks.werewolf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.WeatherUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class Claws implements IAbility {


    @Override
    public void run(Event event) {

        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;

        Player damager = (Player) e.getDamager();
        MGPlayer mgp = Races.getMGPlayer(damager);

        if (!WeatherUtil.isNight(damager.getWorld())) return;

        int level = mgp.getAbilityLevel(getType());

        int damage = 2 * level;

        e.setDamage(e.getDamage() + damage);
    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Claws";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.CLAWS;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.SKULL;
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
        return 5;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"At night, your bare claws are stronger.", "Claw damage is increased by 2."};
                break;
            case 2:
                desc = new String[]{"Claw damage is increased to 4."};
                break;
            case 3:
                desc = new String[]{"Claw damage is increased to 6."};
                break;
            case 4:
                desc = new String[]{"Claw damage is increased to 8."};
                break;
            case 5:
                desc = new String[]{"Claw damage is increased to 10."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;
        }
        return desc;
    }
}