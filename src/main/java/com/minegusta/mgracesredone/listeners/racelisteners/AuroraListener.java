package com.minegusta.mgracesredone.listeners.racelisteners;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora.TidalWave;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class AuroraListener implements Listener {
    private static final List<Material> snowBlocks = Lists.newArrayList(Material.SNOW_BLOCK, Material.SNOW, Material.ICE, Material.PACKED_ICE);

    @EventHandler
    public void onDrownOrFall(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
            if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;
            Player p = (Player) e.getEntity();
            if (Races.getMGPlayer(p).getAbilityLevel(AbilityType.Fish) > 0) {
                e.setCancelled(true);
            }
        } else if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player p = (Player) e.getEntity();
            if (Races.getMGPlayer(p).getAbilityLevel(AbilityType.GLACIOUS) > 2) {
                Material mat = p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType();
                Material mat2 = p.getLocation().getBlock().getType();

                if (snowBlocks.contains(mat) || snowBlocks.contains(mat2)) {
                    e.setDamage(0.0);
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onWaterFlow(BlockFromToEvent e) {
        if (!WorldCheck.isEnabled(e.getBlock().getWorld())) return;

        Material m = e.getBlock().getType();
        if (m == Material.WATER || m == Material.STATIONARY_WATER) {
            if (TidalWave.blockMap.containsKey(e.getBlock().getLocation())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onAuroraSnowball(ProjectileHitEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if (e.getEntity().getShooter() instanceof Player && e.getEntity() instanceof Snowball) {
            Snowball ball = (Snowball) e.getEntity();
            Player p = (Player) ball.getShooter();

            if (!Races.getMGPlayer(p).hasAbility(AbilityType.ICEBARRAGE)) return;

            AbilityType.ICEBARRAGE.run(e);
        }
        if (e.getEntity().getShooter() instanceof IceBarrageThrower) {
            // Acceptable use of Entity::getNearbyEntities according to Spigot
            for (Entity ent : e.getEntity().getNearbyEntities(2, 2, 2)) {
                if (ent instanceof LivingEntity && !(ent instanceof Player && Races.getRace((Player) ent) == RaceType.AURORA)) {
                    LivingEntity le = (LivingEntity) ent;
                    PotionUtil.updatePotion(le, PotionEffectType.SLOW, 5, 8);
                }
            }
            EffectUtil.playSound(e.getEntity().getLocation(), Sound.BLOCK_SNOW_BREAK);
            EffectUtil.playParticle(e.getEntity().getLocation(), Effect.SNOW_SHOVEL, 1, 1, 1, 14, 25);
        } else if (e.getEntity().getShooter() instanceof IceBarragePoisonThrower) {
            // Acceptable use of Entity::getNearbyEntities according to Spigot
            for (Entity ent : e.getEntity().getNearbyEntities(2, 2, 2)) {
                if (ent instanceof LivingEntity && !(ent instanceof Player && Races.getRace((Player) ent) == RaceType.AURORA)) {
                    LivingEntity le = (LivingEntity) ent;
                    PotionUtil.updatePotion(le, PotionEffectType.SLOW, 5, 8);
                    PotionUtil.updatePotion(le, PotionEffectType.POISON, 1, 3);
                }
            }
            EffectUtil.playSound(e.getEntity().getLocation(), Sound.BLOCK_SNOW_BREAK);
            EffectUtil.playParticle(e.getEntity().getLocation(), Effect.SNOW_SHOVEL, 1, 1, 1, 14, 25);
        }
    }

    @EventHandler
    public void onAuroraSneak(PlayerToggleSneakEvent e) {
        if (!WorldCheck.isEnabled(e.getPlayer().getWorld())) return;

        if (PlayerUtil.isInWater(e.getPlayer()) && Races.getMGPlayer(e.getPlayer()).hasAbility(AbilityType.AQUAMAN)) {
            AbilityType.AQUAMAN.run(e.getPlayer());
        }
    }
}
