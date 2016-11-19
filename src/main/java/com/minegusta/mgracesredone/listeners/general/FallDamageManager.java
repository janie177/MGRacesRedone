package com.minegusta.mgracesredone.listeners.general;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.concurrent.ConcurrentMap;

public class FallDamageManager implements Listener {
    public static ConcurrentMap<String, Long> fallMap = Maps.newConcurrentMap();

    public static void addToFallMap(Player p) {
        addToFallMap(p.getUniqueId().toString());
    }

    public static void addToFallMap(String uuid) {
        fallMap.put(uuid, System.currentTimeMillis());
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent e) {
        if (!WorldCheck.isEnabled(e.getEntity().getWorld())) return;

        if (e.getCause() != null && e.getCause() == EntityDamageEvent.DamageCause.FALL && e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (fallMap.containsKey(p.getUniqueId().toString())) {
                if (System.currentTimeMillis() - fallMap.get(p.getUniqueId().toString()) < 5000) e.setCancelled(true);
                fallMap.remove(p.getUniqueId().toString());
            }
        }
    }
}
