package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class AngelListener implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    private void angelFalling(PlayerMoveEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;

        if(Races.getRace(e.getPlayer()) != RaceType.ANGEL)return;

        if(e.getFrom().getY() <= e.getTo().getY())return;

        Player p = e.getPlayer();
        if(p.getItemInHand() != null && p.getItemInHand().getType() == Material.FEATHER)
        {
            p.setVelocity(p.getLocation().getDirection().multiply(0.6).setY(-0.12));
        }
    }

    @EventHandler
    public void onNetherFallDamage(EntityDamageEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL)
        {
            Player p = (Player) e.getEntity();
            if(isAngel(p))
            {
                e.setDamage(0.0);
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onHolyRain(PlayerInteractEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;

        Player p = e.getPlayer();

        if((e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) && p.isSneaking() && isAngel(p) && ItemUtil.isSword(p.getItemInHand().getType()))
        {
            String uuid = p.getUniqueId().toString();
            String name = "hrain";
            if(Cooldown.isCooledDown(name, uuid))
            {
                Cooldown.newCoolDown(name, uuid, 180);
                EffectUtil.playParticle(p, Effect.MAGIC_CRIT);
                EffectUtil.playSound(p, Sound.AMBIENCE_THUNDER);
                p.sendMessage(ChatColor.AQUA + "You call a holy rain on your location!");

                startRain(p.getLocation().add(0,9,0));
            }
            else
            {
                ChatUtil.sendString(p, "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use Holy Rain.");
            }
        }
    }

    private void startRain(Location l)
    {
        HolyRain rain = new HolyRain(30, l);
        rain.start();
    }

    private boolean isAngel(Player p)
    {
        return Races.getRace(p) == RaceType.ANGEL;
    }
}
