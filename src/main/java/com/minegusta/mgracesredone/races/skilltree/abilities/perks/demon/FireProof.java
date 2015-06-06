package com.minegusta.mgracesredone.races.skilltree.abilities.perks.demon;

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
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class FireProof implements IAbility {
    @Override
    public void run(Event event) {
        EntityDamageEvent e = (EntityDamageEvent) event;
        Player p = (Player) e.getEntity();

        MGPlayer mgp = Races.getMGPlayer(p);

        if (mgp.getAbilityLevel(getType()) > 1 || RandomUtil.fiftyfifty()) {
            e.setDamage(0);
            e.getEntity().setFireTicks(0);
            e.setCancelled(true);
        }
    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Fireproof";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.FIREPROOF;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.LAVA_BUCKET;
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
        return Lists.newArrayList(RaceType.DEMON);
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
                desc = new String[]{"There's a 50% chance you take no fire and lava damage."};
                break;
            case 2:
                desc = new String[]{"You never take fire or lava damage."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
