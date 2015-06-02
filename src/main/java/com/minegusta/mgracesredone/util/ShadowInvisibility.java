package com.minegusta.mgracesredone.util;


import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
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
        Player added = Bukkit.getPlayer(uuid);
        invisible.put(uuid, true);
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(p.getUniqueId().equals(added.getUniqueId()))continue;
            p.hidePlayer(added);
        }
    }

    public static void remove(String uuid)
    {
        if(invisible.containsKey(uuid))
        {
            invisible.remove(uuid);
        }
        Player removed = Bukkit.getPlayer(uuid);
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(p.getUniqueId().equals(removed.getUniqueId()))continue;
            p.showPlayer(removed);
        }
    }

    public static Set<String> values()
    {
        return invisible.keySet();
    }
}
