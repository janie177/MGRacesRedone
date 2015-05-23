package com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class IceBarrage implements IAbility
{

    private static final double[] directions = {0.04, -0.04, 0.06, -0.06};
    private static final Effect[] effects = {Effect.SNOW_SHOVEL, Effect.WATERDRIP, Effect.SNOWBALL_BREAK};

    @Override
    public void run(Event event)
    {
        ProjectileHitEvent e = (ProjectileHitEvent) event;
        Player p = (Player) e.getEntity().getShooter();
        Snowball ball = (Snowball) e.getEntity();
        MGPlayer mgp = Races.getMGPlayer(p);
        int level = mgp.getAbilityLevel(getType());

        String name = "snowball";
        String uuid = p.getUniqueId().toString();
        final Location l = ball.getLocation();

        if(Cooldown.isCooledDown(name, uuid))
        {
            ChatUtil.sendString(p, ChatColor.DARK_AQUA + "You send a blizzard at your enemies!");
            Cooldown.newCoolDown(name, uuid, getCooldown(level));
            for(double x : directions)
            {
                for(double z : directions)
                {
                    Missile.createMissile(l, x, 0.06, z, effects, 60);
                }
            }

            if(level > 2)
            {
                boolean poison = level > 3;
                callRain(l, poison);
            }

            for(Entity ent : ball.getNearbyEntities(8, 8, 8))
            {
                if(ent instanceof LivingEntity)
                {
                    //Do not apply to Aurora.
                    if(ent instanceof Player && Races.getRace((Player) ent) == RaceType.AURORA)continue;

                    PotionUtil.updatePotion((LivingEntity) ent, PotionEffectType.SLOW, 1, 7);
                    PotionUtil.updatePotion((LivingEntity) ent, PotionEffectType.BLINDNESS, 1, 4);
                    if(level > 1)
                    {
                        PotionUtil.updatePotion((LivingEntity) ent, PotionEffectType.WEAKNESS, 1, 5);
                    }
                }
            }
        }
        else
        {
            ChatUtil.sendString(p, ChatColor.AQUA + "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use Ice Barrage.");
        }
    }

    private static void callRain(Location l, final boolean poison)
    {
        //The loop for the amount of seconds needed.
        for(int i = 0; i < 20 * 7; i++)
        {
            if(i % 5 == 0)
            {
                double xoffSet = RandomUtil.randomNumber(10) - 5;
                double zoffSet = RandomUtil.randomNumber(10) - 5;
                final Location spot = new Location(l.getWorld(), l.getX() + xoffSet, l.getY() + 20, l.getZ() + zoffSet);

                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run()
                    {

                        final Snowball rain = (Snowball) spot.getWorld().spawnEntity(spot, EntityType.SNOWBALL);
                        rain.setVelocity(new Vector(0, -1, 0));
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                            @Override
                            public void run()
                            {
                                if(!rain.isDead())
                                {
                                   for(Entity ent : rain.getNearbyEntities( 2, 2, 2))
                                    {
                                       if(ent instanceof LivingEntity && !(ent instanceof Player && Races.getRace((Player) ent) == RaceType.AURORA))
                                       {
                                           LivingEntity le = (LivingEntity) ent;
                                           PotionUtil.updatePotion(le, PotionEffectType.SLOW, 5, 6);
                                           if(poison)PotionUtil.updatePotion(le, PotionEffectType.POISON, 1, 3);
                                       }
                                    }
                                }
                            }
                        }, 20);
                    }
                }, i);
            }
        }
    }


    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Ice Barrage";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.ICEBARRAGE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.SNOW_BALL;
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
        return 16;
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
            case 1: desc = new String[]{"Throw a snowball to blind and slow enemies for a short time."};
                break;
            case 2: desc = new String[]{"Enemies will be weakened by your snowballs now."};
                break;
            case 3: desc = new String[]{"It will rain snowballs that will freeze enemies."};
                break;
            case 4: desc = new String[]{"The raining snowballs now poison enemies as well."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
