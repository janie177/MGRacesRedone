package com.minegusta.mgracesredone.util;


import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class InvisibilityUtil {
    public static ConcurrentMap<String, Integer> invisible = Maps.newConcurrentMap();

    public static boolean contains(String uuid) {
        return invisible.containsKey(uuid);
    }

    public static void add(String uuid) {
        Player added = Bukkit.getPlayer(UUID.fromString(uuid));
        invisible.put(uuid, 1);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.hidePlayer(added);
        }
    }

    public static void remove(String uuid) {
        if (invisible.containsKey(uuid)) {
            invisible.remove(uuid);
        }
        try {
            Player removed = Bukkit.getPlayer(UUID.fromString(uuid));
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.showPlayer(removed);
            }
        } catch (Exception ignored) {
        }
    }

    public static Set<String> values() {
        return invisible.keySet();
    }
}
