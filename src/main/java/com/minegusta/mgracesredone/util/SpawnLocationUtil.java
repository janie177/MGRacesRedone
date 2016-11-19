package com.minegusta.mgracesredone.util;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.races.RaceType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.concurrent.ConcurrentMap;

public class SpawnLocationUtil {

    private static ConcurrentMap<RaceType, Location> spawns = Maps.newConcurrentMap();

    public static void setSpawn(RaceType type, Location l) {
        spawns.put(type, l);
    }

    public static Location getSpawn(RaceType type) {
        return spawns.getOrDefault(type, spawns.getOrDefault(RaceType.HUMAN, Bukkit.getWorlds().get(0).getSpawnLocation()));
    }

    public static void init() {
        FileConfiguration conf = Main.getPlugin().getConfig();

        spawns.clear();

        if (!conf.isSet("spawns")) return;


        //Load the spawns from the file
        for (String s : conf.getConfigurationSection("spawns").getKeys(false)) {
            try {
                RaceType race = RaceType.valueOf(s.toUpperCase());
                Location l = LocationUtil.toLocation(conf.getString("spawns." + s.toUpperCase()));
                spawns.put(race, l);
            } catch (Exception ignored) {
                Bukkit.getLogger().info(ChatColor.RED + "Could not load spawn for race '" + s + "'.");
            }
        }

    }

    public static void saveToFile() {
        FileConfiguration conf = Main.getPlugin().getConfig();
        for (RaceType r : spawns.keySet()) {
            Location l = spawns.get(r);
            conf.set("spawns." + r.name(), LocationUtil.toString(l));
        }
        Main.getPlugin().saveConfig();
    }
}
