package com.minegusta.mgracesredone.races.skilltree.abilities.perks.demon;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.RandomUtil;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import java.util.List;

public class MeteorStorm implements IAbility {
    @Override
    public void run(Event event) {

    }

    @Override
    public boolean run(Player player) {
        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());
        Block target = player.getTargetBlock(Sets.newHashSet(Material.AIR), 40).getRelative(0, 30, 0);

        if (!WGUtil.canBuild(player, target.getLocation())) {
            player.sendMessage(ChatColor.RED + "You cannot use MeteorStorm here!");
            return false;
        }

        int interval = 16;
        int duration = 6;

        if (level > 2) duration = 10;
        if (level > 1) interval = 8;

        runStorm(target.getLocation(), duration, interval);
        return true;
    }

    private void runStorm(final Location location, final int duration, final int interval) {
        for (int i = 0; i <= duration * 20; i++) {
            if (i % interval == 0) {
                double offsetX = RandomUtil.randomNumber(16) - 8;
                double offsetZ = RandomUtil.randomNumber(16) - 8;
                final Location spawnLocation = new Location(location.getWorld(), location.getX() + offsetX, location.getY(), location.getZ() + offsetZ);

                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
                    Fireball ball = (Fireball) location.getWorld().spawnEntity(spawnLocation, EntityType.FIREBALL);
                    ball.setDirection(new Vector(0, -1, 0));
                    ball.setYield(4);
                }, i);
            }
        }
    }

    @Override
    public String getName() {
        return "Meteor Storm";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.METEORSTORM;
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
        return AbilityGroup.ACTIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 60;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.DEMON);
    }

    @Override
    public boolean canBind() {
        return true;
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
                desc = new String[]{"Call a Meteor storm on your location.", "Bind to an item using /Bind.", "Duration: 6 seconds."};
                break;
            case 2:
                desc = new String[]{"The amount of meteors in your storm is doubled."};
                break;
            case 3:
                desc = new String[]{"The storm now lasts 10 seconds."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
