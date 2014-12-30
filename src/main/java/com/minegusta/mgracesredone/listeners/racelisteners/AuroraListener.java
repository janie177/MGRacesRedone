package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.Races;
import com.minegusta.mgracesredone.util.WeatherUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class AuroraListener implements Listener
{
    @EventHandler
    public void onDrown(EntityDamageEvent e)
    {
        if(e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.DROWNING)
        {
            if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;
            Player p = (Player) e.getEntity();
            if(isAurora(p))
            {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSnowFallDamage(EntityDamageEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL)
        {
            Player p = (Player) e.getEntity();
            if(isAurora(p))
            {
                e.setDamage(0.0);
                e.setCancelled(true);
            }
        }
    }

    private static boolean isAurora(Player p)
    {
        return Races.getRace(p) == RaceType.AURORA;
    }
}
