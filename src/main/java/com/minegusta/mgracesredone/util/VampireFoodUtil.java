package com.minegusta.mgracesredone.util;

import com.google.common.collect.Maps;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentMap;

public class VampireFoodUtil {

	private static ConcurrentMap<String, Long> foodAllowed = Maps.newConcurrentMap();

	public static void setCanChangeFood(Player p) {
		setCanChangeFood(p, 500);
	}

	public static void setCanChangeFood(Player p, long milliseconds) {
		foodAllowed.put(p.getName().toLowerCase(), System.currentTimeMillis() + milliseconds);
	}

	public static boolean getCanChangeFood(Player p) {
		return foodAllowed.containsKey(p.getName().toLowerCase()) && foodAllowed.get(p.getName().toLowerCase()) > System.currentTimeMillis();
	}

	private static ConcurrentMap<EntityType, Integer> foodAmounts = Maps.newConcurrentMap();

	public static void init() {
		foodAmounts.clear();
		foodAmounts.put(EntityType.CHICKEN, 1);
		foodAmounts.put(EntityType.COW, 2);
		foodAmounts.put(EntityType.SHEEP, 2);
		foodAmounts.put(EntityType.PIG, 2);
		foodAmounts.put(EntityType.SQUID, 2);
		foodAmounts.put(EntityType.PLAYER, 4);
		foodAmounts.put(EntityType.SHULKER, 1);
		foodAmounts.put(EntityType.BAT, 1);
		foodAmounts.put(EntityType.GUARDIAN, 1);
		foodAmounts.put(EntityType.CAVE_SPIDER, 1);
		foodAmounts.put(EntityType.SPIDER, 2);
		foodAmounts.put(EntityType.ENDER_DRAGON, 10);
		foodAmounts.put(EntityType.WITCH, 1);
		foodAmounts.put(EntityType.VILLAGER, 4);
		foodAmounts.put(EntityType.CREEPER, 1);
		foodAmounts.put(EntityType.ENDERMAN, 2);
		foodAmounts.put(EntityType.ENDERMITE, 1);
		foodAmounts.put(EntityType.SILVERFISH, 1);
		foodAmounts.put(EntityType.HORSE, 2);
	}

	public static int getFoodForEntityType(EntityType entity) {
		if (foodAmounts.containsKey(entity)) return foodAmounts.get(entity);
		return 0;
	}
}
