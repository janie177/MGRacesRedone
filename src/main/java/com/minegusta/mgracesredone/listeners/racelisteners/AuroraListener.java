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
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class AuroraListener implements Listener
{
    private static final List<Material> snowBlocks = Lists.newArrayList(Material.SNOW_BLOCK, Material.SNOW, Material.ICE, Material.PACKED_ICE);

    @EventHandler
    public void onDrownOrFall(EntityDamageEvent e)
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
                Material mat2 = p.getLocation().getBlock().getType();

                if(snowBlocks.contains(mat) || snowBlocks.contains(mat2))
                {
                    e.setDamage(0.0);
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onSnowFallDamage(EntityDamageEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

    }

    private static final double[] directions = {0.04, -0.04, 0.06, -0.06};
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
                        Missile.createMissile(l, x, 0.06, z, effects, 60);
                    }
                }

                for(Entity ent : ball.getNearbyEntities(8, 8, 8))
                {
                    if(ent instanceof LivingEntity)
                    {
                        if((ent instanceof Player && isAurora((Player) ent)))return;
                        PotionUtil.updatePotion((LivingEntity) ent, PotionEffectType.SLOW, 2, 6);
                        PotionUtil.updatePotion((LivingEntity) ent, PotionEffectType.WEAKNESS, 2, 3);
                        PotionUtil.updatePotion((LivingEntity) ent, PotionEffectType.BLINDNESS, 1, 5);
                    }
                }
            }
            else
            {
                p.sendMessage(ChatColor.AQUA + "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use that.");
            }
        }
    }

    @EventHandler
    public void onAuroraSneak(PlayerToggleSneakEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;

        if(!isAurora(e.getPlayer()))return;

        if(PlayerUtil.isInWater(e.getPlayer()))
        {
            Player p = e.getPlayer();
            p.setVelocity(p.getLocation().getDirection().normalize().multiply(2.0D));
        }
    }

    private static boolean isAurora(Player p)
    {
        return Races.getRace(p) == RaceType.AURORA;
    }
}
