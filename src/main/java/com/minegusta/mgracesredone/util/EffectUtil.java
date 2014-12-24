package com.minegusta.mgracesredone.util;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
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

    public static void playParticle(LivingEntity e, Effect effect, int x, int y, int z, int amount)
    {
        e.getWorld().spigot().playEffect(e.getLocation(), effect, 1, 1, x, y, z, 1, amount, 15);
    }

    public static void playSound(Player p, Sound sound)
    {
        p.getWorld().playSound(p.getLocation(), sound, 1, 1);
    }

    public static void playSound(Player p, Sound sound, float volume, float pitch)
    {
        p.getWorld().playSound(p.getLocation(), sound, volume, pitch);
    }
}
