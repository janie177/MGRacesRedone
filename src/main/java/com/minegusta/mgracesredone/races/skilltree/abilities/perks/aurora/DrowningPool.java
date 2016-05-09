package com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.EntityUtil;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import java.util.List;

public class DrowningPool implements IAbility {

    @Override
    public void run(Event event) {

    }

    @Override
    public boolean run(Player player) {
        if (!WGUtil.canBuild(player) || !EntityUtil.isInWater(player)) {
            player.sendMessage(ChatColor.RED + "You cannot use Drowning Pool here!");
            return false;
        }

        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());
        Location l = player.getLocation();

        int radius = 8;
        boolean air = level > 2;
        int duration = 6;
        if (level > 1) duration = 10;
        if (level > 3) radius = 12;

        start(l, radius, duration, air);

        return true;
    }

    private void start(final Location l, final int radius, final int duration, final boolean air) {
        for (int i = 0; i < 20 * duration; i++) {
            if (i % 5 == 0) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
                    l.getWorld().getEntitiesByClass(LivingEntity.class).stream().filter(le ->
                            le.getLocation().distance(l) <= radius).filter(EntityUtil::isInWater).
                            filter(le -> !(le instanceof Player && Races.getRace((Player) le).equals(RaceType.AURORA))).
                            forEach(le -> {
                                le.setVelocity(new Vector(le.getVelocity().getX(), -0.12, le.getVelocity().getZ()));
                                if (le instanceof Player) {
                                    le.sendMessage(ChatColor.RED + "You are pulled underwater!");
                                }
                                if (air && le.getRemainingAir() > 3) {
                                    le.setRemainingAir(3);
                                }
                            });
                }, i);
            }
        }
    }

    @Override
    public String getName() {
        return "Drowning Pool";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.DROWNINGPOOL;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.DIAMOND_HELMET;
    }

    @Override
    public int getPrice(int level) {
        return 2;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.ACTIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 140;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.AURORA);
    }

    @Override
    public boolean canBind() {
        return true;
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
                desc = new String[]{"When in water, entities around you will be pulled down.", "Bind to an item using /Bind.", "Will last for 6 seconds.", "The radius is 8."};
                break;
            case 2:
                desc = new String[]{"The duration is increased to 10 seconds."};
                break;
            case 3:
                desc = new String[]{"Entities air is set to 3 when they are pulled down."};
                break;
            case 4:
                desc = new String[]{"The radius is 50% larger."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
