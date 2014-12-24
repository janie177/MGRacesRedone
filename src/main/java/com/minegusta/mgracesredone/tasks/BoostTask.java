package com.minegusta.mgracesredone.tasks;

import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.util.RaceCheck;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BoostTask
{
    private static int TASK = -1;

    public static void start()
    {
        TASK = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                boost();
            }
        }, 20, 20);
    }

    public static void stop()
    {
        if(TASK != -1)Bukkit.getScheduler().cancelTask(TASK);
    }

    private static void boost()
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(WorldCheck.isEnabled(p.getWorld()))
            {
                RaceCheck.getRace(p).passiveBoost(p);
            }
        }
    }
}
