package com.minegusta.mgracesredone.tasks;

import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.enderborn.EnderShield;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class ShieldTask {
    private static int id = -1;
    private static int rotationAngle = 0;

    public static void start() {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), ShieldTask::effects, 5, 3);
    }

    public static void stop() {
        if (id != -1) {
            Bukkit.getScheduler().cancelTask(id);
        }
    }

    private static void effects() {
        rotationAngle = rotationAngle + 6;
        if (rotationAngle >= 360) rotationAngle = 0;

        EnderShield.shields.keySet().forEach(id -> {
            int amount = EnderShield.shields.get(id);
            OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(id));
            if (!p.isOnline()) {
                EnderShield.shields.remove(id);
            } else {
                Location l = p.getPlayer().getLocation();
                playEffect(calculateCircle(l, rotationAngle));
                if (amount > 1) {
                    playEffect(calculateCircle(l, rotationAngle + 180));
                }
            }
        });
    }

    private static void playEffect(Location l) {
        l.getWorld().spigot().playEffect(l, Effect.WITCH_MAGIC, 0, 0, 0, 0, 0, 1 / 20, 1, 25);
    }

    private static Location calculateCircle(Location l, int angle) {
        return new Location(l.getWorld(), l.getX() + 1.4 * Math.sin(angle), l.getY(), l.getZ() + 1.4 * Math.cos(angle));
    }
}
