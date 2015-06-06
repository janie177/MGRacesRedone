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

public class EntityUtil {

    public static void bleed(final LivingEntity ent, final Entity damager, int duration) {
        for (int i = 0; i < duration; i++) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
                if (ent instanceof Player) ent.sendMessage(ChatColor.RED + "You are bleeding! *ouch*");

                EntityDamageByEntityEvent e = new EntityDamageByEntityEvent(damager, ent, EntityDamageEvent.DamageCause.CUSTOM, 1);

                Bukkit.getPluginManager().callEvent(e);

                if (WGUtil.canGetDamage(ent) && !e.isCancelled()) {
                    ent.damage(e.getDamage());
                    ent.setLastDamageCause(e);
                }
                EffectUtil.playParticle(ent, Effect.CRIT);

            }, i * 20);
        }
    }

    public static boolean isInWater(Entity ent) {
        Material mat = ent.getLocation().getBlock().getType();
        return mat == Material.WATER || mat == Material.STATIONARY_WATER;
    }

}
