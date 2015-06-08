package com.minegusta.mgracesredone.util;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Main;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class EnderRiftPortal {

    public static ConcurrentMap<String, EnderRiftPortal> portals = Maps.newConcurrentMap();

    public ConcurrentMap<String, Long> teleported = Maps.newConcurrentMap();

    private String uuid;
    private Location location1;
    private Location location2;
    private int duration;
    private boolean altEntities;

    private EnderRiftPortal(String uuid, Location location1, Location location2, int duration, boolean altEntities) {
        this.uuid = uuid;
        this.location1 = location1;
        this.location2 = location2;
        this.duration = duration;
        this.altEntities = altEntities;
    }


    //Static methods

    public static void create(String uuid, Location location1, Location location2, int duration, boolean altEntities) {
        portals.put(uuid, new EnderRiftPortal(uuid, location1, location2, duration, altEntities));
    }

    public static boolean remove(String uuid) {
        if (contains(uuid)) {
            portals.remove(uuid);
            return true;
        }
        return false;
    }

    public static boolean contains(String uuid) {
        return portals.containsKey(uuid);
    }

    public static Location getLocation2(String uuid) {
        return portals.get(uuid).getLocation2();
    }

    public static Location getLocation1(String uuid) {
        return portals.get(uuid).getLocation1();
    }

    public static void setLocation1(String uuid, Location location) {
        portals.get(uuid).setLocation1(location);
        EffectUtil.playParticle(location, Effect.ENDER_SIGNAL, 0, 0, 0, 1, 1, 40);
    }

    public static void setAltEntities(String uuid, Boolean altEntities) {
        portals.get(uuid).setAltEntities(altEntities);
    }

    public static boolean getAltEntities(String uuid) {
        return portals.get(uuid).getAltEntities();
    }

    public static void setLocation2(String uuid, Location location) {
        portals.get(uuid).setLocation2(location);
        EffectUtil.playParticle(location, Effect.ENDER_SIGNAL, 0, 0, 0, 1, 1, 40);
    }

    public static boolean portalsSet(String uuid) {
        return contains(uuid) && getLocation1(uuid) != null && getLocation2(uuid) != null;
    }

    public static void setDuration(String uuid, int duration) {
        portals.get(uuid).setDuration(duration);
    }

    public static int getDuration(String uuid) {
        return portals.get(uuid).getDuration();
    }

    public static void start(String uuid) {
        portals.get(uuid).start();
    }


    //Local methods

    private Location getLocation1() {
        return location1;
    }

    private Location getLocation2() {
        return location2;
    }

    private void setLocation1(Location l) {
        this.location1 = l;
    }

    private void setDuration(int duration) {
        this.duration = duration;
    }

    private int getDuration() {
        return duration;
    }

    private void setAltEntities(boolean b) {
        this.altEntities = b;
    }

    private void setLocation2(Location l) {
        this.location2 = l;
    }

    private boolean getAltEntities() {
        return altEntities;
    }

    private String getUUID() {
        return uuid;
    }

    private void addTeleported(String uuid) {
        teleported.put(uuid, System.currentTimeMillis());
    }

    private void removeTeleported(String uuid) {
        if (getIfTeleported(uuid)) teleported.remove(uuid);
    }

    private boolean getIfTeleported(String uuid) {
        return teleported.containsKey(uuid);
    }

    private boolean getTeleportCooledDown(String uuid) {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - teleported.get(uuid)) > 1.4;
    }

    private void start() {
        int ticks = duration * 20;

        for (int i = 0; i <= ticks; i++) {
            if (i % 8 == 0) {

                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run() {
                        effect(location1);
                        effect(location2);
                        teleport(location1, location2);
                        teleport(location2, location1);
                    }
                }, i);

            }
        }
    }

    private static final Effect[] effects = {Effect.PORTAL};

    private void effect(Location l) {
        EffectUtil.playParticle(l, Effect.CLOUD, 0, 0, 0, 1 / 30, 6, 30);
        EffectUtil.playParticle(l, Effect.LARGE_SMOKE, 0, 0, 0, 1 / 10, 3, 30);
        EffectUtil.playParticle(l, Effect.FIREWORKS_SPARK, 0, 1, 0, 1 / 10, 3, 30);
        EffectUtil.playSound(l, Sound.PORTAL_TRAVEL, 3, 4);

        Location newLocation = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());

        Missile.createMissile(newLocation, 0, 0.01, 0, effects, 4);
    }

    private void teleport(Location l1, Location l2) {
        if (l2.getBlock().getType() != Material.AIR || l2.getBlock().getRelative(BlockFace.UP).getType() != Material.AIR) {
            //Obsctructed teleport location.
            return;
        }


        //Void
        double highestY = l2.getWorld().getHighestBlockAt(l2).getY();

        if (l2.getWorld().getBlockAt((int) l2.getX(), 0, (int) l2.getZ()).getType() == Material.AIR && (highestY == 0.0 || highestY > l2.getY())) {
            return;
        }
        if (l1.getWorld() != l2.getWorld() || l1.distance(l2) > 40) {
            return;
        }

        Entity dummy = l1.getWorld().spawnEntity(l1, EntityType.ENDER_SIGNAL);

        for (Entity ent : dummy.getNearbyEntities(1, 1, 1)) {
            String id = ent.getUniqueId().toString();
            if (getIfTeleported(id)) {
                if (getTeleportCooledDown(id)) teleported.remove(id);
                else continue;
            }

            if (ent instanceof Player) {
                if (!ent.getUniqueId().toString().equals(uuid)) {
                    continue;
                }
                ent.teleport(l2);
                EffectUtil.playParticle(ent, Effect.CLOUD);
                EffectUtil.playSound(ent, Sound.PORTAL_TRIGGER);
                addTeleported(id);
            } else if (altEntities && (ent instanceof LivingEntity || ent instanceof Item || ent instanceof Projectile)) {
                ent.teleport(l2);
                EffectUtil.playParticle(ent, Effect.CLOUD);
                EffectUtil.playSound(ent, Sound.PORTAL_TRIGGER);
                addTeleported(id);
            }
        }

        dummy.remove();
    }
}
