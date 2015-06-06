package com.minegusta.mgracesredone.tasks;

import com.minegusta.mgracesredone.data.Storage;
import com.minegusta.mgracesredone.main.Main;
import org.bukkit.Bukkit;

public class SaveTask {
    private static int TASK = -1;

    public static void start() {
        TASK = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), SaveTask::save, 20 * 180, 20 * 180);
    }

    public static void stop() {
        if (TASK != -1) {
            Bukkit.getScheduler().cancelTask(TASK);
        }
    }

    public static void save() {
        Storage.getPlayers().forEach(com.minegusta.mgracesredone.playerdata.MGPlayer::saveFile);
    }
}
