package com.minegusta.mgracesredone.listeners.general;

import com.minegusta.mgracesredone.data.Storage;
import com.minegusta.mgracesredone.files.FileManager;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.playerdata.MapManager;
import com.minegusta.mgracesredone.util.InvisibilityUtil;
import com.minegusta.mgracesredone.util.ScoreboardUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class GeneralListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        MapManager.add(e.getPlayer(), FileManager.getFile(e.getPlayer().getUniqueId()));

        Player joined = e.getPlayer();

        for (String s : InvisibilityUtil.values()) {
            Player p = Bukkit.getPlayer(UUID.fromString(s));
            joined.hidePlayer(p);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        MapManager.remove(e.getPlayer());
        ScoreboardUtil.removeScoreBoard(e.getPlayer());

        String uuid = e.getPlayer().getUniqueId().toString();

        if (InvisibilityUtil.contains(uuid)) {
            InvisibilityUtil.remove(uuid);
        }
    }

    @EventHandler
    public void onWorldSwitch(PlayerChangedWorldEvent e) {
        MGPlayer mgp = Storage.getPlayer(e.getPlayer());

        mgp.updateHealth();
        mgp.updateScoreboard();

        if (!WorldCheck.isEnabled(e.getPlayer().getWorld())) return;

        Player changed = e.getPlayer();
        String uuid = changed.getUniqueId().toString();

        if (InvisibilityUtil.contains(uuid)) {
            InvisibilityUtil.remove(uuid);
        }
    }
}
