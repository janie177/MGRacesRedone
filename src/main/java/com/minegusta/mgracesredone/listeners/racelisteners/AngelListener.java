package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.Race;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
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

        if(!Races.getMGPlayer(p).hasAbility(AbilityType.GLIDE))return;

        if(p.getItemInHand() != null && p.getItemInHand().getType() == Material.FEATHER)
        {
            AbilityType.GLIDE.run(p);
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player p = (Player) e.getEntity();

            if (!isAngel(p)) return;

            if (!Races.getMGPlayer(p).hasAbility(AbilityType.HOLYNESS)) return;

            //Do not run this in the holyness class because YOLO ;D
            e.setDamage(0);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onAngelFood(FoodLevelChangeEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof Player && isAngel((Player) e.getEntity()))
        {
            MGPlayer mgp = Races.getMGPlayer((Player) e.getEntity());
            if(mgp.getAbilityLevel(AbilityType.HOLYNESS) < 3)return;
            AbilityType.HOLYNESS.run(e);
        }
    }


    @EventHandler
    public void onAngelDamage(EntityDamageByEntityEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;


        //Angels cannot damage anything without a sword or bow.
        if(e.getDamager() instanceof Player && Races.getRace((Player)e.getDamager()) == RaceType.ANGEL)
        {
            Player p = (Player) e.getDamager();
            if(!ItemUtil.isSword(p.getItemInHand().getType()))
            {
                e.setDamage(0);
            }
        }

        //Angels wont get damage in invincibility mode.
        if(e.getEntity() instanceof Player && Races.getRace((Player)e.getEntity()) == RaceType.ANGEL)
        {
            if(AngelInvincibility.contains(e.getEntity().getUniqueId().toString()))
            {
                e.setDamage(0.0);
            }
        }
    }

    @EventHandler
    public void onAngelDamage(EntityDamageEvent e)
    {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if(e.getEntity()instanceof Player && Races.getRace((Player)e.getEntity()) == RaceType.ANGEL)
        {
            if(AngelInvincibility.contains(e.getEntity().getUniqueId().toString()))
            {
                e.setDamage(0.0);
            }
        }
    }

    @EventHandler
    public void onAngelDamage(EntityDamageByBlockEvent e)
    {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if(e.getEntity()instanceof Player && Races.getRace((Player)e.getEntity()) == RaceType.ANGEL)
        {
            if(AngelInvincibility.contains(e.getEntity().getUniqueId().toString()))
            {
                e.setDamage(0.0);
            }
        }
    }


    @EventHandler
    public void onHolyRain(PlayerInteractEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;

        Player p = e.getPlayer();

        if((e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) && isAngel(p))
        {
            if(ItemUtil.isSword(p.getItemInHand().getType()) && Races.getMGPlayer(p).hasAbility(AbilityType.HOLYRAIN))
            {
                AbilityType.HOLYRAIN.run(p);
            }
            else if(p.getItemInHand() != null && p.getItemInHand().getType() == Material.FEATHER)
            {
                if(Races.getMGPlayer(p).hasAbility(AbilityType.WHIRLWIND))
                {
                    AbilityType.WHIRLWIND.run(p);
                }
            }
            else if(p.getItemInHand().getType() == Material.IRON_INGOT)
            {
                if(Races.getMGPlayer(p).hasAbility(AbilityType.STEELSKIN))
                {
                    AbilityType.STEELSKIN.run(p);
                }
            }
        }
    }

    private boolean isAngel(Player p)
    {
        return Races.getRace(p) == RaceType.ANGEL;
    }
}
