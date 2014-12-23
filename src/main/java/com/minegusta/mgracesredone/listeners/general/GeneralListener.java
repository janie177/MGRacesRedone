package com.minegusta.mgracesredone.listeners.general;

import com.minegusta.mgracesredone.data.Storage;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.playerdata.MapManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GeneralListener implements Listener
{
    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        MapManager.add(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        MapManager.remove(e.getPlayer());
    }

    @EventHandler
    public void onWorldSwitch(PlayerChangedWorldEvent e)
    {
        MGPlayer mgp = Storage.getPlayer(e.getPlayer());

        mgp.updateHealth();
    }
}
