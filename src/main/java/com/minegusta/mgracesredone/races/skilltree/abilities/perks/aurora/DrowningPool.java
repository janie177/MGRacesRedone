package com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.Cooldown;
import com.minegusta.mgracesredone.util.EntityUtil;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import java.util.List;

public class DrowningPool implements IAbility
{

    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player)
    {
        if(!WGUtil.canBuild(player))
        {
            ChatUtil.sendString(player, "You cannot use Drowning Pool here!");
        }

        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());
        Location l = player.getLocation();
        String uuid = player.getUniqueId().toString();
        String cooldownName = "drown";

        if(Cooldown.isCooledDown(cooldownName, uuid))
        {
            ChatUtil.sendString(player, "You use drowning pool on your location!");
            Cooldown.newCoolDown(cooldownName, uuid, getCooldown(level));
            int radius = 8;
            boolean air = level > 2;
            int duration = 10;
            if(level > 1)duration = 16;
            if(level > 3)radius = 12;

            start(l, radius, duration, air);
        }
        else
        {
            ChatUtil.sendString(player, "You need to wait another " + Cooldown.getRemaining(cooldownName, uuid) + " seconds to use Drowning Pool.");
        }
    }

    private void start(final Location l, final int radius,final int duration,final boolean air)
    {
        for(int i = 0; i < 20 * duration; i++)
        {
            if(i % 5 == 0)
            {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run()
                    {
                        Entity dummy = l.getWorld().spawnEntity(l, EntityType.SNOWBALL);

                        for(Entity ent : dummy.getNearbyEntities(radius, radius, radius))
                        {
                            if(ent instanceof LivingEntity && EntityUtil.isInWater(ent) && !(ent instanceof Player && Races.getRace((Player) ent) == RaceType.AURORA))
                            {
                                ent.setVelocity(new Vector(ent.getVelocity().getX(), -0.5, ent.getVelocity().getZ()));
                                if(ent instanceof Player)
                                {
                                    ((Player)ent).sendMessage(ChatColor.RED + "You are pulled underwater!");
                                }
                                if(air && ((LivingEntity)ent).getRemainingAir() > 3)
                                {
                                    ((LivingEntity)ent).setRemainingAir(3);
                                }
                            }
                        }
                        dummy.remove();
                    }
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
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level)
        {
            case 1: desc = new String[]{"When in water, entities around you will be pulled down.", "Activate by right clicking with a sword in water.", "Will last for 8 seconds.", "The radius is 8."};
                break;
            case 2: desc = new String[]{"The duration is increased to 16 seconds."};
                break;
            case 3: desc = new String[]{"Entities air is set to 3 when they are pulled down."};
                break;
            case 4: desc = new String[]{"The radius is 50% larger."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
