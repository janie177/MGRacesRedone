package com.minegusta.mgracesredone.tasks;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.util.Bleed;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.concurrent.ConcurrentMap;

public class BleedTask {
    private static int id = -1;

    public static void start() {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), BleedTask::bleed, 20, 20);
    }

    public static void stop() {
        if (id != -1) {
            Bukkit.getScheduler().cancelTask(id);
        }
    }

    public static ConcurrentMap<String, Bleed> bleeding = Maps.newConcurrentMap();

    public static void addBleed(LivingEntity ent, Entity damager, int duration) {
        if (bleeding.containsKey(ent.getUniqueId().toString())) return;
        bleeding.put(ent.getUniqueId().toString(), new Bleed(damager, ent, duration));
    }

    private static void bleed() {
        for (String s : bleeding.keySet()) {
            Bleed b = bleeding.get(s);
            if (!b.bleed()) {
                bleeding.remove(s);
            }
        }
    }
}
