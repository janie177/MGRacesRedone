package com.minegusta.mgracesredone.tasks;

import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.util.WeaknessUtil;
import org.bukkit.Bukkit;
import org.bukkit.Effect;

public class WeaknessTask {

    private static int ID = -1;

    public static void start() {
        ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () ->
        {
            WeaknessUtil.getEntities().stream().forEach(ent ->
            {
                //Expired effect
                if (!ent.isValid() || !ent.getLocation().getChunk().isLoaded() || WeaknessUtil.getExpiration(ent) < System.currentTimeMillis()) {
                    WeaknessUtil.removeWeakness(ent);
                }
                //Apply the weakness effect.
                else {
                    ent.getWorld().spigot().playEffect(ent.getLocation().clone().add(0, 1, 0), Effect.POTION_SWIRL_TRANSPARENT, 0, 0, 1, 1, 1, 0, 9, 30);
                }
            });
        }, 20, 20);
    }

    public static void stop() {
        if (ID != -1) Bukkit.getScheduler().cancelTask(ID);
    }
}
