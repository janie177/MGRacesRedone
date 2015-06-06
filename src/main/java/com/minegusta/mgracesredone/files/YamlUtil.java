package com.minegusta.mgracesredone.files;

import com.minegusta.mgracesredone.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class YamlUtil {
    //Credits to CensoredSoftware for letting me use this!

    public static FileConfiguration getConfiguration(String path, String fileName) {
        File dataFile = new File(Main.getPlugin().getDataFolder() + path + fileName);
        if (!(dataFile.exists())) createFile(dataFile);
        return YamlConfiguration.loadConfiguration(dataFile);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void createFile(File dataFile) {
        try {
            // Create the directories.
            if (!Main.getPlugin().getDataFolder().exists()) Main.getPlugin().getDataFolder().mkdirs();
            (dataFile.getParentFile()).mkdirs();

            // Create the new file.
            dataFile.createNewFile();
        } catch (Exception errored) {
            throw new RuntimeException("Couldn't create a data file!", errored);
        }
    }

    public static boolean saveFile(String path, String fileName, FileConfiguration conf) {
        try {
            conf.save(Main.getPlugin().getDataFolder() + path + fileName);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }
}
