package com.minegusta.mgracesredone.races;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.util.WeatherUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
						"Find a vampire and ask them to infect you.",
						"Alternatively, you can sacrifice your soul.",
						"To do this, drink a potion of cold blood.",
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
						"Humans are merely their prey."

				};
	}

	@Override
	public void passiveBoost(Player p) {
		Location loc = p.getLocation();
		WeatherUtil.BiomeType biome = WeatherUtil.getBiomeType(loc);
		MGPlayer mgp = Races.getMGPlayer(p);

		//Passive vampire boosts

	}
}
