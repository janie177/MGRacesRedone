package com.minegusta.mgracesredone.races.skilltree.abilities.perks.vampire;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.*;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class BatShield implements IAbility {
	@Override
	public void run(Event event) {

	}

	@Override
	public boolean run(Player player) {

		MGPlayer mgp = Races.getMGPlayer(player);
		int level = mgp.getAbilityLevel(getType());

		if (!WGUtil.canPVP(player)) {
			player.sendMessage(ChatColor.RED + "You cannot use that here!");
			return false;
		}

		if (player.getFoodLevel() < 2) {
			player.sendMessage(ChatColor.RED + "You need more blood to use this.");
			return false;
		}

		//Drain 2 food.
		VampireFoodUtil.setCanChangeFood(player);
		player.setFoodLevel(player.getFoodLevel() - 2);

		PotionUtil.updatePotion(player, PotionEffectType.DAMAGE_RESISTANCE, 1, 6);

		if (level > 1) {
			PotionUtil.updatePotion(player, PotionEffectType.REGENERATION, 0, 6);
		}

		if (level > 2) {
			for (int i = 0; i < 6; i++) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () ->
				{
					if (!player.isOnline()) return;
					EffectUtil.playParticle(player, Effect.SLIME);
					player.getWorld().getLivingEntities().stream().filter(e -> e.getLocation().distance(player.getLocation()) < 3 && !e.getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())).forEach(e -> e.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 4, 0)));
				}, i * 20);
			}
		}

		int duration = 6;
		double startingStrength = 0.15;

		//Remove bats after
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () ->
		{
			if (player.isOnline()) {
				player.getWorld().getEntitiesByClass(Bat.class).stream().filter(b -> b.getLocation().distance(player.getLocation()) < 15).forEach(Bat::remove);
			}
		}, 6 * 20);

		//The bats effect
		for (int i = 0; i <= 20 * duration; i++) {
			if (i % 2 == 0) {
				final int k = i;

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
					if (!player.isOnline()) return;
					if (k % 20 == 0) {
						EffectUtil.playSound(player.getLocation(), Sound.ITEM_SHIELD_BLOCK);
					}
					EffectUtil.playParticle(player.getLocation(), Effect.PARTICLE_SMOKE, 1, 10, 1, 30, 50);
					player.getWorld().spawnEntity(player.getLocation().clone().add(RandomUtil.randomDouble(0, 6) - 3, RandomUtil.randomDouble(0, 6) - 3, RandomUtil.randomDouble(0, 6) - 3), EntityType.BAT);

					player.getLocation().getWorld().getEntitiesByClasses(Bat.class, Projectile.class).stream().
							filter(ent -> ent.getLocation().distance(player.getLocation()) <= 4).forEach(ent -> {

						double angle = Math.toRadians(14);
						double radius = Math.abs(ent.getLocation().distance(player.getLocation()));

						double x = ent.getLocation().getX() - player.getLocation().getX();
						double z = ent.getLocation().getZ() - player.getLocation().getZ();

						double dx = x * Math.cos(angle) - z * Math.sin(angle);
						double dz = x * Math.sin(angle) + z * Math.cos(angle);

						Location target1 = new Location(ent.getWorld(), dx + player.getLocation().getX(), ent.getLocation().getY(), dz + player.getLocation().getZ());

						double ix = ent.getLocation().getX() - target1.getX();
						double iz = ent.getLocation().getZ() - target1.getZ();

						Vector v = new Vector(ix, -0.2, iz);
						v.normalize();

						//The closer to the center, the stronger the force.
						double amplifier = startingStrength + 2 / radius;
						ent.setVelocity(ent.getVelocity().add(v).multiply(-amplifier));

					});
				}, i);
			}
		}

		return true;
	}

	@Override
	public String getName() {
		return "Bat Shield";
	}

	@Override
	public AbilityType getType() {
		return AbilityType.BATSHIELD;
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public Material getDisplayItem() {
		return Material.SHIELD;
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
		return 65;
	}

	@Override
	public List<RaceType> getRaces() {
		return Lists.newArrayList(RaceType.VAMPIRE);
	}

	@Override
	public boolean canBind() {
		return true;
	}

	@Override
	public String getBindDescription() {
		return "Summon bats to shield you.";
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
				desc = new String[]{"Bats will protect you from harm.", "Reduces combat damage taken.", "Lasts for 6 seconds", "Bind using /Bind."};
				break;
			case 2:
				desc = new String[]{"Now also slowly heals you."};
				break;
			case 3:
				desc = new String[]{"The bats will poison anyone standing too close."};
				break;
			default:
				desc = new String[]{"This is an error!"};
				break;

		}
		return desc;
	}
}
