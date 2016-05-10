package com.minegusta.mgracesredone.races.skilltree.abilities.perks.elf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ElfShooter;
import com.minegusta.mgracesredone.util.RandomUtil;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;

import java.util.List;

public class ArrowNado implements IAbility {
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

		//Checks for level
		boolean poison = false, twice = false;

		if (level > 2) poison = true;
		if (level > 1) twice = true;

		int duration = 7;

		//Spawn the whirlwind.
		createWind(player, player.getLocation(), duration, twice, poison);

		return true;
	}

	private void createWind(Player shooter, Location l, int duration, boolean twice, boolean poison) {
		Location center = l;
		int interval = twice ? 2 : 4;
		List<Entity> arrows = Lists.newArrayList();
		ElfShooter es = new ElfShooter(shooter.getUniqueId().toString());
		for (int i = 0; i <= 20 * duration; i++) {
			if (i % 2 == 0) {
				final int k = i;

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
					if (k % 20 == 0) {
						center.getWorld().playSound(center, Sound.ENTITY_ARROW_SHOOT, 10, 1);
					}

					//Spawn arrow.
					if (k % interval == 0) {
						if (poison && RandomUtil.chance(70)) {
							TippedArrow arrow = (TippedArrow) l.getWorld().spawnEntity(l.clone().add(RandomUtil.randomNumber(10) - 5, RandomUtil.randomNumber(10) - 5, RandomUtil.randomNumber(10) - 5), EntityType.TIPPED_ARROW);
							arrow.setBasePotionData(new PotionData(RandomUtil.fiftyfifty() ? PotionType.POISON : PotionType.INSTANT_DAMAGE, false, false));
							arrow.setCritical(false);
							arrow.setShooter(es);
							arrows.add(arrow);
						} else {
							Arrow arrow = (Arrow) l.getWorld().spawnEntity(l.clone().add(RandomUtil.randomNumber(10) - 5, RandomUtil.randomNumber(10) - 5, RandomUtil.randomNumber(10) - 5), EntityType.ARROW);
							arrow.setCritical(true);
							arrow.setShooter(es);
							arrows.add(arrow);
						}
					}

					//The sucking people in effect
					center.getWorld().getEntitiesByClasses(Arrow.class, TippedArrow.class, SpectralArrow.class).stream().
							filter(ent -> ent.getLocation().distance(center) <= 12).forEach(ent ->
					{
						double angle = Math.toRadians(8);
						double radius = Math.abs(ent.getLocation().distance(center));

						if (radius < 1) {
							ent.setVelocity(new Vector(1, 0, 1));
						}

						double x = ent.getLocation().getX() - center.getX();
						double z = ent.getLocation().getZ() - center.getZ();

						double dx = x * Math.cos(angle) - z * Math.sin(angle);
						double dz = x * Math.sin(angle) + z * Math.cos(angle);

						Location target1 = new Location(ent.getWorld(), dx + center.getX(), ent.getLocation().getY(), dz + center.getZ());

						double ix = ent.getLocation().getX() - target1.getX();
						double iz = ent.getLocation().getZ() - target1.getZ();

						Vector v = new Vector(ix, -0.2, iz);
						v.normalize();

						double amplifier = 0.6;
						ent.setVelocity(ent.getVelocity().add(v).multiply(-amplifier));
					});

					//Launch the arrows at nearby entities
					if (k == 20 * duration) {
						List<LivingEntity> targets = Lists.newArrayList();

						l.getWorld().getLivingEntities().stream().filter(le -> le.getLocation().distance(center) < 35 && (le instanceof Player || le instanceof Monster)).forEach(targets::add);

						if (targets.contains(shooter)) targets.remove(shooter);
						if (targets.isEmpty()) {
							arrows.stream().filter(Entity::isValid).forEach(Entity::remove);
						}

						arrows.stream().filter(ent -> ent.isValid() && !ent.isOnGround()).forEach(a ->
						{
							LivingEntity target = targets.get(RandomUtil.randomNumber(targets.size()) - 1);
							if (target.isValid()) {
								Vector v = target.getLocation().toVector().subtract(a.getLocation().toVector());
								a.setVelocity(v.multiply(1.8));
							}
						});

						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () ->
						{
							arrows.stream().filter(Entity::isValid).forEach(Entity::remove);
							arrows.clear();
							targets.clear();
						}, 60);
					}
				}, i);
			}
		}
	}

	@Override
	public String getName() {
		return "ArrowNado";
	}

	@Override
	public AbilityType getType() {
		return AbilityType.ARROWNADO;
	}

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public Material getDisplayItem() {
		return Material.SPECTRAL_ARROW;
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
		return 40;
	}

	@Override
	public List<RaceType> getRaces() {
		return Lists.newArrayList(RaceType.ELF);
	}

	@Override
	public boolean canBind() {
		return true;
	}

	@Override
	public String getBindDescription() {
		return "Call a tornado of arrows that then hit nearby enemies.";
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
				desc = new String[]{"Summon a tornado of arrows.", "They eventually fire at nearby monsters and players.", "Bind using /Bind."};
				break;
			case 2:
				desc = new String[]{"Twice as many arrows spawn."};
				break;
			case 3:
				desc = new String[]{"Arrows now have poisoned and damaging tips."};
				break;
			default:
				desc = new String[]{"This is an error!"};
				break;

		}
		return desc;
	}
}
