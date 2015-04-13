package com.minegusta.mgracesredone.files;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class FileManager
{

    public static FileConfiguration getFile(String uuid)
    {
        return YamlUtil.getConfiguration("/players/", uuid + ".yml");
    }

    public static FileConfiguration getFile(UUID uuid)
    {
        return getFile(uuid.toString());
    }

    public static boolean save(String uuid, FileConfiguration f)
    {
        return YamlUtil.saveFile("/players/", uuid + ".yml", f);
    }

    public static boolean save(UUID uuid, FileConfiguration f)
    {
        return save(uuid.toString(), f);
    }
}
