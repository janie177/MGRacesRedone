package com.minegusta.mgracesredone.playerdata;

import com.minegusta.mgracesredone.data.Storage;
import org.bukkit.entity.Player;

public class MapManager
{
    public static void add(Player p)
    {
        Storage.add(p, new MGPlayer(p));
    }

    public static void add(String uuid)
    {
        Storage.add(uuid, new MGPlayer(uuid));
    }

    public static void remove(Player p)
    {
        Storage.remove(p);
    }

    public static void remove(String uuid)
    {
        Storage.remove(uuid);
    }
}
