package com.minegusta.mgracesredone.tasks;

import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.ShadowInvisibility;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
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

        //make everyone visible
        for(String s : ShadowInvisibility.values())
        {
            for(Player p : Bukkit.getOnlinePlayers())
            {
                p.showPlayer(Bukkit.getPlayer(UUID.fromString(s)));
            }
        }
    }

    private static void boost()
    {
        for(String s : ShadowInvisibility.values())
        {
            Location l = Bukkit.getPlayer(UUID.fromString(s)).getLocation();
            l.getWorld().spigot().playEffect(l, Effect.LARGE_SMOKE, 0, 0, 1,0,1, 1/10, 20, 30);
        }

        count++;

        if(count % 4 == 0)
        {
            for(String s : ShadowInvisibility.values())
            {
                ShadowInvisibility.add(s, ShadowInvisibility.getRaining(s) - 1);
                if(ShadowInvisibility.getRaining(s) < 1)
                {
                    ShadowInvisibility.remove(s);
                    ChatUtil.sendString(Bukkit.getPlayer(UUID.fromString(s)), "Your invisibility has ran out!");
                }
            }
        }

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
