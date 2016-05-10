package com.minegusta.mgracesredone.listeners.racelisteners;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VampireListener implements Listener {

	private static final ItemStack monsterBloodEssence = new ItemStack(Material.INK_SACK, 1, (short) 1) {
		{
			ItemMeta meta = getItemMeta();
			meta.setLore(Lists.newArrayList(ChatColor.RED + "Monster Blood Essence"));
			meta.setDisplayName(ChatColor.RED + "Monster Blood Essence");
			setItemMeta(meta);
		}
	};

	private static final ItemStack humanBloodEssence = new ItemStack(Material.INK_SACK, 1, (short) 1) {
		{
			ItemMeta meta = getItemMeta();
			meta.setLore(Lists.newArrayList(ChatColor.RED + "Human Blood Essence"));
			meta.setDisplayName(ChatColor.RED + "Human Blood Essence");
			setItemMeta(meta);
		}
	};

	//Drop blood essence
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (!WorldCheck.isEnabled(e.getEntity().getWorld())) {
			return;
		}

		if (e.getEntity() instanceof Player && RandomUtil.chance(25)) {
			//Drop item
			e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), humanBloodEssence);

		} else if (e.getEntity() instanceof Monster && RandomUtil.chance(5)) {
			//Drop item
			e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), monsterBloodEssence);
		}
	}

	//Vampires can not eat
	@EventHandler
	public void onEat(PlayerItemConsumeEvent e) {
		if (!WorldCheck.isEnabled(e.getPlayer().getWorld()) || e.isCancelled()) return;

		Player p = e.getPlayer();
		MGPlayer mgp = Races.getMGPlayer(p);

		if (mgp.isRace(RaceType.VAMPIRE) && e.getItem().getType() != Material.POTION && e.getItem().getType() != Material.MILK_BUCKET) {
			e.setCancelled(true);
		}
	}

	//Do not remove food over time.
	@EventHandler()
	public void onFoodDrain(FoodLevelChangeEvent e) {
		if (!(e.getEntity() instanceof Player) || !WorldCheck.isEnabled(e.getEntity().getWorld()) || e.isCancelled()) {
			return;
		}

		Player p = (Player) e.getEntity();
		MGPlayer mgp = Races.getMGPlayer(p);

		if (mgp.isRace(RaceType.VAMPIRE) && !VampireFoodUtil.getCanChangeFood(p)) {
			e.setCancelled(true);
		}
	}

	//Vampires get food from slapping people with their pimp hand
	@EventHandler
	public void onPimpSlap(EntityDamageByEntityEvent e) {
		if (!WorldCheck.isEnabled(e.getEntity().getWorld()) || e.isCancelled()) return;
		if (e.getEntity() instanceof LivingEntity && e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			MGPlayer mgp = Races.getMGPlayer(p);

			int foodAmount;
			if (mgp.isRace(RaceType.VAMPIRE) && (foodAmount = VampireFoodUtil.getFoodForEntityType(e.getEntityType())) > 0) {

				if (p.getInventory().getItemInMainHand().getType() != Material.AIR) {
					return;
				}

				foodAmount = (int) e.getDamage() * foodAmount;
				foodAmount = foodAmount == 0 ? 1 : foodAmount;

				int maxHealed = 20 - p.getFoodLevel();
				if (maxHealed > 0) {
					VampireFoodUtil.setCanChangeFood(p);
					EffectUtil.playParticle(e.getEntity().getLocation(), Effect.CRIT);
					EffectUtil.playSound(e.getEntity().getLocation(), Sound.BLOCK_WATER_AMBIENT);
					foodAmount = maxHealed < foodAmount ? maxHealed : foodAmount;
					p.setFoodLevel(p.getFoodLevel() + foodAmount);
					p.sendMessage(ChatColor.RED + "You feed off the living being...");
				}
			}
		}
	}

	//Vampire wood sword and smite weakness, also monster mash even passing on
	@EventHandler
	public void onVampireDamage(EntityDamageByEntityEvent e) {
		if (!WorldCheck.isEnabled(e.getEntity().getWorld()) || e.isCancelled()) {
			return;
		}



		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			MGPlayer mgp = Races.getMGPlayer(p);


			//Blood sacrifice
			int regenLevel = mgp.getAbilityLevel(AbilityType.REGENERATE);
			if (regenLevel > 3 && WeatherUtil.isNight(p.getWorld()) && Cooldown.isCooledDown("bcrifice", p.getUniqueId().toString())) {
				if (!p.isDead() && p.getHealth() - e.getDamage() < 1 && p.getFoodLevel() > 1) {
					int food = p.getFoodLevel() - 1;
					double maxHealed = p.getMaxHealth() - p.getHealth();
					double healed = food > maxHealed ? maxHealed : food;
					p.setHealth(p.getHealth() + healed);
					VampireFoodUtil.setCanChangeFood(p);
					p.setFoodLevel(1);
					p.sendMessage(org.bukkit.ChatColor.DARK_RED + "Your blood was sacrificed to prevent you from dying!");
					EffectUtil.playParticle(p, Effect.MOBSPAWNER_FLAMES);
					Cooldown.newCoolDown("bcrifice", p.getUniqueId().toString(), 300);
				}
			}

			//Passs event to MonsterMash
			AbilityType.MONSTERMASH.run(e);

			if (!mgp.isRace(RaceType.VAMPIRE)) {
				return;
			}

			double damage = e.getDamage();

			if (e.getDamager() instanceof LivingEntity) {
				LivingEntity attacker = (LivingEntity) e.getDamager();
				if (attacker instanceof Player) {
					ItemStack weapon = ((Player) attacker).getInventory().getItemInMainHand();
					if (ItemUtil.isWood(weapon.getType())) {
						damage = damage + 8;
						int reduction;
						if ((reduction = mgp.getAbilityLevel(AbilityType.WOODBANE)) > 0) {
							damage -= reduction;
						}
					}
					if (weapon.containsEnchantment(Enchantment.DAMAGE_UNDEAD)) {
						int level = weapon.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
						damage += level / 2;
					}

					e.setDamage(damage);
				}
			}
		}
	}

	//MonsterMash ability
	@EventHandler
	public void onEntityTarger(EntityTargetLivingEntityEvent e) {
		if (!WorldCheck.isEnabled(e.getEntity().getWorld()) || e.isCancelled()) {
			return;
		}

		if (e.getTarget() instanceof Player) {
			AbilityType.MONSTERMASH.run(e);
		}
	}

	//Cancel fall damage when the correct perk is present
	@EventHandler
	public void onVampireFall(EntityDamageEvent e) {
		if (e.isCancelled() || !WorldCheck.isEnabled(e.getEntity().getWorld()) || !(e instanceof Player)) return;
		AbilityType.DARKBLOOD.run(e);
	}
}
