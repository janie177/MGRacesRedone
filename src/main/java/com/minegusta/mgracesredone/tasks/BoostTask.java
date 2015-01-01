package com.minegusta.mgracesredone.tasks;

import com.minegusta.mgracesredone.data.Storage;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.RaceCheck;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.swing.text.html.parser.Entity;

public class BoostTask
{
    private static int TASK = -1;
    private static int count = 0;

    public static void start()
    {
        TASK = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                boost();
            }
        }, 20, 20);
    }

    public static void stop()
    {
        if(TASK != -1)Bukkit.getScheduler().cancelTask(TASK);
    }

    private static void boost()
    {
        count++;
        for(Player p : Bukkit.getOnlinePlayers())
        {
            if(WorldCheck.isEnabled(p.getWorld()))
            {
                RaceCheck.getRace(p).passiveBoost(p);
            }
        }
        if(count > 20)
        {
            //Reset the count.
            count = 0;
            for(MGPlayer mgp : Storage.getPlayers())
            {
                if(mgp.getRaceType() == RaceType.WEREWOLF)
                {
                    for(org.bukkit.entity.Entity ent : mgp.getPlayer().getNearbyEntities(15, 15, 15))
                    {
                        if(ent instanceof Wolf)
                        {
                            ((Wolf) ent).addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 25, 0));
                            ((Wolf) ent).addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 25, 0));
                        }
                    }
                }
            }

        }
    }
}
