package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.*;
import net.minegusta.mglib.utils.Title;
import net.minegusta.mglib.utils.TitleUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;

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

            MGPlayer mgp = Races.getMGPlayer((Player) e.getDamager());

            Player p = (Player) e.getDamager();
            if (!e.isCancelled() && mgp.getAbilityLevel(AbilityType.PREDATOR) > 1 && WGUtil.canFightEachother(p, e.getEntity())) {
                AbilityType.PREDATOR.run(e);
            }

            //Backstabbing
            if (!e.isCancelled() && mgp.getAbilityLevel(AbilityType.PREDATOR) > 4) {
                if (Math.abs(e.getDamager().getLocation().getYaw() - e.getEntity().getLocation().getYaw()) < 50) {
                    e.setDamage(e.getDamage() * 1.2);
                    Title title = TitleUtil.createTitle("", ChatColor.RED + "Backstab! " + ChatColor.DARK_PURPLE + "1.2x Damage Done", 5, 15, 5, true);
                    title.send(p);
                }
            }
        }

        if (e.getEntity() instanceof Player && Races.getMGPlayer((Player) e.getEntity()).hasAbility(AbilityType.ENDERSHIELD)) {
            AbilityType.ENDERSHIELD.run(e);
        }

        //Invisibility
        if (e.getEntity() instanceof Player) {
            if (ShadowInvisibility.contains(e.getEntity().getUniqueId().toString())) {
                ShadowInvisibility.remove(e.getEntity().getUniqueId().toString());
                ChatUtil.sendString(((Player) e.getEntity()), "You got hit and are no longer invisible!");
            }
        }
        if (e.getDamager() instanceof Player) {
            String uuid = e.getDamager().getUniqueId().toString();
            if (ShadowInvisibility.contains(uuid)) {
                ShadowInvisibility.remove(uuid);
                ChatUtil.sendString((Player) e.getDamager(), "You attacked and are no longer invisible!");
            }
        }

    }

    @EventHandler
    public void onPearlDrop(PlayerDropItemEvent e) {
        if (!WorldCheck.isEnabled(e.getPlayer().getWorld())) return;

        if (e.getItemDrop().getItemStack().getType() == Material.ENDER_PEARL && Races.getMGPlayer(e.getPlayer()).hasAbility(AbilityType.ENDERSHIELD)) {
            AbilityType.ENDERSHIELD.run(e);
        }
    }

    @EventHandler
    public void onPearlToggle(PlayerInteractEvent e) {
        if (!WorldCheck.isEnabled(e.getPlayer().getWorld())) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        Player p = e.getPlayer();
        MGPlayer mgp = Races.getMGPlayer(p);

        //Switching pearl mode
        if (p.getInventory().getItemInMainHand().getType() == Material.ENDER_PEARL && (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) && Races.getMGPlayer(p).hasAbility(AbilityType.PEARLPOWER)) {
            AbilityType.PEARLPOWER.run(p);
        }
    }

    @EventHandler
    public void onPearlThrow(ProjectileHitEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;
        if (e.getEntity() instanceof EnderPearl && e.getEntity().getShooter() instanceof Player && Races.getMGPlayer((Player) e.getEntity().getShooter()).hasAbility(AbilityType.PEARLPOWER)) {
            AbilityType.PEARLPOWER.run(e);
        }
    }

    @EventHandler
    public void onEnderMobTarget(EntityTargetLivingEntityEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;
        if (e.getTarget() instanceof Player && (e.getEntity() instanceof Enderman || e.getEntity() instanceof Endermite)) {
            Player p = (Player) e.getTarget();
            if (Races.getMGPlayer(p).getAbilityLevel(AbilityType.COLDBLOODED) > 2) e.setCancelled(true);
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
        }
    }

    @EventHandler
    public void onJoin(EntityDamageEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && e.getEntity() instanceof Player && Races.getMGPlayer((Player) e.getEntity()).getAbilityLevel(AbilityType.OTHERWORLDLY) > 4 && WeatherUtil.isEnd(e.getEntity().getLocation())) {
            e.setCancelled(true);
        }
    }

    //Listening to invisibility

    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
        Player joined = e.getPlayer();

        for (String s : ShadowInvisibility.values()) {
            Player p = Bukkit.getPlayer(UUID.fromString(s));
            joined.hidePlayer(p);
        }
    }


    @EventHandler
    public void onLogout(PlayerQuitEvent e) {
        String uuid = e.getPlayer().getUniqueId().toString();

        if (ShadowInvisibility.contains(uuid)) {
            ShadowInvisibility.remove(uuid);
        }
    }

    @EventHandler
    public void onEvent(PlayerChangedWorldEvent e) {
        if (!WorldCheck.isEnabled(e.getPlayer().getWorld())) return;

        Player changed = e.getPlayer();
        String uuid = changed.getUniqueId().toString();

        if (ShadowInvisibility.contains(uuid)) {
            ShadowInvisibility.remove(uuid);
        }
    }
}
