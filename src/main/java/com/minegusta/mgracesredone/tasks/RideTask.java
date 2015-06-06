package com.minegusta.mgracesredone.tasks;

import com.google.common.collect.Sets;
import com.minegusta.mgracesredone.listeners.racelisteners.ElfListener;
import com.minegusta.mgracesredone.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

public class RideTask {
    private static int ID = -1;

    public static void start() {
        ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), RideTask::loop, 5, 5);
    }

    public static void stop() {
        if (ID != -1) {
            Bukkit.getScheduler().cancelTask(ID);
        }
    }

    private static void remove(String s) {
        if (ElfListener.riders.containsKey(s)) {
            ElfListener.riders.remove(s);
        }
    }

    private static void loop() {
        if (ElfListener.riders.isEmpty()) {
            return;
        }

        for (String s : ElfListener.riders.keySet()) {
            LivingEntity ent = ElfListener.riders.get(s);
            UUID uuid = UUID.fromString(s);

            if (!Bukkit.getOfflinePlayer(uuid).isOnline()) {
                remove(s);
                continue;
            }

            Player rider = Bukkit.getPlayer(uuid);

            if (ent == null || ent.isDead() || ent.getPassenger() == null || !ent.getPassenger().getUniqueId().toString().equals(s)) {
                remove(s);
                continue;
            }
            Block target = rider.getTargetBlock(Sets.newHashSet(Material.AIR), 4);

            double y = -0.5;

            if (target.getType() != null && target.getType() != Material.AIR && ent.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
                y = 1.001;
            }

            ent.setVelocity(new Vector(0, 0, 0));
            ent.setVelocity(rider.getLocation().getDirection().multiply(0.6).setY(y));

            ent.setFallDistance(0);

            if (y > 1) {
                rider.setFallDistance(0);
            }
        }
    }
}
