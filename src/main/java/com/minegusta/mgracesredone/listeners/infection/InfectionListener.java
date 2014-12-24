package com.minegusta.mgracesredone.listeners.infection;

import com.minegusta.mgracesredone.recipes.Recipe;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class InfectionListener implements Listener
{
    /**
     * It is important to update changes in the documentation by hand!
     */
    //Aurora
    @EventHandler
    public void onAuroraDeath(PlayerDeathEvent e)
    {
        Player p = e.getEntity();
        if(!WorldCheck.isEnabled(p.getWorld()))return;
        if(p.getLocation().getBlock().getTemperature() > 0.15)return;
        if(e.getEntity().getLastDamageCause() != null && e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.DROWNING)
        {
            if(p.getInventory().containsAtLeast(Recipe.ICECRYSTAL.getResult(), 1))
            {

            }
        }
    }


}
