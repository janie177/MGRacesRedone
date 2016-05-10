package com.minegusta.mgracesredone.util;

import com.google.common.collect.Maps;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class WeaknessUtil {

	private static ConcurrentMap<LivingEntity, long[]> entities = Maps.newConcurrentMap();

	public static long getWeakness(LivingEntity ent) {
		if (entities.containsKey(ent)) return entities.get(ent)[0];
		return 0;
	}

	public static long getExpiration(LivingEntity ent) {
		return entities.get(ent)[1];
	}

	public static void setWeakness(LivingEntity ent, long amplifier, int seconds) {
		if (ent instanceof Player && !entities.containsKey(ent)) ent.sendMessage(ChatColor.GRAY + "You feel weaker...");
		entities.put(ent, new long[]{amplifier, System.currentTimeMillis() + seconds * 1000});

	}

	public static void removeWeakness(LivingEntity ent) {
		if (entities.containsKey(ent)) {
			entities.remove(ent);
			if (ent instanceof Player) ent.sendMessage(ChatColor.GRAY + "You no longer feel weakened.");
		}
	}

	public static Set<LivingEntity> getEntities() {
		return entities.keySet();
	}


}
