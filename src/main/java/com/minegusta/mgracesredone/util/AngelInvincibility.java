package com.minegusta.mgracesredone.util;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentMap;

public class AngelInvincibility
{
    public static ConcurrentMap<String, InvincibleBoost> invincibleMap = Maps.newConcurrentMap();

    public static boolean remove(String uuid)
    {
        if(invincibleMap.containsKey(uuid))
        {
            invincibleMap.remove(uuid);
            return true;
        }
        return false;
    }

    public static void startInvincibility(Player p, int seconds, int endHealth)
    {
        invincibleMap.put(p.getUniqueId().toString(), new InvincibleBoost(p, seconds, endHealth));
    }

    public static boolean contains(String uuid)
    {
        return invincibleMap.containsKey(uuid);
    }
}
