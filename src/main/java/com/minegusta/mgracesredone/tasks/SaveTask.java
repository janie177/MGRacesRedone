package com.minegusta.mgracesredone.tasks;

import com.minegusta.mgracesredone.data.Storage;
import com.minegusta.mgracesredone.files.FileManager;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import org.bukkit.Bukkit;

public class SaveTask
{
    private static int TASK = -1;

    public static void start()
    {
        TASK = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                save();
            }
        }, 20 * 180, 20 * 180);
    }

    public static void stop()
    {
        if(TASK != -1)Bukkit.getScheduler().cancelTask(TASK);
    }

    private static void save()
    {
        for(MGPlayer mgp : Storage.getPlayers())
        {
            FileManager.getConfig().set(mgp.getUniqueIdAsString(), mgp.getRaceType().name());
        }
        FileManager.save();
    }
}
