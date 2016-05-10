package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.Bind;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class AngelListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    private void angelFalling(PlayerMoveEvent e) {
        if (!WorldCheck.isEnabled(e.getPlayer().getWorld())) return;

        if (e.getFrom().getY() <= e.getTo().getY()) return;

        Player p = e.getPlayer();

        if (!Races.getMGPlayer(p).hasAbility(AbilityType.GLIDE)) return;

        Material item = p.getInventory().getItemInMainHand().getType();
        short data = item != Material.AIR ? p.getInventory().getItemInMainHand().getDurability() : 0;
        boolean ignoreData = BindUtil.ignoreItemData(item);

        for (Bind b : Races.getMGPlayer(p).getBindForAbility(AbilityType.GLIDE)) {
            if (b.getItem() == item && (data == b.getData() || ignoreData)) {
                AbilityType.GLIDE.run(p);
                break;
            }
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player p = (Player) e.getEntity();

            if (!Races.getMGPlayer(p).hasAbility(AbilityType.HOLINESS)) return;

            //Do not run this in the holiness class because YOLO ;D
            e.setDamage(0);
            e.setCancelled(true);
        }

        //Nyctophobia level 3 prevents poison damage.
        if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.POISON) {
            if (Races.getMGPlayer((Player) e.getEntity()).getAbilityLevel(AbilityType.NYCTOPHOBIA) > 2) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onAngelTargetEntity(EntityTargetLivingEntityEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if (e.getTarget() instanceof Player && Races.getRace((Player) e.getTarget()) == RaceType.ANGEL && e.getEntity() instanceof Rabbit) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onAngelDamage(EntityDamageByEntityEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;


        //Angels cannot damage anything without a sword or bow.
        if (e.getDamager() instanceof Player && Races.getRace((Player) e.getDamager()) == RaceType.ANGEL) {
            Player p = (Player) e.getDamager();
            if (!ItemUtil.isSword(p.getInventory().getItemInMainHand().getType())) {
                e.setDamage(0);
                e.setCancelled(true);
            }
        }

        if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
            Player p = (Player) e.getDamager();
            int level = Races.getMGPlayer(p).getAbilityLevel(AbilityType.PURGE);

            //Purge against mobs
            if (Races.getMGPlayer(p).hasAbility(AbilityType.PURGE) && MonsterUtil.isUnholy(e.getEntityType())) {
                //Apply extra damage.
                int damage = level + 1;
                if (level % 2 != 0) {
                    e.setDamage(e.getDamage() + damage);
                } else {
                    e.setDamage(e.getDamage() + level);
                }
            }

            //Purge against races
            if (e.getEntity() instanceof Player && PlayerUtil.isUnholy((Player) e.getEntity())) {
                //Crit chance against RACES.
                if (level > 3) {
                    if (RandomUtil.chance(10)) {
                        double added = e.getDamage() / 20;
                        e.setDamage(e.getDamage() + added);
                    }
                } else if (level > 1) {
                    if (RandomUtil.chance(10)) {
                        double added = e.getDamage() / 10;
                        e.setDamage(e.getDamage() + added);
                    }
                }
            }

            //Invincibility damage halved
            if (AngelInvincibility.contains(p.getUniqueId().toString())) {
                e.setDamage(e.getDamage() / 2);
            }

        }

        //Angels wont get damage in invincibility mode.
        if (e.getEntity() instanceof Player) {
            if (AngelInvincibility.contains(e.getEntity().getUniqueId().toString())) {
                if (e.getDamager() instanceof Player) {
                    e.getDamager().sendMessage(ChatColor.RED + "The Angel has activated invincibility! RUN!");
                }
                e.setDamage(0.0);
            }
        }
    }

    @EventHandler
    public void onAngelDamage(EntityDamageEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if (e.getEntity() instanceof Player) {
            if (AngelInvincibility.contains(e.getEntity().getUniqueId().toString())) {
                e.setDamage(0.0);
            }
            //Angels do not get poison damage with the right perks.
            else if (e.getCause() == EntityDamageEvent.DamageCause.POISON && Races.getMGPlayer((Player) e.getEntity()).getAbilityLevel(AbilityType.NYCTOPHOBIA) > 3) {
                e.setCancelled(true);
                e.setDamage(0);
            }
        }
    }

    @EventHandler
    public void onAngelDamage(EntityDamageByBlockEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if (e.getEntity() instanceof Player) {
            if (AngelInvincibility.contains(e.getEntity().getUniqueId().toString())) {
                e.setDamage(0.0);
            }
        }
    }
}
