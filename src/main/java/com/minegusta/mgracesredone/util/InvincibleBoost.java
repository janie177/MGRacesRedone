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

    public InvincibleBoost(Player p)
    {
        this.p = p;
        this.uuid = p.getUniqueId().toString();
        start();
    }

    public void start()
    {
        for(int i = 0; i < 9; i++)
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
                    p.setHealth(1);
                    EffectUtil.playSound(p, Sound.WITHER_DEATH);
                    EffectUtil.playParticle(p, Effect.CLOUD);
                }
            }
        }, 20 * 8);

    }
}
