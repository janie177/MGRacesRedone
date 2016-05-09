package com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class KhionesBlessing implements IAbility {
	@Override
	public void run(Event event) {

	}

	@Override
	public boolean run(Player player) {

		if (!WGUtil.canBuild(player)) {
			player.sendMessage(ChatColor.RED + "You cannot use that here.");
			return false;
		}
		MGPlayer mgp = Races.getMGPlayer(player);
		int level = mgp.getAbilityLevel(getType());

		int duration = 3;
		boolean buffs = false;
		int regenLevel = 0;

		if (level > 1) {
			duration = 5;
			regenLevel = 1;
		}
		if (level > 2) {
			buffs = true;
		}

		//Potion effect
		PotionUtil.updatePotion(player, PotionEffectType.REGENERATION, regenLevel, duration);

		//Setting the blocks and effects
		List<Location> blocks = Lists.newArrayList();

		Location l = player.getLocation();

		for (int x = -4; x <= 4; x++) {
			for (int y = -4; y <= 4; y++) {
				for (int z = -4; z <= 4; z++) {
					Location loc = new Location(l.getWorld(), l.getX() + x, l.getY() + y, l.getZ() + z);
					double distance = loc.distance(l);
					if (distance > 2 && distance <= 3 && loc.getBlock().getType() == Material.AIR) {
						loc.getBlock().setType(Material.PACKED_ICE);
						blocks.add(loc);
					}
				}
			}
		}


		//Undoing it
		final boolean doBuffs = buffs;
		final Player p = player;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () ->
		{
			blocks.stream().filter(l2 -> l2.getBlock().getType() == Material.PACKED_ICE).forEach(l2 -> l2.getBlock().setType(Material.AIR));
			if (doBuffs && p.isOnline()) {
				PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 1, 6);
				PotionUtil.updatePotion(p, PotionEffectType.SPEED, 1, 6);
			}
		}, 20 * duration);

		return true;
	}

	@Override
	public String getName() {
		return "Khione's Blessing";
	}

	@Override
	public AbilityType getType() {
		return AbilityType.KHIONESBLESSING;
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public Material getDisplayItem() {
		return Material.FROSTED_ICE;
	}

	@Override
	public int getPrice(int level) {
		return 2;
	}

	@Override
	public AbilityGroup getGroup() {
		return AbilityGroup.ACTIVE;
	}

	@Override
	public int getCooldown(int level) {
		return 80;
	}

	@Override
	public List<RaceType> getRaces() {
		return Lists.newArrayList(RaceType.AURORA);
	}

	@Override
	public boolean canBind() {
		return true;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public String[] getDescription(int level) {
		String[] desc;

		switch (level) {
			case 1:
				desc = new String[]{"Encase yourself in a shell of ice.", "Regenerates health and lasts for 3 seconds.", "Bind to an item using /Bind."};
				break;
			case 2:
				desc = new String[]{"Regeneration is stronger and lasts 5 seconds."};
				break;
			case 3:
				desc = new String[]{"When your shell disappears you gain temporary speed and strength buffs."};
				break;
			default:
				desc = new String[]{"This is an error!"};
				break;

		}
		return desc;
	}
}
