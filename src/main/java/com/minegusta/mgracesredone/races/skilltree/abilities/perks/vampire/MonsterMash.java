package com.minegusta.mgracesredone.races.skilltree.abilities.perks.vampire;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.VampireTruceUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.List;

public class MonsterMash implements IAbility {

	private static List<EntityType> truce = Lists.newArrayList(EntityType.GIANT, EntityType.ZOMBIE, EntityType.SKELETON, EntityType.SPIDER, EntityType.CAVE_SPIDER, EntityType.CREEPER, EntityType.WITCH, EntityType.BLAZE, EntityType.GHAST, EntityType.PIG_ZOMBIE, EntityType.ENDERMAN, EntityType.ENDERMITE);

	private static long helpCooldown = 0;

	@Override
	public void run(Event event) {

		//Target event
		if (event instanceof EntityTargetLivingEntityEvent) {
			EntityTargetLivingEntityEvent e = (EntityTargetLivingEntityEvent) event;
			Player p = (Player) e.getTarget();
			MGPlayer mgp = Races.getMGPlayer(p);
			if (mgp.getAbilityLevel(getType()) > 0 && VampireTruceUtil.hasTruce(p) && truce.contains(e.getEntityType())) {
				e.setCancelled(true);
			}
		}
		//Level 2 getting mobs to actually help you, or break the truce when you attack the mobs.
		else if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
			if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
				LivingEntity target = (LivingEntity) e.getEntity();
				Player damager = (Player) e.getDamager();
				MGPlayer mgp = Races.getMGPlayer(damager);

				//Return if player does not have this perk
				if (!mgp.hasAbility(getType())) return;

				//Break truce if attacking an allied mob
				if (truce.contains(target.getType())) {
					damager.sendMessage(ChatColor.RED + "You have temporarily broken your truce with monsters!");
					VampireTruceUtil.setAttackable(damager, 60);
				}

				//Get mobs to help you
				else if (mgp.getAbilityLevel(getType()) > 1 && helpCooldown < System.currentTimeMillis() && VampireTruceUtil.hasTruce(damager)) {
					damager.sendMessage(ChatColor.GREEN + "Nearby allied monsters are coming to your aid!");
					if (target instanceof Player)
						target.sendMessage(ChatColor.RED + "Nearby monsters are siding with the vampire!");
					helpCooldown = System.currentTimeMillis() + 4000;
					damager.getWorld().getLivingEntities().stream().filter(ent -> ent.getLocation().distance(target.getLocation()) < 40 && truce.contains(ent.getType()) && ent instanceof Creature)
							.forEach(ent -> ((Creature) ent).setTarget(target));
				}
			}
		}
	}

	@Override
	public boolean run(Player player) {
		return true;
	}

	@Override
	public String getName() {
		return "Monster Mash";
	}

	@Override
	public AbilityType getType() {
		return AbilityType.MONSTERMASH;
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public Material getDisplayItem() {
		return Material.MONSTER_EGG;
	}

	@Override
	public int getPrice(int level) {
		return 1;
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
		return 2;
	}

	@Override
	public String[] getDescription(int level) {
		String[] desc;

		switch (level) {
			case 1:
				desc = new String[]{"Most hostile mobs will no longer target you unless provoked."};
				break;
			case 2:
				desc = new String[]{"Truced mobs will now aid you in combat."};
				break;
			default:
				desc = new String[]{"This is an error!"};
				break;

		}
		return desc;
	}
}
