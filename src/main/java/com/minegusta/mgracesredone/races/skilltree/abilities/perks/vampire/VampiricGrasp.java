package com.minegusta.mgracesredone.races.skilltree.abilities.perks.vampire;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.EntityDamageByEntityEventUtil;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;
import java.util.Optional;

public class VampiricGrasp implements IAbility {
	@Override
	public void run(Event event) {

	}

	@Override
	public boolean run(Player player) {

		MGPlayer mgp = Races.getMGPlayer(player);
		int level = mgp.getAbilityLevel(getType());
		int duration = 5;

		if (!WGUtil.canGetDamage(player) || !WGUtil.canPVP(player)) {
			player.sendMessage(ChatColor.RED + "You cannot use that ability here.");
			return false;
		}

		if (level < 4) {


			Location targetLocation = player.getTargetBlock(Sets.newHashSet(Material.AIR), 10).getRelative(0, 2, 0).getLocation();
			Optional<LivingEntity> target = player.getWorld().getLivingEntities().stream().filter(ent -> ent.getLocation().distance(targetLocation) < 4 && !ent.getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())).findFirst();
			if (!target.isPresent()) {
				player.sendMessage(ChatColor.RED + "No living being could be found.");
				return false;
			} else if (!WGUtil.canFightEachother(player, target.get())) {
				player.sendMessage(ChatColor.RED + "You cannot attack that target here.");
				return false;
			}

			int damage = level > 3 ? 3 : level;

			for (int i = 0; i < duration; i++)
			{
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () ->
				{
					if (!player.isOnline() || !target.get().isValid()) return;
					if (target.get().getLocation().distance(player.getLocation()) > 10) return;

					if (runDrain(player, target.get(), damage)) {
						player.sendMessage(ChatColor.DARK_RED + "You absorb your targets life force...");
					}
				}, 20 * i);
			}


		} else {
			List<LivingEntity> targets = Lists.newArrayList();
			player.getWorld().getLivingEntities().stream().filter(ent -> ent.getLocation().distance(player.getLocation()) < 11 && !ent.getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())).forEach(targets::add);
			if (targets.isEmpty()) {
				player.sendMessage(ChatColor.RED + "No living being could be found.");
				return false;
			} else if (!WGUtil.canPVP(player)) {
				player.sendMessage(ChatColor.RED + "You cannot attack that target here.");
				return false;
			}

			int damage = level > 3 ? 3 : level;

			for (int i = 0; i < duration; i++)
			{
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () ->
				{
					targets.stream().filter(LivingEntity::isValid).filter(ent -> ent.getLocation().distance(player.getLocation()) < 11).forEach(ent ->
					{
						runDrain(player, ent, damage);
					});
					player.sendMessage(ChatColor.DARK_RED + "You absorb your targets life force...");
				}, 20 * i);
			}
		}


		return true;
	}

	private boolean runDrain(Player attacker, LivingEntity target, double damage) {
		if (!target.isValid()) return false;
		if (target.getLocation().distance(attacker.getLocation()) > 10) return false;
		EntityDamageByEntityEvent e = EntityDamageByEntityEventUtil.createEvent(attacker, target, EntityDamageEvent.DamageCause.CUSTOM, damage);
		Bukkit.getPluginManager().callEvent(e);

		if (!e.isCancelled()) {

			//Make the particles
			double yLength = (attacker.getLocation().getY() - target.getLocation().getY()) / 10;
			double xLength = (attacker.getLocation().getX() - target.getLocation().getX()) / 10;
			double zLength = (attacker.getLocation().getZ() - target.getLocation().getZ()) / 10;

			for (int i = 1; i < 11; i++) {
				//Red dust.
				target.getWorld().spigot().playEffect(target.getLocation().clone().add(xLength * i, (yLength * i) + 1, zLength * i), Effect.COLOURED_DUST, 0, 0, 255, 0, 0, 1, 0, 30);
			}

			//Damage the target
			target.damage(damage);
			//Heal the attacker
			attacker.setHealth(attacker.getHealth() + (attacker.getHealth() < attacker.getMaxHealth() ? 1 : 0));

			if (target instanceof Player) {
				((Player) target).sendMessage(ChatColor.RED + "Your blood is being drained!");
			}
			return true;
		}

		return false;
	}

	@Override
	public String getName() {
		return "Vampiric Grasp";
	}

	@Override
	public AbilityType getType() {
		return AbilityType.VAMPIRICGRASP;
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public Material getDisplayItem() {
		return Material.BLAZE_ROD;
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
		return 60;
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
		return 4;
	}

	@Override
	public String[] getDescription(int level) {
		String[] desc;

		switch (level) {
			case 1:
				desc = new String[]{"Aim at a living being to start draining their blood.", "Fills your blood and health.", "Has a reach of 10 blocks and lasts 5 seconds.", "Bind using /Bind."};
				break;
			case 2:
				desc = new String[]{"Damage increased to 2."};
				break;
			case 3:
				desc = new String[]{"Damage increased to 3."};
				break;
			case 4:
				desc = new String[]{"Drain any entity in a radius of 10 around you."};
				break;
			default:
				desc = new String[]{"This is an error!"};
				break;
		}
		return desc;
	}
}
