package com.minegusta.mgracesredone.listeners.racelisteners;


import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.*;
import com.minegusta.mgracesredone.main.Races;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Weather;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class WereWolfListener implements Listener
{
    @EventHandler
    public void onWolfInteract(PlayerInteractEntityEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;
        Player p = e.getPlayer();

        if(isWereWolf(p) && !e.isCancelled())
        {
            if(e.getRightClicked() instanceof Wolf)
            {
                Wolf w = (Wolf) e.getRightClicked();
                if(!w.isTamed())
                {
                    w.setTamed(true);
                    w.setOwner(p);
                    EffectUtil.playParticle(w, Effect.HEART);
                    EffectUtil.playSound(p, Sound.WOLF_HOWL);
                    p.sendMessage(ChatColor.RED + "You tamed a wolf! It's now part of your pack.");
                }
                else if(p.isSneaking() && w.getOwner().getUniqueId().equals(p.getUniqueId()))
                {
                    double health = p.getHealth();
                    double maxHealed = p.getMaxHealth() - health;
                    double healed = w.getHealth() / 2;
                    if(healed > maxHealed) healed = maxHealed;

                    p.setHealth(p.getHealth() + healed);

                    EffectUtil.playParticle(p, Effect.HEART);

                    EffectUtil.playSound(p, Sound.WOLF_GROWL);
                    EffectUtil.playParticle(w, Effect.CRIT, 1, 1, 1, 30);
                    w.damage(1000);
                    p.sendMessage(ChatColor.RED + "You drained a wolf's life force.");
                }
            }
        }
    }

    @EventHandler
    public void onWerewolfGoldDamage(EntityDamageByEntityEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player)
        {
            Player player = (Player) e.getEntity();
            Player damager = (Player) e.getDamager();

            if(!WGUtil.canFightEachother(player, damager))return;

            if(isWereWolf(player))
            {
                if(ItemUtil.isGoldTool(damager.getItemInHand().getType()))
                {
                    e.setDamage(e.getDamage() + 10.0);
                    return;
                }
            }
        }
        if(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity && isWereWolf((Player) e.getDamager()))
        {
            Player damager = (Player) e.getDamager();
            if(!WeatherUtil.isNight(damager.getWorld()))return;
            if(damager.getItemInHand().getType() == null || damager.getItemInHand().getType() == Material.AIR)
            {
                e.setDamage(e.getDamage() + 12.0);
            }
            else {
                e.setDamage(e.getDamage() / 5);
            }
        }
    }

    @EventHandler
    public void onWerewolfJump(PlayerInteractEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;

        if(!isWereWolf(e.getPlayer()) || e.getAction() != Action.RIGHT_CLICK_AIR)return;

        Player p = e.getPlayer();

        if(!p.isSneaking())return;

        String name = "wolfjump";
        String uuid = p.getUniqueId().toString();

        if(Cooldown.isCooledDown(name, uuid))
        {
            //Jump
            EffectUtil.playSound(p, Sound.WOLF_HOWL);
            EffectUtil.playParticle(p, Effect.FLAME);

            p.teleport(p.getLocation().add(0,0.1,0));
            p.setVelocity(p.getLocation().getDirection().normalize().multiply(2.4D));

            Cooldown.newCoolDown(name, uuid, 5);
        }
        else
        {
            p.sendMessage(ChatColor.RED + "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use that.");
        }
    }

    @EventHandler
    public void onWolfFall(EntityDamageEvent e)
    {
        if(e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL)
        {
            Player p = (Player) e.getEntity();
            if(isWereWolf(p)& WeatherUtil.isNight(p.getWorld()))
            {
                e.setDamage(0.0);
                e.setCancelled(true);
            }
        }
    }

    public static boolean isWereWolf(Player p)
    {
        return Races.getRace(p) == RaceType.WEREWOLF;
    }
}
