package com.minegusta.mgracesredone.util;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class Cooldown
{

    private static ConcurrentMap<String, Long> cooldown = Maps.newConcurrentMap();

    public static void newCoolDown(String cooldownName, String id, int seconds)
    {
        String stored = cooldownName + id;
        long expireTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(seconds);

        cooldown.put(stored, expireTime);
    }

    public static int getRemaining(String cooldownName, String id)
    {
        if(!cooldown.containsKey(cooldownName + id))return 0;
        long remainingSeconds = cooldown.get(cooldownName + id) - System.currentTimeMillis();
        return (int) TimeUnit.MILLISECONDS.toSeconds(remainingSeconds);
    }

    public static boolean isCooledDown(String cooldownName, String id)
    {
        if(!cooldown.containsKey(cooldownName + id))return true;
        if(cooldown.get(cooldownName + id) <= System.currentTimeMillis())
        {
            remove(cooldownName, id);
            return true;
        }
        return false;
    }

    private static void remove(String cooldownName, String id)
    {
        if(!cooldown.containsKey(cooldownName + id)) cooldown.remove(cooldownName + id);
    }
}
