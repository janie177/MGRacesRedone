package com.minegusta.mgracesredone.util;


import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class ShadowInvisibility
{
    public static ConcurrentMap<String, Boolean> invisible = Maps.newConcurrentMap();

    public static boolean contains(String uuid)
    {
        return invisible.containsKey(uuid);
    }

    public static void add(String uuid)
    {
        Player added = Bukkit.getPlayer(UUID.fromString(uuid));
        invisible.put(uuid, true);
        for(Player p : Bukkit.getOnlinePlayers())
        {
            p.hidePlayer(added);
        }
    }

    public static void remove(String uuid)
    {
        if(invisible.containsKey(uuid))
        {
            invisible.remove(uuid);
        }
        Player removed = Bukkit.getPlayer(UUID.fromString(uuid));
        for(Player p : Bukkit.getOnlinePlayers())
        {
            p.showPlayer(removed);
        }
    }

    public static Set<String> values()
    {
        return invisible.keySet();
    }
}
