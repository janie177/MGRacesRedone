package com.minegusta.mgracesredone.util;

import com.google.common.collect.Maps;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.concurrent.ConcurrentMap;

public class Missile {
    private static ConcurrentMap<Integer, Missile> missiles = Maps.newConcurrentMap();

    private Location location;
    private int id;
    private double xSpeed;
    private double yspeed;
    private double zspeed;
    private Effect[] effects;
    private int duration;
    private int lifeTime = 0;


    private Missile(Location location, double xSpeed, double ySpeed, double zSpeed, Effect[] effects, int duration) {
        this.location = location;
        this.xSpeed = xSpeed;
        this.yspeed = ySpeed;
        this.zspeed = zSpeed;
        this.effects = effects;
        this.duration = duration;
        this.id = RandomUtil.randomNumber(9999999);
        if (missiles.containsKey(id)) id = RandomUtil.randomNumber(99999999);
        missiles.put(id, this);
    }

    public static Missile createMissile(Location location, double xSpeed, double ySpeed, double zSpeed, Effect[] effects, int duration) {
        return new Missile(location, xSpeed, ySpeed, zSpeed, effects, duration);
    }

    public static Missile createMissile(Location location, Vector velocity, Effect[] effects, int duration) {
        return new Missile(location, velocity.getX(), velocity.getY(), velocity.getZ(), effects, duration);
    }

    public static void update() {
        if (missiles.size() == 0) return;
        for (Missile m : missiles.values()) {
            m.updateLocation();
            for (Effect effect : m.getEffects()) {
                EffectUtil.playParticle(m.getLocation(), effect);
            }
            m.updateLifeTime();
            if (m.outlived()) m.stop();
        }
    }

    public void stop() {
        missiles.remove(id);
    }

    public void updateLifeTime() {
        lifeTime++;
    }

    public int getId() {
        return id;
    }

    public boolean outlived() {
        return lifeTime >= duration;
    }

    public void updateLocation() {
        Location newLocation = getLocation().add(xSpeed, yspeed, zspeed);
        setLocation(newLocation);
    }

    public Effect[] getEffects() {
        return effects;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
