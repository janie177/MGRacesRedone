package com.minegusta.mgracesredone.util;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class HolyRain
{
    private int duration;
    private World w;
    private Location location;
    private static final List<EntityType> unholy = Lists.newArrayList(EntityType.SKELETON, EntityType.ZOMBIE, EntityType.WITCH, EntityType.BLAZE, EntityType.GHAST, EntityType.ENDERMAN, EntityType.PIG_ZOMBIE, EntityType.CAVE_SPIDER, EntityType.SPIDER, EntityType.CREEPER, EntityType.ENDERMITE, EntityType.GUARDIAN, EntityType.WITHER);
    private static final List<RaceType> unholyRaces = Lists.newArrayList(RaceType.DEMON, RaceType.WEREWOLF, RaceType.ENDERBORN);

    public HolyRain(int duration, Location l)
    {
        this.duration = duration * 4;
        this.location = l;
        this.w = location.getWorld();

        start();
    }

    public void start()
    {

        for(int i = 0; i < duration; i++)
        {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                @Override
                public void run()
                {
                    w.spigot().playEffect(location, Effect.WATERDRIP, 1, 1, 8, 0, 8, 1, 25, 20);
                }
            }, 5 * i);

            if(i % 4 == 0)
            {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run()
                    {
                        runRain();
                    }
                }, 20 * (i/4));
            }
        }
    }

    private void runRain()
    {
        Entity dummy = w.spawnEntity(location, EntityType.SNOWBALL);
        for(Entity ent : dummy.getNearbyEntities(8, 15, 8))
        {
            if(!(ent instanceof LivingEntity))continue;

            LivingEntity le = (LivingEntity) ent;

            if(ent instanceof Player)
            {
                Player p = (Player) ent;
                if(unholyRaces.contains(Races.getRace(p)))
                {
                    damage(p);
                }
                else
                {
                    heal(p);
                }
            }
            else
            {
                if(unholy.contains(ent.getType()))
                {
                    damage(le);
                }
                else
                {
                    heal(le);
                }
            }
        }
        dummy.remove();
    }

    private void heal(LivingEntity ent)
    {
        PotionUtil.updatePotion(ent, PotionEffectType.REGENERATION, 0, 5);
        PotionUtil.updatePotion(ent, PotionEffectType.SPEED, 0, 5);
        PotionUtil.updatePotion(ent, PotionEffectType.FIRE_RESISTANCE, 0, 5);
    }

    private void damage(LivingEntity ent)
    {

        PotionUtil.updatePotion(ent, PotionEffectType.HUNGER, 0, 5);
        PotionUtil.updatePotion(ent, PotionEffectType.SLOW, 0, 5);
        PotionUtil.updatePotion(ent, PotionEffectType.POISON, 0, 5);
    }
}
