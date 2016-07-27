package com.minegusta.mgracesredone.listeners.racelisteners;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.Effect;
import org.bukkit.Material;
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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.concurrent.ConcurrentMap;

public class ElfListener implements Listener {

    @EventHandler
    public void onElfFruitEat(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        if (!WorldCheck.isEnabled(p.getWorld())) return;

        MGPlayer mgp = Races.getMGPlayer(p);

        if (ItemUtil.isFruit(e.getItem().getType()) && mgp.hasAbility(AbilityType.FRUITFANATIC)) {
            AbilityType.FRUITFANATIC.run(e);
        }
    }

    @EventHandler
    public void onElfShoot(EntityShootBowEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (Races.getMGPlayer(p).hasAbility(AbilityType.POINTYSHOOTY)) {
                AbilityType.POINTYSHOOTY.run(e);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBowDamage(EntityDamageByEntityEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        //Arrows do more damage.
        if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
            Player p = (Player) e.getDamager();
            if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE && Races.getMGPlayer(p).hasAbility(AbilityType.RANGER) && WGUtil.canFightEachother(p, e.getEntity()) && !e.isCancelled()) {
                AbilityType.RANGER.run(e);
            }
        }

        if (e.getEntity() instanceof Animals && e.getDamager() instanceof Player) {
            MGPlayer mgp = Races.getMGPlayer((Player) e.getDamager());

            if (mgp.hasAbility(AbilityType.FORESTFRIEND)) {
                AbilityType.FORESTFRIEND.run(e);
            }
        }

        //Animals saving the elf.
        if (e.getEntity() instanceof Player && e.getDamager() instanceof LivingEntity) {
            Player p = (Player) e.getEntity();
            MGPlayer mgp = Races.getMGPlayer(p);
            if (mgp.hasAbility(AbilityType.NATURALIST)) {
                AbilityType.NATURALIST.run(e);
            }
        }

        //Block ArrowNado damage towards the shooter.
        if (e.getDamager() instanceof Arrow && ((Arrow) e.getDamager()).getShooter() instanceof ElfShooter && e.getEntity() instanceof Player && e.getEntity().getUniqueId().toString().equalsIgnoreCase(((ElfShooter) ((Arrow) e.getDamager()).getShooter()).getUuid())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onElfInteract(PlayerInteractEvent e) {
        if (!WorldCheck.isEnabled(e.getPlayer().getWorld())) return;
        if (e.getHand() != EquipmentSlot.HAND) return;

        Player p = e.getPlayer();

        Material hand = p.getInventory().getItemInMainHand().getType();

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getInventory().getItemInMainHand().getType() == Material.AIR && e.getClickedBlock().getType() == Material.GRASS && Races.getMGPlayer(p).hasAbility(AbilityType.NATURALIST)) {
            AbilityType.NATURALIST.run(e);
            return;
        }

        if (hand == Material.BOW && e.getAction() == Action.LEFT_CLICK_AIR && Races.getMGPlayer(p).hasAbility(AbilityType.POINTYSHOOTY)) {
            SpecialArrows.nextArrow(p);
            return;
        }

        //Elves can shoot hearts pew pew
        if (hand == Material.RED_ROSE && e.getAction() == Action.RIGHT_CLICK_AIR && p.isSneaking() && Races.getRace(p) == RaceType.ELF) {
            Missile.createMissile(p.getLocation(), p.getLocation().getDirection().multiply(1.4), new Effect[]{Effect.HEART}, 30);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFireDamage(EntityDamageEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if (e.getEntity() instanceof Player && (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) && WGUtil.canGetDamage(e.getEntity())) {
            Player p = (Player) e.getEntity();

            //Elves have fire weakness
            if (Races.getRace(p) == RaceType.ELF) {
                e.setDamage(e.getDamage() + 3.0);
            }
            if (Races.getMGPlayer(p).hasAbility(AbilityType.FLAMERESISTANCE)) {
                AbilityType.FLAMERESISTANCE.run(e);
            }
        }
    }

    public static ConcurrentMap<String, LivingEntity> riders = Maps.newConcurrentMap();

    @EventHandler
    public void onDisMount(EntityDismountEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if (e.getEntity() instanceof Player) {
            if (riders.containsKey(e.getEntity().getUniqueId().toString())) {
                riders.remove(e.getEntity().getUniqueId().toString());
            }
        }
    }

    @EventHandler
    public void onElfRide(PlayerInteractEntityEvent e) {
        if (!WorldCheck.isEnabled(e.getPlayer().getWorld())) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        if (e.getRightClicked() instanceof LivingEntity && !(e.getRightClicked() instanceof Player || e.getRightClicked() instanceof Villager || e.getRightClicked() instanceof Horse)) {
            MGPlayer mgp = Races.getMGPlayer(e.getPlayer());

            if (!mgp.hasAbility(AbilityType.ANIMALRIDER)) return;

            if (e.getPlayer().getInventory().getItemInMainHand().getType() == null || e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
                AbilityType.ANIMALRIDER.run(e);
            } else {
                ChatUtil.sendString(e.getPlayer(), "Elves can only ride animals with an empty hand.");
            }
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;
        if (e.isCancelled()) return;

        EntityDamageEvent.DamageCause cause = e.getCause();
        if (cause == EntityDamageEvent.DamageCause.FALL) {
            if (e.getEntity() instanceof Player) {
                Player p = (Player) e.getEntity();
                MGPlayer mgp = Races.getMGPlayer(p);
                if (mgp.getAbilityLevel(AbilityType.NATURALIST) > 4) {
                    boolean run = true;
                    for (ItemStack i : p.getInventory().getArmorContents()) {
                        if (i != null && ItemUtil.isDiamondArmour(i.getType())) {
                            run = false;
                            break;
                        }
                    }
                    if (run) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if (e.getEntityType() == EntityType.ARROW && e.getEntity().getShooter() != null && e.getEntity().getShooter() instanceof Player) {
            Player p = (Player) e.getEntity().getShooter();
            if (Races.getMGPlayer(p).hasAbility(AbilityType.POINTYSHOOTY)) {
                AbilityType.POINTYSHOOTY.run(e);
            }
            if (p.isSneaking() && Races.getMGPlayer(p).hasAbility(AbilityType.ARROWRAIN)) {
                AbilityType.ARROWRAIN.run(e);
            }
        }

        if (e.getEntityType() == EntityType.EGG && e.getEntity().getShooter() != null && e.getEntity().getShooter() instanceof Player) {
            Player p = (Player) e.getEntity().getShooter();
            if (Races.getRace(p) == RaceType.ELF && Races.getMGPlayer(p).hasAbility(AbilityType.FORESTFRIEND)) {
                AbilityType.FORESTFRIEND.run(e);
            }
        }
    }
}
