package com.minegusta.mgracesredone.util;

import com.minegusta.mgracesredone.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityUtil
{

    public static void bleed(final LivingEntity ent, final Entity damager, int duration)
    {
        for(int i = 0; i < duration; i++)
        {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                @Override
                public void run()
                {
                    if(ent instanceof Player) ((Player)ent).sendMessage(ChatColor.RED + "You are bleeding! *ouch*");

                    EntityDamageByEntityEvent e = new EntityDamageByEntityEvent(damager, ent, EntityDamageEvent.DamageCause.CUSTOM, 1);

                    Bukkit.getPluginManager().callEvent(e);

                    if(WGUtil.canGetDamage(ent) && !e.isCancelled())
                    {
                        ent.damage(1.0);
                        ent.setLastDamageCause(e);
                    }
                    EffectUtil.playParticle(ent, Effect.COLOURED_DUST);

                }
            }, i * 20);
        }
    }

    public static boolean isInWater(Entity ent)
    {
        Material mat = ent.getLocation().getBlock().getType();
        return mat == Material.WATER || mat == Material.STATIONARY_WATER;
    }

}
