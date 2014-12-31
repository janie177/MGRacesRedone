package com.minegusta.mgracesredone.files;

import com.minegusta.mgracesredone.races.RaceType;
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

    public static FileConfiguration getConfig()
    {
        return playerFile;
    }

    public static RaceType getRace(String uuid)
    {
        if(getConfig().isSet(uuid))
        {
            return RaceType.valueOf(getConfig().getString(uuid));
        }
        return RaceType.HUMAN;
    }

    public static void setRace(String uuid, RaceType raceType)
    {
        String saved = raceType.getName().toUpperCase();
        getConfig().set(uuid, saved);
    }

    public static boolean save()
    {
        return YamlUtil.saveFile(path, fileName, playerFile);
    }
}
