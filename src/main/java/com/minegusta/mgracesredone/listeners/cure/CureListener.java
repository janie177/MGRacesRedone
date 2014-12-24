package com.minegusta.mgracesredone.listeners.cure;

import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CureListener implements Listener
{
    @EventHandler
    public void onCure(PlayerInteractEvent event)
    {
        if(!WorldCheck.isEnabled(event.getPlayer().getWorld()))return;

        if(event.hasBlock() && event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {

        }
    }
}
