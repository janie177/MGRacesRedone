package com.minegusta.mgracesredone.tasks;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.util.EffectUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.VampireFoodUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ConcurrentMap;

public class BloodLustTask {

	public static ConcurrentMap<Player, Integer> players = Maps.newConcurrentMap();
	public static int foodInterval = 0;

	private static int ID = -1;

	public static void start() {
		ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () ->
		{
			players.keySet().stream().forEach(p ->
			{
				if (p.isOnline() && WorldCheck.isEnabled(p.getWorld())) {

					int level = players.get(p);
					if (p.getFoodLevel() < 2) removePlayer(p);
					if (foodInterval > 3) {
						VampireFoodUtil.setCanChangeFood(p, 60);
						p.setFoodLevel(p.getFoodLevel() - 1);
					}

					PotionUtil.updatePotion(p, PotionEffectType.SPEED, 3, 5);
					PotionUtil.updatePotion(p, PotionEffectType.JUMP, 3, 5);
					EffectUtil.playParticle(p, Effect.LARGE_SMOKE);
					EffectUtil.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF);
					if (level > 1) p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 0, 0));

					if (level > 2) {
						PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 1, 5);
					}
				} else {
					removePlayer(p);
				}
			});

			foodInterval++;
			foodInterval = foodInterval > 4 ? 0 : foodInterval;
		}, 20, 10);
	}

	public static void stop() {
		if (ID != -1) {
			Bukkit.getScheduler().cancelTask(ID);
		}
	}

	public static void removePlayer(Player p) {
		if (players.containsKey(p)) players.remove(p);
		p.sendMessage(ChatColor.RED + "BloodLust is no longer active.");
		PotionUtil.updatePotion(p, PotionEffectType.SPEED, 4, 0);
		PotionUtil.updatePotion(p, PotionEffectType.JUMP, 4, 0);
		PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 4, 0);
	}

	public static boolean containsPlayer(Player p) {
		return players.containsKey(p);
	}

	public static void addPlayer(Player p, int level) {
		players.put(p, level);
		p.sendMessage(ChatColor.DARK_RED + "BloodLust is now active.");
	}
}
