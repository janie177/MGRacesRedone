package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

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
            p.setVelocity(p.getLocation().getDirection().setY(-0.2));
        }
    }

}
