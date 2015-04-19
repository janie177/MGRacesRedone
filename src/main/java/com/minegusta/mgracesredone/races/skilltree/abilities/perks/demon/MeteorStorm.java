package com.minegusta.mgracesredone.races.skilltree.abilities.perks.demon;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.Race;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.Cooldown;
import com.minegusta.mgracesredone.util.RandomUtil;
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
    public void run(Event event)
    {

    }

    @Override
    public void run(Player player)
    {
        String name = "mstorm";
        String id = player.getUniqueId().toString();
        int cooldownTime = 60;

        if(Cooldown.isCooledDown(name, id))
        {
            Cooldown.newCoolDown(name, id, cooldownTime);
            Block target = player.getTargetBlock(Sets.newHashSet(Material.AIR), 40).getRelative(0, 30, 0);

            MGPlayer mgp = Races.getMGPlayer(player);
            int level = mgp.getAbilityLevel(getType());

            ChatUtil.sendString(player, "You call a Meteor Storm on your enemies!");

            int interval = 16;
            int duration = 6;

            if(level > 2) duration = 10;
            if(level > 1) interval = 8;

            runStorm(target.getLocation(), duration, interval);

        }
        else
        {
            ChatUtil.sendString(player, ChatColor.RED + "You need to wait another " + Cooldown.getRemaining(name, id) + " seconds to use Meteor Storm.");
        }
    }

    private void runStorm(final Location location,final int duration,final int interval)
    {
        for(int i = 0; i <= duration * 20; i++)
        {
            if(i % interval == 0)
            {
                double offsetX = RandomUtil.randomNumber(16) - 8;
                double offsetZ = RandomUtil.randomNumber(16) - 8;
                final Location spawnLocation = new Location(location.getWorld(), location.getX() + offsetX, location.getY(), location.getZ() + offsetZ);

                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Fireball ball = (Fireball) location.getWorld().spawnEntity(spawnLocation, EntityType.FIREBALL);
                        ball.setDirection(new Vector(0, -1, 0));
                        ball.setYield(3);
                    }
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
        return level;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.ACTIVE;
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

        switch (level)
        {
            case 1: desc = new String[]{"Call a Meteor storm on your location.", "Duration: 6 seconds."};
                break;
            case 2: desc = new String[]{"The amount of meteors in your storm is doubled."};
                break;
            case 3: desc = new String[]{"The storm now lasts 10 seconds."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
