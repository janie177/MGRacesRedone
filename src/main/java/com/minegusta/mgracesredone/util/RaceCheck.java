package com.minegusta.mgracesredone.util;

import com.minegusta.mgracesredone.data.Storage;
import com.minegusta.mgracesredone.races.RaceType;
import org.bukkit.entity.Player;

public class RaceCheck
{
    private static boolean isHuman(Player p)
    {
        return Storage.getRace(p.getUniqueId().toString()) == RaceType.HUMAN;
    }

    private static boolean isHuman(String uuid)
    {
        return Storage.getRace(uuid) == RaceType.HUMAN;
    }

    private RaceType getRace(String uuid)
    {
        return Storage.getRace(uuid);
    }

    private RaceType getRace(Player p)
    {
        return Storage.getRace(p.getUniqueId().toString());
    }

}
