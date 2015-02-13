package com.minegusta.mgracesredone.util;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EffectUtil
{
    public static void playParticle(UUID uuid, Effect effect, int x, int y, int z, int amount)
    {
        Player p = Bukkit.getPlayer(uuid);
        p.getWorld().spigot().playEffect(p.getLocation(), effect, 1, 1, x, y, z, 1, amount, 15);
    }

    public static void playParticle(Player p, Effect effect, int x, int y, int z, int amount)
    {
        p.getWorld().spigot().playEffect(p.getLocation(), effect, 1, 1, x, y, z, 1, amount, 15);
    }

    public static void playParticle(Entity e, Effect effect, int x, int y, int z, int amount)
    {
        e.getWorld().spigot().playEffect(e.getLocation(), effect, 1, 1, x, y, z, 1, amount, 15);
    }

    public static void playParticle(Entity e, Effect effect)
    {
        e.getWorld().spigot().playEffect(e.getLocation(), effect, 1, 1, 1, 0, 1, 1, 9, 15);
    }

    public static void playParticle(Location l, Effect effect)
    {
        l.getWorld().spigot().playEffect(l, effect, 1, 1, 0, 0, 0, 1, 9, 15);
    }

    public static void playParticle(Location l, Effect effect, int x, int y, int z, int amount)
    {
        l.getWorld().spigot().playEffect(l, effect, 1, 1, x, y, z, 1, amount, 15);
    }

    public static void playSound(Entity ent, Sound sound)
    {
        ent.getWorld().playSound(ent.getLocation(), sound, 1, 1);
    }

    public static void playSound(Entity ent, Sound sound, float volume, float pitch)
    {
        ent.getWorld().playSound(ent.getLocation(), sound, volume, pitch);
    }
}
