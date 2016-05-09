package com.minegusta.mgracesredone.races.skilltree.abilities.perks.vampire;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.tasks.BloodLustTask;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class BloodLust implements IAbility {
	@Override
	public void run(Event event) {

	}

	@Override
	public boolean run(Player player) {
		if (player.getFoodLevel() < 2) {
			player.sendMessage(ChatColor.RED + "Your Blood Bar is too low to use BloodLust.");
			return false;
		}
		if (BloodLustTask.containsPlayer(player)) {
			BloodLustTask.removePlayer(player);
		} else BloodLustTask.addPlayer(player, Races.getMGPlayer(player).getAbilityLevel(getType()));
		return false;
	}

	@Override
	public String getName() {
		return "Blood Lust";
	}

	@Override
	public AbilityType getType() {
		return AbilityType.BLOODLUST;
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public Material getDisplayItem() {
		return Material.SKULL_ITEM;
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
		return 0;
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
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public String[] getDescription(int level) {
		String[] desc;

		switch (level) {
			case 1:
				desc = new String[]{"Activate Bloodlust mode. This uses your blood bar.", "Gives you Jump 3 and Speed 3.", "Bind using /Bind."};
				break;
			case 2:
				desc = new String[]{"Now also gives Regeneration 1."};
				break;
			case 3:
				desc = new String[]{"Now also adds a strength 2 effect."};
				break;
			default:
				desc = new String[]{"This is an error!"};
				break;

		}
		return desc;
	}
}
