package com.minegusta.mgracesredone.util;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HaloBuilder
{
    public static void spawnHalo(Player p)
    {
        Location l = p.getLocation().add(0,2.2,0);
        World w = p.getWorld();

        spawnParticles(w, l);

    }

    private static void spawnParticles(World w, Location l)
    {
        w.spigot().playEffect(l.add(-0.2,0,-0.2), Effect.SLIME, 1, 1, 0, 0, 0, 5, 1, 11);
        w.spigot().playEffect(l.add(-0.2,0,0.2), Effect.SLIME, 1, 1, 0, 0, 0, 5, 1, 11);
        w.spigot().playEffect(l.add(0.2,0,-0.2), Effect.SLIME, 1, 1, 0, 0, 0, 5, 1, 11);
        w.spigot().playEffect(l.add(0.2,0,0.2), Effect.SLIME, 1, 1, 0, 0, 0, 5, 1, 11);
    }
}
