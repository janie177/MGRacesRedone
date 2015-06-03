package com.minegusta.mgracesredone.tasks;

import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.EffectUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.ShadowInvisibility;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class InvisibleTask
{
    private static int count = 0;
    private static int TASK = -1;

    public static void start()
    {
        TASK = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                boost();
            }
        }, 20, 5);
    }

    public static void stop()
    {
        if(TASK != -1)Bukkit.getScheduler().cancelTask(TASK);
    }

    private static void boost()
    {
        for(String s : ShadowInvisibility.values())
        {
            EffectUtil.playParticle(Bukkit.getPlayer(s).getLocation(), Effect.PARTICLE_SMOKE, 2,0,2, 30, 30);
        }

        count++;

        if(count > 7)
        {
            count = 0;

            for(String s : ShadowInvisibility.values())
            {
                Player p = Bukkit.getPlayer(UUID.fromString(s));
                int level = Races.getMGPlayer(p).getAbilityLevel(AbilityType.SHADOW);

                if(level > 3)
                {
                    PotionUtil.updatePotion(p, PotionEffectType.SPEED, 0, 5);
                    PotionUtil.updatePotion(p, PotionEffectType.JUMP, 0, 5);
                }
            }

        }
    }
}
