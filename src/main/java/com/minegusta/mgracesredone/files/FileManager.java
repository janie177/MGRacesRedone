package com.minegusta.mgracesredone.files;

import com.minegusta.mgracesredone.races.RaceType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class FileManager
{
    private static String path = "/players/";
    private static String fileName = "playerfile.yml";
    private static FileConfiguration playerFile;

    public static void create()
    {
        playerFile = YamlUtil.getConfiguration(path, fileName);
    }

    public static FileConfiguration get()
    {
        return playerFile;
    }

    public static RaceType getRace(String uuid)
    {
        if(get().isSet(uuid))
        {
            return RaceType.valueOf(get().getString(uuid));
        }
        return RaceType.HUMAN;
    }

    public static void setRace(String uuid, RaceType raceType)
    {
        get().set(uuid, raceType.name());
    }

    public static boolean save()
    {
        return YamlUtil.saveFile(path, fileName, playerFile);
    }
}
