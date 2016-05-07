package com.minegusta.mgracesredone.listeners.racelisteners;


import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.ItemUtil;
import com.minegusta.mgracesredone.util.WGUtil;
import com.minegusta.mgracesredone.util.WeatherUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;

public class WereWolfListener implements Listener {
    @EventHandler
    public void onWolfInteract(PlayerInteractEntityEvent e) {
        if (!WorldCheck.isEnabled(e.getPlayer().getWorld())) return;
        if (e.getHand() != EquipmentSlot.HAND) return;
        Player p = e.getPlayer();
        MGPlayer mgp = Races.getMGPlayer(p);

        if (mgp.hasAbility(AbilityType.CANIS) && !e.isCancelled()) {
            if (e.getRightClicked() instanceof Wolf) {
                AbilityType.CANIS.run(e);
            }
        }
    }

    @EventHandler
    public void onWerewolfConsume(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        if (!WorldCheck.isEnabled(p.getWorld())) return;
        MGPlayer mgp = Races.getMGPlayer(p);

        if (ItemUtil.isRawMeat(e.getItem().getType()) && mgp.hasAbility(AbilityType.CARNIVORE)) {
            AbilityType.CARNIVORE.run(p);
        }
    }


    @EventHandler
    public void onWerewolfDamage(EntityDamageByEntityEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player player = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();

            if (!WGUtil.canFightEachother(player, damager)) return;

            if (isWereWolf(player)) {
                if (ItemUtil.isGoldTool(damager.getInventory().getItemInMainHand().getType())) {
                    e.setDamage(e.getDamage() + 10.0);
                    if (Races.getMGPlayer(player).hasAbility(AbilityType.GOLDRESISTANCE)) {
                        AbilityType.GOLDRESISTANCE.run(e);
                    }
                    return;
                }
            }
        }
        if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity && isWereWolf((Player) e.getDamager()) && WGUtil.canFightEachother(e.getDamager(), e.getEntity())) {
            Player damager = (Player) e.getDamager();
            MGPlayer mgp = Races.getMGPlayer(damager);

            if (!WeatherUtil.isNight(damager.getWorld())) return;

            if (damager.getInventory().getItemInMainHand().getType() == null || damager.getInventory().getItemInMainHand().getType() == Material.AIR) {
                if (mgp.hasAbility(AbilityType.CLAWS)) AbilityType.CLAWS.run(e);
                if (mgp.hasAbility(AbilityType.CARNIVORE)) AbilityType.CARNIVORE.run(e);
            }

            if (isWereWolf(damager) && damager.getInventory().getItemInMainHand().getType() != Material.AIR && WeatherUtil.isNight(damager.getWorld()) && WeatherUtil.isFullMoon(damager.getWorld())) {
                e.setDamage(e.getDamage() / 2);
            }
        }
    }

    @EventHandler
    public void onPounce(PlayerInteractEvent e) {
        if (!WorldCheck.isEnabled(e.getPlayer().getWorld())) return;
        if (e.getHand() != EquipmentSlot.HAND) return;

        MGPlayer mgp = Races.getMGPlayer(e.getPlayer());

        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (mgp.hasAbility(AbilityType.HOWL) && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.BONE) {
                AbilityType.HOWL.run(e.getPlayer());
            }
        }

        if ((e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) && mgp.hasAbility(AbilityType.DIG) && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
            AbilityType.DIG.run(e);
        }

        if (!mgp.hasAbility(AbilityType.POUNCE) || e.getAction() != Action.RIGHT_CLICK_AIR) return;

        Player p = e.getPlayer();

        if (!p.isSneaking()) return;

        AbilityType.POUNCE.run(e);
    }

    @EventHandler
    public void onWolfFall(EntityDamageEvent e) {

        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player p = (Player) e.getEntity();
            MGPlayer mgp = Races.getMGPlayer(p);

            if (mgp.getAbilityLevel(AbilityType.NOCTURNAL) > 4 && WeatherUtil.isNight(p.getWorld())) {
                e.setDamage(0.0);
                e.setCancelled(true);
            }
        }
    }

    public static boolean isWereWolf(Player p) {
        return Races.getRace(p) == RaceType.WEREWOLF;
    }
}
