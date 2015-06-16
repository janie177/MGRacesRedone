package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.enderborn.PearlPower;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.*;

import java.util.UUID;

public class EnderBornListener implements Listener {
    @EventHandler
    public void onEnderBornMeatEat(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        if (!WorldCheck.isEnabled(p.getWorld())) return;
        MGPlayer mgp = Races.getMGPlayer(p);

        if (ItemUtil.isRawMeat(e.getItem().getType()) && mgp.hasAbility(AbilityType.PREDATOR)) {
            AbilityType.PREDATOR.run(p);
        }
    }

    //Bleeding with the Predator perk.

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCombat(EntityDamageByEntityEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        //Bleeding
        if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
            Player p = (Player) e.getDamager();
            if (!e.isCancelled() && Races.getMGPlayer(p).getAbilityLevel(AbilityType.PREDATOR) > 1 && WGUtil.canFightEachother(p, e.getEntity())) {
                AbilityType.PREDATOR.run(e);
                Bukkit.getLogger().info("Predator damage Ran.");
            }
        }

        if (e.getEntity() instanceof Player && Races.getMGPlayer((Player) e.getEntity()).hasAbility(AbilityType.ENDERSHIELD)) {
            AbilityType.ENDERSHIELD.run(e);
            Bukkit.getLogger().info("Endershield damage reduction Ran.");
        }


        //Invisibility
        if (e.getEntity() instanceof Player) {
            if (ShadowInvisibility.contains(e.getEntity().getUniqueId().toString())) {
                ShadowInvisibility.remove(e.getEntity().getUniqueId().toString());
                ChatUtil.sendString(((Player) e.getEntity()), "You got hit and are no longer invisible!");
                Bukkit.getLogger().info("Invisibility ended by hit.");
            }
        }
        if (e.getDamager() instanceof Player) {
            String uuid = e.getDamager().getUniqueId().toString();
            if (ShadowInvisibility.contains(uuid)) {
                ShadowInvisibility.remove(uuid);
                ChatUtil.sendString((Player) e.getDamager(), "You attacked and are no longer invisible!");
                Bukkit.getLogger().info("Invisibility ended by hitting.");
            }
        }
    }

    @EventHandler
    public void onPearlDrop(PlayerDropItemEvent e) {
        if (!WorldCheck.isEnabled(e.getPlayer().getWorld())) return;

        if (e.getItemDrop().getItemStack().getType() == Material.ENDER_PEARL && Races.getMGPlayer(e.getPlayer()).hasAbility(AbilityType.ENDERSHIELD)) {
            AbilityType.ENDERSHIELD.run(e);
            Bukkit.getLogger().info("Endershield charge added.");
        }
    }

    @EventHandler
    public void onPearlToggle(PlayerInteractEvent e) {
        if (!WorldCheck.isEnabled(e.getPlayer().getWorld())) return;
        Player p = e.getPlayer();
        MGPlayer mgp = Races.getMGPlayer(p);

        //Switching pearl mode
        if (p.getItemInHand().getType() == Material.ENDER_PEARL && (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) && Races.getMGPlayer(p).hasAbility(AbilityType.PEARLPOWER)) {
            AbilityType.PEARLPOWER.run(p);
            Bukkit.getLogger().info("Pearlpower interact ran.");
        }
        //Activate EndRift
        if (p.getItemInHand().getType() == Material.STICK && mgp.hasAbility(AbilityType.ENDRIFT)) {
            AbilityType.ENDRIFT.run(e);
            Bukkit.getLogger().info("Endrift activated.");
        }
        //Telekinesis.
        else if (p.getItemInHand().getType() == Material.BLAZE_ROD && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) && mgp.hasAbility(AbilityType.TELEKINESIS)) {
            AbilityType.TELEKINESIS.run(p);
            Bukkit.getLogger().info("Telekinesis Ran.");
        }
        //Activate shadow
        else if (e.hasBlock() && e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getY() < e.getPlayer().getLocation().getY() && e.getClickedBlock().getLocation().distance(e.getPlayer().getLocation()) < 2 && mgp.hasAbility(AbilityType.SHADOW)) {
            AbilityType.SHADOW.run(p);
            Bukkit.getLogger().info("Shadow interact Ran.");
        }
    }

    @EventHandler
    public void onPearlThrow(ProjectileHitEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;
        if (e.getEntity() instanceof EnderPearl && e.getEntity().getShooter() instanceof Player && Races.getMGPlayer((Player) e.getEntity().getShooter()).hasAbility(AbilityType.PEARLPOWER)) {
            AbilityType.PEARLPOWER.run(e);
            Bukkit.getLogger().info("Pearlpower projectile impact Ran.");
        }
    }

    @EventHandler
    public void onEnderMobTarget(EntityTargetLivingEntityEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;
        if (e.getTarget() instanceof Player && (e.getEntity() instanceof Enderman || e.getEntity() instanceof Endermite)) {
            Player p = (Player) e.getTarget();
            if (Races.getMGPlayer(p).getAbilityLevel(AbilityType.COLDBLOODED) < 3) return;
            Bukkit.getLogger().info("Coolblooded ender target cancelled.");
            e.setCancelled(true);
        }

    }

    //Teleporting players with the required pearlpower level will not take damage.
    @EventHandler
    public void onBlockTeleport(PlayerTeleportEvent e) {
        if (!WorldCheck.isEnabled(e.getPlayer().getWorld())) return;
        if (!(e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) return;

        Player p = e.getPlayer();

        if (Races.getMGPlayer(p).getAbilityLevel(AbilityType.PEARLPOWER) > 1) {
            e.setCancelled(true);
            if (PearlPower.getFromMap(p) == PearlPower.PearlAbility.NORMAL) {
                p.teleport(e.getTo());
            }
            Bukkit.getLogger().info("Enderpearl teleport ran.");
        }
    }

    //Listening to invisibility

    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
        Player joined = e.getPlayer();

        for (String s : ShadowInvisibility.values()) {
            Player p = Bukkit.getPlayer(UUID.fromString(s));
            joined.hidePlayer(p);
            Bukkit.getLogger().info("Hiding players for invisibility.");
        }
    }


    @EventHandler
    public void onLogout(PlayerQuitEvent e) {
        String uuid = e.getPlayer().getUniqueId().toString();

        if (ShadowInvisibility.contains(uuid)) {
            ShadowInvisibility.remove(uuid);
            Bukkit.getLogger().info("Removed player from invisibility on logout.");
        }
    }

    @EventHandler
    public void onEvent(PlayerChangedWorldEvent e) {
        if (!WorldCheck.isEnabled(e.getPlayer().getWorld())) return;

        Player changed = e.getPlayer();
        String uuid = changed.getUniqueId().toString();

        if (ShadowInvisibility.contains(uuid)) {
            ShadowInvisibility.remove(uuid);
            Bukkit.getLogger().info("Invisibility world change.");
        }
    }
}
