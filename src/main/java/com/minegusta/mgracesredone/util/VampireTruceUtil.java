package com.minegusta.mgracesredone.util;

import com.google.common.collect.Maps;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentMap;

public class VampireTruceUtil {

	private static ConcurrentMap<String, Long> players = Maps.newConcurrentMap();

	public static boolean hasTruce(Player p) {
		return !players.containsKey(p.getName().toLowerCase()) || players.get(p.getName().toLowerCase()) < System.currentTimeMillis();
	}

	public static void setAttackable(Player p, int seconds) {
		long duration = seconds * 1000;
		if (!players.containsKey(p.getName().toLowerCase()) || players.get(p.getName().toLowerCase()) < System.currentTimeMillis())
			p.sendMessage(ChatColor.RED + "You have temporarily broken your truce with monsters!");
		players.put(p.getName().toLowerCase(), duration + System.currentTimeMillis());
	}
}
