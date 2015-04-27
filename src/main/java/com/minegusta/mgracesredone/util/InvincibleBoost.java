package com.minegusta.mgracesredone.util;

import com.minegusta.mgracesredone.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class InvincibleBoost
{
    private Player p;
    private String uuid;
    private int endHealth;
    private int seconds;

    public InvincibleBoost(Player p, int seconds, int endHealth)
    {
        this.p = p;
        this.endHealth = endHealth;
        this.seconds = seconds;
        this.uuid = p.getUniqueId().toString();
        start();
    }

    public void start()
    {
        for(int i = 0; i < seconds; i++)
        {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                @Override
                public void run()
                {
                    if(p.isOnline())
                    {
                        EffectUtil.playParticle(p, Effect.VILLAGER_THUNDERCLOUD);
                        EffectUtil.playSound(p, Sound.ANVIL_LAND);
                    }
                }
            }, 20 * i);
        }



        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run()
            {
                AngelInvincibility.invincibleMap.remove(uuid);
                if(p.isOnline())
                {
                    AngelInvincibility.remove(uuid);
                    p.setHealth(endHealth);
                    EffectUtil.playSound(p, Sound.WITHER_DEATH);
                    EffectUtil.playParticle(p, Effect.CLOUD);
                }
            }
        }, 20 * seconds);

    }
}
