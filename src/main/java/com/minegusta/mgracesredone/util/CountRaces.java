package com.minegusta.mgracesredone.util;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.races.RaceType;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class CountRaces {

    private static List<String> amounts = Lists.newArrayList();

    public static List<String> getAmounts() {
        return amounts;
    }

    public static void count() {
        File dir = new File(Main.getPlugin().getDataFolder() + "/players/");
        if (dir.listFiles() == null) return;

        for (RaceType race : RaceType.values()) {

            int amount = 0;

            for (File file : dir.listFiles()) {
                if (file.getName().endsWith(".yml")) {
                    FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
                    RaceType raceType = RaceType.valueOf(conf.getString("racetype", "HUMAN"));
                    if (raceType == race) {
                        amount++;
                    }
                }
            }

            amounts.add(ChatColor.GRAY + " - " + ChatColor.YELLOW + race.getName() + ChatColor.LIGHT_PURPLE + " " + amount);
        }
    }

}
