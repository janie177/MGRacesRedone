package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class AngelListener implements Listener
{
    @EventHandler(priority = EventPriority.LOWEST)
    private void angelFalling(PlayerMoveEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;

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

            if (!Races.getMGPlayer(p).hasAbility(AbilityType.HOLYNESS)) return;

            //Do not run this in the holyness class because YOLO ;D
            e.setDamage(0);
            e.setCancelled(true);
        }

        //Nyctophobia level 3 prevents poison damage.
        if(e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.POISON)
        {
            if(Races.getMGPlayer((Player) e.getEntity()).getAbilityLevel(AbilityType.NYCTOPHOBIA) > 2)
            {
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
    public void onAngelFood(FoodLevelChangeEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof Player)
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

        if(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity)
        {
            Player p = (Player) e.getDamager();
            int level = Races.getMGPlayer(p).getAbilityLevel(AbilityType.PURGE);
            if(Races.getMGPlayer(p).hasAbility(AbilityType.PURGE) && MonsterUtil.isUnholy(e.getEntityType()))
            {
                //Apply extra damage.
                int damage = level + 1;
                if(level % 2 != 0)
                {
                    e.setDamage(e.getDamage() + damage);
                }
                else
                {
                    e.setDamage(e.getDamage() + level);
                }
            }
            if(e.getEntity() instanceof Player && PlayerUtil.isUnholy((Player) e.getEntity()))
            {
                //Crit chance against RACES.
                if(level > 3)
                {
                    if(RandomUtil.chance(10))
                    {
                        double added = e.getDamage() / 20;
                        e.setDamage(e.getDamage() + added);
                    }
                }
                else if(level > 1)
                {
                    if(RandomUtil.chance(10))
                    {
                        double added = e.getDamage() / 10;
                        e.setDamage(e.getDamage() + added);
                    }
                }
            }
        }

        //Angels wont get damage in invincibility mode.
        if(e.getEntity() instanceof Player)
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

        if(e.getEntity()instanceof Player)
        {
            if(AngelInvincibility.contains(e.getEntity().getUniqueId().toString()))
            {
                e.setDamage(0.0);
            }
            //Angels do not get poison damage with the right perks.
            else if(e.getCause() == EntityDamageEvent.DamageCause.POISON && Races.getMGPlayer((Player) e.getEntity()).getAbilityLevel(AbilityType.NYCTOPHOBIA) > 3)
            {
                e.setCancelled(true);
                e.setDamage(0);
            }
        }
    }

    @EventHandler
    public void onAngelDamage(EntityDamageByBlockEvent e)
    {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if(e.getEntity()instanceof Player)
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

        //Activate justice
        if(e.getAction() == Action.LEFT_CLICK_BLOCK && Races.getMGPlayer(p).hasAbility(AbilityType.JUSTICE) && e.getClickedBlock().getLocation().distance(p.getLocation()) < 2 && e.getClickedBlock().getY() < e.getPlayer().getLocation().getY())
        {
            if(p.isSneaking())
            {
                AbilityType.JUSTICE.run(p);
            }
        }

        if((e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR))
        {
            if(p.getItemInHand().getType() == Material.BOOK && Races.getMGPlayer(p).hasAbility(AbilityType.PRAYER))
            {
                AbilityType.PRAYER.run(p);
            }
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
}
