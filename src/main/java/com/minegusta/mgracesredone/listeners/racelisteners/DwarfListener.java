package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.Effect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.potion.PotionEffectType;

public class DwarfListener implements Listener
{
    @EventHandler
    public void onDwarfAxeHit(EntityDamageByEntityEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity)
        {
            Player p = (Player) e.getDamager();
            if(isDwarf(p) && ItemUtil.isAxe(p.getItemInHand().getType()) && WGUtil.canFightEachother(p, e.getEntity()) && !e.isCancelled())
            {
                e.setDamage(e.getDamage() + 2.0);
            }
        }

        //Arrow weakness

        if(e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE && e.getEntity() instanceof Player)
        {
            Player p = (Player) e.getEntity();
            if(isDwarf(p) && !e.isCancelled() && WGUtil.canGetDamage(p))
            {
                e.setDamage(e.getDamage() + 1.0);
            }
        }
    }

    @EventHandler
    public void onKillStreak(EntityDeathEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity().getKiller() != null)
        {
            Player p = e.getEntity().getKiller();
            if(isDwarf(p))
            {
                PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 0, 8);
                EffectUtil.playParticle(p, Effect.VILLAGER_THUNDERCLOUD);
            }
        }
    }

    private static boolean isDwarf(Player p)
    {
        return Races.getRace(p) == RaceType.DWARF;
    }
}
