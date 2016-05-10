package com.minegusta.mgracesredone.races;

import com.minegusta.mgessentials.MGEssentialsPlugin;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

public class Vampire implements Race {
	@Override
	public double getHealth() {
		return 20;
	}

	@Override
	public String getName() {
		return "Vampire";
	}

	@Override
	public String[] getInfectionInfo() {
		return new String[]
				{
						"To become Vampire, follow these steps:",
						"Drink a potion of cold blood.",
						"The potion can be obtained by offering",
						"Blood Essence to a vampire altar at night.",
						"A vampire altar needs to contain 4 Gold Blocks",
						"and 10 Obsidian.",
						"Right click the gold blocks at night with",
						"3 Blood Essence from mobs in your inventory,",
						"and 1 player Blood Essence as well.",
						"Blood Essence is dropped by killing creatures and players."

				};
	}

	@Override
	public String getColoredName() {
		return ChatColor.DARK_RED + getName();
	}

	@Override
	public int getPerkPointCap() {
		return 26;
	}

	@Override
	public String[] getInfo() {
		return new String[]
				{
						"Vampires are undead humans.",
						"They cannot survive in the sunlight, but are powerful at night.",
						"Perks are magic and darkness based.",
						"Vampires blend in well with humans, as they have much in common.",
						"They are weak to Smite and wooden weapons.",
						"Vampires have a blood bar instead of food bar.",
						"It only drains when using abilities.",
						"Fill it by hitting living beings with your hands."

				};
	}

	int interval = 0;
	@Override
	public void passiveBoost(Player p) {
		MGPlayer mgp = Races.getMGPlayer(p);

		//Passive vampire boosts

		//Burn in the sun
		if (WeatherUtil.isOverWorld(p.getLocation()) && !WeatherUtil.isNight(p.getWorld()) && PlayerUtil.isInOpenAir(p) && !WeatherUtil.isRaining(p.getWorld())) {
			p.setFireTicks(80);
			PotionUtil.updatePotion(p, PotionEffectType.CONFUSION, 0, 12);
			PotionUtil.updatePotion(p, PotionEffectType.BLINDNESS, 0, 8);
			PotionUtil.updatePotion(p, PotionEffectType.SLOW, 0, 8);
			PotionUtil.updatePotion(p, PotionEffectType.WEAKNESS, 3, 8);
			EffectUtil.playParticle(p, Effect.MOBSPAWNER_FLAMES);
		}

		interval++;

		int regenLevel = mgp.getAbilityLevel(AbilityType.REGENERATE);

		//Regenerate from REGENERATE Ability when NOT in combat
		if (interval > 2) {
			interval = 0;
			if (WeatherUtil.isNight(p.getWorld()) && regenLevel > 0 && !p.isDead() && !MGEssentialsPlugin.inCombat(p)) {
				regenLevel = regenLevel > 3 ? 3 : regenLevel;
				double max = p.getMaxHealth() - p.getHealth();
				if (max >= regenLevel) {
					p.setHealth(p.getHealth() + regenLevel);
				}
			}
		}

		//DarkBlood
		int darkbloodLevel;
		if ((darkbloodLevel = mgp.getAbilityLevel(AbilityType.DARKBLOOD)) > 0) {
			if (DarkBloodUtil.isToggledOn(p)) {
				//level 1
				PotionUtil.updatePotion(p, PotionEffectType.NIGHT_VISION, 0, 20);

				//level 3
				if (darkbloodLevel > 2) {
					PotionUtil.updatePotion(p, PotionEffectType.SPEED, 0, 5);
				}

				//level 4
				if (darkbloodLevel > 3) {
					PotionUtil.updatePotion(p, PotionEffectType.JUMP, 1, 5);
				}
			}

			//level 5, potions
			Material hand = p.getInventory().getItemInOffHand().getType();
			if (darkbloodLevel > 4 && (hand == Material.POTION || hand == Material.SPLASH_POTION || hand == Material.LINGERING_POTION)) {
				PotionMeta meta = (PotionMeta) p.getInventory().getItemInOffHand().getItemMeta();
				PotionEffectType type = meta.getBasePotionData().getType().getEffectType();
				if (type == PotionEffectType.HEAL || type == PotionEffectType.HEALTH_BOOST)
					type = PotionEffectType.REGENERATION;
				if (PotionUtil.isPositiveForPlayer(type)) PotionUtil.updatePotion(p, type, 0, 5);
			}

		}
	}
}
