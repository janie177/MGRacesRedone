package com.minegusta.mgracesredone.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {
	public static Location toLocation(String s) {
		try {
			String[] strings = s.split(",");
			String world = strings[0];
			int x = Integer.parseInt(strings[1]);
			int y = Integer.parseInt(strings[2]);
			int z = Integer.parseInt(strings[3]);

			return new Location(Bukkit.getWorld(world), x, y, z);
		} catch (Exception ignored) {
			return null;
		}
	}

	public static String toString(Location l) {
		return l.getWorld().getName() + "," + Integer.toString((int) l.getX()) + "," + Integer.toString((int) l.getY()) + "," + Integer.toString((int) l.getZ());
	}
}
