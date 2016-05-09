package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.PlayerUtil;
import com.minegusta.mgracesredone.util.WeatherUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class DemonListener implements Listener {
    @EventHandler
    public void onDemonDamage(EntityDamageEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            //Demon falls in hell
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL && WeatherUtil.isHell(p.getLocation())) {
                if (Races.getMGPlayer(p).hasAbility(AbilityType.HELLSPAWN)) {
                    AbilityType.HELLSPAWN.run(e);
                }

            }

            //Demon takes fire/lava/firetick damage
            else if (e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                if (Races.getMGPlayer(p).hasAbility(AbilityType.FIREPROOF)) {
                    AbilityType.FIREPROOF.run(e);
                }
            }

        }
    }

    @EventHandler
    public void onDemonCombust(EntityCombustEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if (e.getEntity() instanceof Player && (Races.getMGPlayer((Player) e.getEntity()).getAbilityLevel(AbilityType.FIREPROOF) > 1)) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onDemonMobtarget(EntityTargetLivingEntityEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;
        if (e.getTarget() instanceof Player) {
            Player p = (Player) e.getTarget();

            if (Races.getMGPlayer(p).hasAbility(AbilityType.HELLISHTRUCE)) {
                AbilityType.HELLISHTRUCE.run(e);
            }
        }
    }

    @EventHandler
    public void onDemonSneak(PlayerToggleSneakEvent e) {
        if (!WorldCheck.isEnabled(e.getPlayer().getWorld())) return;

        Player p = e.getPlayer();

        if (PlayerUtil.isInLava(p) && Races.getMGPlayer(p).hasAbility(AbilityType.LAVALOVER)) {
            AbilityType.LAVALOVER.run(p);
        }
    }

    @EventHandler
    public void onDemonNearDeath(EntityDamageByEntityEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        //When demon hits something
        if (e.getEntity() instanceof LivingEntity && e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();

            if (!e.isCancelled() && Races.getMGPlayer(p).hasAbility(AbilityType.HELLISHTRUCE)) {
                AbilityType.HELLISHTRUCE.run(e);
            }
        }

        //Spawn hell minions.
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if (!(e.getDamager() instanceof LivingEntity) || !(p.getHealth() < 8)) return;

            if (Races.getMGPlayer(p).hasAbility(AbilityType.MINIONMASTER)) {
                AbilityType.MINIONMASTER.run(e);
            }

        }
    }
}
