package com.minegusta.mgracesredone.listeners.racelisteners;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class AuroraListener implements Listener
{
    private static final List<Material> snowBlocks = Lists.newArrayList(Material.SNOW_BLOCK, Material.SNOW, Material.ICE, Material.PACKED_ICE);

    @EventHandler
    public void onDrown(EntityDamageEvent e)
    {
        if(e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.DROWNING)
        {
            if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;
            Player p = (Player) e.getEntity();
            if(isAurora(p))
            {
                e.setCancelled(true);
            }
        }

        else if(e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL)
        {
            Player p = (Player) e.getEntity();
            if(isAurora(p))
            {
                Material mat = p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType();

                if(!snowBlocks.contains(mat))return;
                e.setDamage(0.0);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSnowFallDamage(EntityDamageEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

    }

    private static final double[] directions = {0.1, -0.1, 0.2, -0.2};
    private static final Effect[] effects = {Effect.SNOW_SHOVEL, Effect.WATERDRIP, Effect.SNOWBALL_BREAK};

    @EventHandler
    public void onAuroraSnowball(ProjectileHitEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity().getShooter() instanceof Player && e.getEntity() instanceof Snowball)
        {
            Snowball ball = (Snowball) e.getEntity();
            Player p = (Player) ball.getShooter();

            if(!isAurora(p))return;

            String name = "snowball";
            String uuid = p.getUniqueId().toString();
            final Location l = ball.getLocation();

            if(Cooldown.isCooledDown(name, uuid))
            {
                p.sendMessage(ChatColor.DARK_AQUA + "You send a blizzard at your enemies!");
                Cooldown.newCoolDown(name, uuid, 15);
                for(double x : directions)
                {
                    for(double z : directions)
                    {
                        Missile.createMissile(l, x, 0.2, z, effects, 500);
                    }
                }

                for(Entity ent : ball.getNearbyEntities(8, 8, 8))
                {
                    if(ent instanceof LivingEntity)
                    {
                        if((ent instanceof Player && isAurora((Player) ent)))return;
                        PotionUtil.updatePotion((LivingEntity) ent, PotionEffectType.SLOW, 2, 4);
                        PotionUtil.updatePotion((LivingEntity) ent, PotionEffectType.WEAKNESS, 2, 2);
                        PotionUtil.updatePotion((LivingEntity) ent, PotionEffectType.BLINDNESS, 1, 2);
                    }
                }
            }
            else
            {
                p.sendMessage(ChatColor.AQUA + "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use that.");
            }
        }
    }

    private static boolean isAurora(Player p)
    {
        return Races.getRace(p) == RaceType.AURORA;
    }
}
