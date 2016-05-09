package com.minegusta.mgracesredone.util;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ConcurrentMap;

public class DarkBloodUtil {
	private static ConcurrentMap<String, Boolean> players = Maps.newConcurrentMap();

	public static boolean isToggledOn(Player p) {
		return players.containsKey(p.getName().toLowerCase()) && players.get(p.getName().toLowerCase());
	}

	public static void setForPlayer(Player p, boolean enabled) {
		players.put(p.getName().toLowerCase(), enabled);
		if (!enabled) {
			//Set the night vision to 1 second remaining so it will go off right after toggling, rather than having it stay on for another 20 seconds.
			PotionUtil.updatePotion(p, PotionEffectType.NIGHT_VISION, 0, 1);
		}
	}

	public static boolean toggle(Player p) {
		if (isToggledOn(p)) {
			setForPlayer(p, false);
			return false;
		} else {
			setForPlayer(p, true);
			return true;
		}
	}
}
