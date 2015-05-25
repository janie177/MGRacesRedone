package com.minegusta.mgracesredone.playerdata;

import com.minegusta.mgracesredone.data.Storage;
import com.minegusta.mgracesredone.main.Races;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MapManager
{
    public static void add(Player p, FileConfiguration f)
    {
        Storage.add(p, new MGPlayer(p, f));
    }

    public static void add(String uuid, FileConfiguration f)
    {
        Storage.add(uuid, new MGPlayer(uuid, f));
    }

    public static void remove(Player p)
    {
        Races.savePlayerFile(p.getUniqueId().toString());
        Storage.remove(p);
    }

    public static void remove(String uuid)
    {
        Races.savePlayerFile(uuid);
        Storage.remove(uuid);
    }
}
