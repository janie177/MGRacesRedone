package com.minegusta.mgracesredone.listeners.racelisteners;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.concurrent.ConcurrentMap;

public class ElfListener implements Listener
{

    @EventHandler
    public void onElfFruitEat(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        if (!WorldCheck.isEnabled(p.getWorld())) return;

        MGPlayer mgp = Races.getMGPlayer(p);

        if (mgp.isRace(RaceType.ELF) && ItemUtil.isFruit(e.getItem().getType()) && mgp.hasAbility(AbilityType.FRUITFANATIC)) {
            AbilityType.FRUITFANATIC.run(e);
        }
    }

    @EventHandler
    public void onElfShoot(EntityShootBowEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof Player)
        {
            Player p = (Player) e.getEntity();
            if(isElf(p) && Races.getMGPlayer(p).hasAbility(AbilityType.POINTYSHOOTY))
            {
                AbilityType.POINTYSHOOTY.run(e);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBowDamage(EntityDamageByEntityEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        //Arrows do more damage.
        if(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity)
        {
            Player p = (Player) e.getDamager();
            if(e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE  && isElf(p) && Races.getMGPlayer(p).hasAbility(AbilityType.RANGER) && WGUtil.canFightEachother(p, e.getEntity()) && !e.isCancelled())
            {
                AbilityType.RANGER.run(e);
            }
        }

        //Animals saving the elf.
        if(e.getEntity() instanceof Player && e.getDamager() instanceof LivingEntity)
        {
            Player p = (Player) e.getEntity();
            MGPlayer mgp = Races.getMGPlayer(p);
            if(isElf(p) && mgp.hasAbility(AbilityType.NATURALIST))
            {
                AbilityType.NATURALIST.run(e);
            }
        }
    }

    @EventHandler
    public void onElfInteract(PlayerInteractEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;

        if(!isElf(e.getPlayer()))return;

        Player p = e.getPlayer();

        Material hand = p.getItemInHand().getType();

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getItemInHand().getType() == Material.AIR && e.getClickedBlock().getType() == Material.GRASS && Races.getMGPlayer(p).hasAbility(AbilityType.NATURALIST))
        {
            AbilityType.NATURALIST.run(e);
            return;
        }

        if(hand == Material.BOW && e.getAction() == Action.LEFT_CLICK_AIR && Races.getMGPlayer(p).hasAbility(AbilityType.POINTYSHOOTY))
        {
            SpecialArrows.nextArrow(p);
            return;
        }

        if(hand == Material.RED_ROSE && e.getAction() == Action.RIGHT_CLICK_AIR && p.isSneaking())
        {
            Missile.createMissile(p.getLocation(), p.getLocation().getDirection().multiply(1.4), new Effect[]{Effect.HEART}, 30);
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFireDamage(EntityDamageEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof Player && (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) && isElf((Player) e.getEntity()) && WGUtil.canGetDamage(e.getEntity()))
        {
            Player p = (Player) e.getEntity();

            e.setDamage(e.getDamage() + 4.0);

            if(Races.getMGPlayer(p).hasAbility(AbilityType.FLAMERESISTANCE))
            {
                AbilityType.FLAMERESISTANCE.run(e);
            }
        }
    }

    public static ConcurrentMap<String, LivingEntity> riders = Maps.newConcurrentMap();

    @EventHandler
    public void onDisMount(EntityDismountEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof Player)
        {
            if(riders.containsKey(e.getEntity().getUniqueId().toString()))
            {
                riders.remove(e.getEntity().getUniqueId().toString());
            }
        }
    }

    @EventHandler
    public void onElfRide(PlayerInteractEntityEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()) || Races.getRace(e.getPlayer()) != RaceType.ELF)return;

        if(e.getRightClicked() instanceof LivingEntity && !(e.getRightClicked() instanceof Player || e.getRightClicked() instanceof Villager || e.getRightClicked() instanceof Horse))
        {
            MGPlayer mgp = Races.getMGPlayer(e.getPlayer());

            if(!mgp.hasAbility(AbilityType.ANIMALRIDER))return;

            if (e.getPlayer().getItemInHand().getType() == null || e.getPlayer().getItemInHand().getType() == Material.AIR) {
                ChatUtil.sendString(e.getPlayer(), "Elves can only ride animals with an empty hand.");
            } else
            {
                AbilityType.ANIMALRIDER.run(e);
            }
        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntityType() == EntityType.ARROW && e.getEntity().getShooter() != null && e.getEntity().getShooter() instanceof Player)
        {
            Player p = (Player) e.getEntity().getShooter();
            if(Races.getRace(p) == RaceType.ELF && Races.getMGPlayer(p).hasAbility(AbilityType.POINTYSHOOTY))
            {
                AbilityType.POINTYSHOOTY.run(e);
            }
        }
    }

    private static boolean isElf(Player p)
    {
        return Races.getRace(p) == RaceType.ELF;
    }

}
