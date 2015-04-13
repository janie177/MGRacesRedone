package com.minegusta.mgracesredone.tasks;

import com.minegusta.mgracesredone.data.Storage;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffectType;

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
                Races.getRace(p).passiveBoost(p);
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
                        if(ent instanceof Wolf && ((Wolf)ent).isTamed() && ((Wolf)ent).getOwner().getUniqueId().equals(mgp.getUniqueId()))
                        {
                            PotionUtil.updatePotion((Wolf) ent, PotionEffectType.INCREASE_DAMAGE, 1, 25);
                            PotionUtil.updatePotion((Wolf) ent, PotionEffectType.DAMAGE_RESISTANCE, 0, 25);
                        }
                    }
                }
            }

        }
    }
}
