package com.minegusta.mgracesredone.files;

import com.minegusta.mgracesredone.playerdata.MapManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class LoadPlayerData
{
    public static void load(String uuid)
    {
        FileConfiguration conf = FileManager.getFile(uuid);

        MapManager.add(uuid, conf);
    }

    public static void unload(String uuid)
    {
        MapManager.remove(uuid);
    }

    public static void unload(UUID uuid)
    {
        unload(uuid.toString());
    }

    public static void load(UUID uuid) {
        load(uuid.toString());
    }
}
