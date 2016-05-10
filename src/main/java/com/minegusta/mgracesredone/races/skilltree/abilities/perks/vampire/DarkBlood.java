package com.minegusta.mgracesredone.races.skilltree.abilities.perks.vampire;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.DarkBloodUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;

public class DarkBlood implements IAbility {
	@Override
	public void run(Event event) {

		if (event instanceof EntityDamageEvent) {
			EntityDamageEvent e = (EntityDamageEvent) event;
			Player p = (Player) e.getEntity();
			MGPlayer mgp = Races.getMGPlayer(p);

			if (e.getCause() == EntityDamageEvent.DamageCause.FALL && mgp.getAbilityLevel(getType()) > 1) {
				e.setCancelled(true);
			}
		}


	}

	@Override
	public boolean run(Player player) {
		MGPlayer mgp = Races.getMGPlayer(player);

		if (mgp.hasAbility(getType())) {
			if (DarkBloodUtil.toggle(player)) {
				player.sendMessage(ChatColor.GREEN + "You toggled on Dark Blood effects.");
			} else {
				player.sendMessage(ChatColor.RED + "You toggled off Dark Blood effects.");
			}
		}
		return true;
	}

	@Override
	public String getName() {
		return "Dark Blood";
	}

	@Override
	public AbilityType getType() {
		return AbilityType.DARKBLOOD;
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public Material getDisplayItem() {
		return Material.LINGERING_POTION;
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
		return true;
	}

	@Override
	public String getBindDescription() {
		return "Toggle vampiric effects at night.";
	}

	@Override
	public int getMaxLevel() {
		return 5;
	}

	@Override
	public String[] getDescription(int level) {
		String[] desc;

		switch (level) {
			case 1:
				desc = new String[]{"Ability to toggle night vision.", "Bind using /Bind."};
				break;
			case 2:
				desc = new String[]{"You no longer take fall damage."};
				break;
			case 3:
				desc = new String[]{"You gain permanent speed 1.", "Bind using /Bind."};
				break;
			case 4:
				desc = new String[]{"You get permanent jump 2.", "Bind using /Bind."};
				break;
			case 5:
				desc = new String[]{"Holding a potion in your off-hand will cause the effect", "to be applied to you, although weaker."};
				break;
			default:
				desc = new String[]{"This is an error!"};
				break;

		}
		return desc;
	}
}