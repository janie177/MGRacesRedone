package com.minegusta.mgracesredone.tasks;

import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.enderborn.EnderShield;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ShieldTask
{
    private static int id = -1;

    public static void start()
    {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                effects();
            }
        }, 5, 3);
    }

    public static void stop()
    {
        if(id != -1)
        {
            Bukkit.getScheduler().cancelTask(id);
        }
    }


    private static int rotationAngle = 0;

    private static void effects()
    {
        rotationAngle = rotationAngle + 6;
        if(rotationAngle >= 360) rotationAngle = 0;

        for(String s : EnderShield.shields.keySet())
        {
            int amount = EnderShield.shields.get(s);
            Player p = Bukkit.getPlayer(UUID.fromString(s));
            if(!p.isOnline())
            {
                EnderShield.shields.remove(s);
                continue;
            }

            Location l = p.getLocation();
            playEffect(calculateCircle(l, rotationAngle));
            if(amount > 1)playEffect(calculateCircle(l, rotationAngle + 180));
        }
    }

    private static void playEffect(Location l)
    {
        l.getWorld().spigot().playEffect(l, Effect.WITCH_MAGIC, 0, 0, 0, 0, 0, 1/20,1, 25);
    }

    private static Location calculateCircle(Location l, int angle)
    {
        double x = l.getX();
        double y = l.getY();
        double z = l.getZ();
        double radius = 1.4;

        return new Location(l.getWorld(), x + radius * Math.sin(angle), y , z + radius * Math.cos(angle));
    }
}
