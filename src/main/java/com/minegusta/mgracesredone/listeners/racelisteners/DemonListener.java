package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.Races;
import com.minegusta.mgracesredone.util.WeatherUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import com.sk89q.worldedit.foundation.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DemonListener implements Listener
{
    @EventHandler
    public void onNetherFallDamage(EntityDamageEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL)
        {
            Player p = (Player) e.getEntity();
            if(isDemon(p) && WeatherUtil.isHell(p.getLocation()))
            {
                e.setDamage(0.0);
                e.setCancelled(true);
            }
        }
    }

    private static boolean isDemon(Player p)
    {
        return Races.getRace(p) == RaceType.DEMON;
    }
}
