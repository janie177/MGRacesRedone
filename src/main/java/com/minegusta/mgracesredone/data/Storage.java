package com.minegusta.mgracesredone.data;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.files.FileManager;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.playerdata.MapManager;
import com.minegusta.mgracesredone.races.RaceType;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;

public class Storage {
    public static ConcurrentMap<String, MGPlayer> racesMap = Maps.newConcurrentMap();

    public static MGPlayer getPlayer(String uuid) {
        if (!racesMap.containsKey(uuid)) {
            MapManager.add(uuid, FileManager.getFile(uuid));
        }
        return racesMap.get(uuid);
    }

    public static MGPlayer getPlayer(Player p) {
        return getPlayer(p.getUniqueId().toString());
    }

    public static RaceType getRace(String uuid) {
        return getPlayer(uuid).getRaceType();
    }

    public static RaceType getRace(Player p) {
        return getRace(p.getUniqueId().toString());
    }

    public static void add(Player p, MGPlayer mgp) {
        racesMap.put(p.getUniqueId().toString(), mgp);
    }

    public static void add(String uuid, MGPlayer mgp) {
        racesMap.put(uuid, mgp);
    }

    public static void remove(Player p) {
        if (racesMap.containsKey(p.getUniqueId().toString())) racesMap.remove(p.getUniqueId().toString());
    }

    public static Collection<MGPlayer> getPlayers() {
        return racesMap.values();
    }

    public static void remove(String uuid) {
        if (racesMap.containsKey(uuid)) racesMap.remove(uuid);
    }
}
