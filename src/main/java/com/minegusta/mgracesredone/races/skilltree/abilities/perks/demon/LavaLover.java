package com.minegusta.mgracesredone.races.skilltree.abilities.perks.demon;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.listeners.general.FallDamageManager;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class LavaLover implements IAbility {
    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player) {
        MGPlayer mgp = Races.getMGPlayer(player);

        int level = mgp.getAbilityLevel(getType());
        double boost = 0.5 + 0.5 * level;

        player.setVelocity(player.getLocation().getDirection().normalize().multiply(boost));

        if (player.getVelocity().getY() > 1.2) {
            FallDamageManager.addToFallMap(player.getUniqueId().toString());
        }
    }

    @Override
    public String getName() {
        return "Lava Lover";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.LAVALOVER;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.IRON_BOOTS;
    }

    @Override
    public int getPrice(int level) {
        return level;
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
        return 3;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"When toggling crouch in lava, you will be launched forward."};
                break;
            case 2:
                desc = new String[]{"The launch boost is 50% faster."};
                break;
            case 3:
                desc = new String[]{"The launch boost is now 100% faster."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
