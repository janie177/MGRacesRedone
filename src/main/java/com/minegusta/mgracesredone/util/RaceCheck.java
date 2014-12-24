package com.minegusta.mgracesredone.util;

import com.minegusta.mgracesredone.data.Storage;
import com.minegusta.mgracesredone.races.RaceType;
import org.bukkit.entity.Player;

public class RaceCheck
{
    public static boolean isHuman(Player p)
    {
        return Storage.getRace(p.getUniqueId().toString()) == RaceType.HUMAN;
    }

    public static boolean isHuman(String uuid)
    {
        return Storage.getRace(uuid) == RaceType.HUMAN;
    }

    public static RaceType getRace(String uuid)
    {
        return Storage.getRace(uuid);
    }

    public static RaceType getRace(Player p)
    {
        return Storage.getRace(p.getUniqueId().toString());
    }

}
