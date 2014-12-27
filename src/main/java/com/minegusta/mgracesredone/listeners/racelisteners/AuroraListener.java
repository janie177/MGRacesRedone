package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.Races;
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
            if(Races.getRace(p) == RaceType.AURORA)
            {
                e.setCancelled(true);
            }
        }
    }
}
