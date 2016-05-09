package com.minegusta.mgracesredone.races.skilltree.abilities.perks.vampire;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.PotionUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.Optional;

public class Thrall implements IAbility {
	@Override
	public void run(Event event) {

	}

	@Override
	public boolean run(Player player) {
		MGPlayer mgp = Races.getMGPlayer(player);
		int level = mgp.getAbilityLevel(getType());

		//Spawn the thralls
		Zombie z = (Zombie) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
		z.setCanPickupItems(false);
		z.setCustomName(ChatColor.RED + "Vampire's Thrall");
		z.setCustomNameVisible(true);
		z.setBaby(false);

		PotionUtil.updatePotion(z, PotionEffectType.DAMAGE_RESISTANCE, 1, 600);
		PotionUtil.updatePotion(z, PotionEffectType.INCREASE_DAMAGE, 0, 600);
		PotionUtil.updatePotion(z, PotionEffectType.SPEED, 0, 6);
		PotionUtil.updatePotion(z, PotionEffectType.FIRE_RESISTANCE, 0, 600);

		Optional<Player> target = player.getWorld().getPlayers().stream().filter(p -> !p.getName().equalsIgnoreCase(player.getName()) && p.getLocation().distance(player.getLocation()) < 15).findAny();

		if (target.isPresent()) ((Creature) z).setTarget(target.get());

		//Skeletons
		if (level > 1) {
			for (int i = 0; i < 2; i++) {
				Skeleton s = (Skeleton) player.getWorld().spawnEntity(player.getLocation(), EntityType.SKELETON);
				s.getEquipment().setItemInMainHand(new ItemStack(Material.IRON_SWORD));
				s.getEquipment().setItemInOffHand(new ItemStack(Material.IRON_SWORD));
				s.getEquipment().setItemInMainHandDropChance(0);
				s.getEquipment().setItemInOffHandDropChance(0);
				s.setCanPickupItems(false);
				s.setCustomName(ChatColor.RED + "Vampire's Skeletal Thrall");
				s.setCustomNameVisible(true);
				PotionUtil.updatePotion(s, PotionEffectType.SPEED, 0, 6);
				if (target.isPresent()) ((Creature) s).setTarget(target.get());
				if (level > 2) {
					PotionUtil.updatePotion(s, PotionEffectType.INCREASE_DAMAGE, 0, 600);
					PotionUtil.updatePotion(s, PotionEffectType.DAMAGE_RESISTANCE, 0, 600);
				}
			}
		}

		//Buff them more
		if (level > 2) {
			PotionUtil.updatePotion(z, PotionEffectType.DAMAGE_RESISTANCE, 2, 600);
			PotionUtil.updatePotion(z, PotionEffectType.INCREASE_DAMAGE, 1, 600);
		}

		return true;
	}

	@Override
	public String getName() {
		return "Thrall";
	}

	@Override
	public AbilityType getType() {
		return AbilityType.THRALL;
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public Material getDisplayItem() {
		return Material.DRAGON_EGG;
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
		return 3;
	}

	@Override
	public String[] getDescription(int level) {
		String[] desc;

		switch (level) {
			case 1:
				desc = new String[]{"Summon a strong zombie thrall to your aid.", "Bind to an item using /Bind."};
				break;
			case 2:
				desc = new String[]{"Also summon two skeleton thralls."};
				break;
			case 3:
				desc = new String[]{"The thralls are much stronger."};
				break;
			default:
				desc = new String[]{"This is an error!"};
				break;

		}
		return desc;
	}
}
