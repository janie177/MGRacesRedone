package com.minegusta.mgracesredone.util;

import com.minegusta.mgracesredone.files.FileManager;
import com.minegusta.mgracesredone.playerdata.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class OnReload
{
    public static void addToMap()
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            MapManager.add(p, FileManager.getFile(p.getUniqueId()));
        }
    }
}
