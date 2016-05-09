package com.minegusta.mgracesredone.races.skilltree.abilities.perks.vampire;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class Regenerate implements IAbility {
	@Override
	public void run(Event event) {
	}

	@Override
	public boolean run(Player player) {

		return false;
	}

	@Override
	public String getName() {
		return "Regenerate";
	}

	@Override
	public AbilityType getType() {
		return AbilityType.REGENERATE;
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public Material getDisplayItem() {
		return Material.RED_ROSE;
	}

	@Override
	public int getPrice(int level) {
		return 2;
	}

	@Override
	public AbilityGroup getGroup() {
		return AbilityGroup.PASSIVE;
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
		return false;
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	@Override
	public String[] getDescription(int level) {
		String[] desc;

		switch (level) {
			case 1:
				desc = new String[]{"At night, regenerate when not in combat."};
				break;
			case 2:
				desc = new String[]{"At night, regenerate twice as fast out of combat."};
				break;
			case 3:
				desc = new String[]{"At night, regenerate fastest when out of combat."};
				break;
			case 4:
				desc = new String[]{"Heal yourself, setting your blood bar to 1.", "Activated when below 4 health.", "Only works at night."};
				break;
			default:
				desc = new String[]{"This is an error!"};
				break;

		}
		return desc;
	}
}
