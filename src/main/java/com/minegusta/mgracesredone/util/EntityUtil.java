package com.minegusta.mgracesredone.util;

import com.minegusta.mgracesredone.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class EntityUtil
{

    public static void bleed(final LivingEntity ent, int duration)
    {
        for(int i = 0; i < duration; i++)
        {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                @Override
                public void run()
                {
                    if(ent instanceof Player) ((Player)ent).sendMessage(ChatColor.RED + "You are bleeding! *ouch*");
                    if(WGUtil.canGetDamage(ent)) ent.damage(2.0);
                    EffectUtil.playParticle(ent, Effect.COLOURED_DUST);

                }
            }, i * 20);
        }
    }
}
