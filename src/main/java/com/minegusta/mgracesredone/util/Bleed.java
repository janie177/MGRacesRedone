package com.minegusta.mgracesredone.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Bleed {
    private Entity damager;
    private LivingEntity target;
    private int duration;

    public Bleed(Entity damager, LivingEntity target, int duration) {
        this.damager = damager;
        this.target = target;
        this.duration = duration;
    }

    public boolean bleed() {
        if (target == null || damager == null || target.isDead()) return false;
        if (damager instanceof Player && !Bukkit.getOfflinePlayer(damager.getUniqueId()).isOnline()) {
            return false;
        }

        EntityDamageByEntityEvent e = new EntityDamageByEntityEvent(damager, target, EntityDamageEvent.DamageCause.CUSTOM, 1);

        Bukkit.getPluginManager().callEvent(e);

        if (WGUtil.canGetDamage(target) && !e.isCancelled()) {
            target.damage(e.getFinalDamage());
            target.setLastDamageCause(e);
            if (target instanceof Player) {
                target.sendMessage(ChatColor.DARK_RED + "You are bleeding..");
            }
        }
        EffectUtil.playParticle(target, Effect.CRIT);

        duration--;

        return duration > 0;
    }

}
