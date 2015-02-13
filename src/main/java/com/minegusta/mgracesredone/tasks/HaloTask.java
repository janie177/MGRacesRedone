package com.minegusta.mgracesredone.tasks;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.HaloBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class HaloTask
{
    private static ConcurrentMap<String, Boolean> angels = Maps.newConcurrentMap();
    private static int ID = 0;
    private static int ID2 = 0;

    public static void start()
    {
        ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run()
            {
                for(String s : angels.keySet())
                {
                    UUID uuid = UUID.fromString(s);
                    if(Bukkit.getOfflinePlayer(UUID.fromString(s)).isOnline())
                    {
                        HaloBuilder.spawnHalo(Bukkit.getPlayer(uuid));
                    }
                    else
                    {
                        angels.remove(s);
                    }
                }
            }
        }, 20, 5);

        ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run()
            {
                for(Player p : Bukkit.getOnlinePlayers())
                {
                    if(Races.getRace(p) == RaceType.ANGEL)
                    {
                        angels.put(p.getUniqueId().toString(), true);
                    }
                }
            }
        }, 20, 20 * 8);
    }

    public static void stop()
    {
        if(ID != 0)Bukkit.getScheduler().cancelTask(ID);
        if(ID2 != 0)Bukkit.getScheduler().cancelTask(ID2);
    }
}

