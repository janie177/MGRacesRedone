package com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class IceBarrage implements IAbility {

    private static final double[] directions = {0.04, -0.04, 0.06, -0.06};
    private static final Effect[] effects = {Effect.SNOW_SHOVEL, Effect.WATERDRIP, Effect.SNOWBALL_BREAK};

    private static void callRain(Location l, final boolean poison) {
        //The loop for the amount of seconds needed.
        for (int i = 0; i < 20 * 7; i++) {
            if (i % 5 == 0) {
                double xoffSet = RandomUtil.randomNumber(10) - 5;
                double zoffSet = RandomUtil.randomNumber(10) - 5;
                final Location spot = new Location(l.getWorld(), l.getX() + xoffSet, l.getY() + 20, l.getZ() + zoffSet);

                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
                    final Snowball rain = (Snowball) spot.getWorld().spawnEntity(spot, EntityType.SNOWBALL);
                    rain.setVelocity(new Vector(0, -1, 0));
                    if (poison) rain.setShooter(new IceBarragePoisonThrower());
                    else rain.setShooter(new IceBarrageThrower());
                }, i);
            }
        }
    }

    @Override
    public void run(Event event) {
        ProjectileHitEvent e = (ProjectileHitEvent) event;
        Player p = (Player) e.getEntity().getShooter();

        if (!WGUtil.canBuild(p, p.getLocation())) {
            ChatUtil.sendString(p, "You cannot use Ice Barrage here!");
            return;
        }

        Snowball ball = (Snowball) e.getEntity();
        MGPlayer mgp = Races.getMGPlayer(p);
        int level = mgp.getAbilityLevel(getType());

        String name = "snowball";
        String uuid = p.getUniqueId().toString();
        final Location l = ball.getLocation();

        if (Cooldown.isCooledDown(name, uuid)) {
            ChatUtil.sendString(p, ChatColor.DARK_AQUA + "You send a blizzard at your enemies!");
            Cooldown.newCoolDown(name, uuid, getCooldown(level));
            for (double x : directions) {
                for (double z : directions) {
                    Missile.createMissile(l, x, 0.06, z, effects, 60);
                }
            }

            if (level > 2) {
                boolean poison = level > 3;
                callRain(l, poison);
            }

            ball.getWorld().getEntitiesByClass(LivingEntity.class).stream().
                    filter(le -> le.getLocation().distance(ball.getLocation()) <= 5).
                    filter(le -> !(le instanceof Player && Races.getRace((Player) le).equals(RaceType.AURORA))).
                    forEach(le -> {
                        PotionUtil.updatePotion(le, PotionEffectType.SLOW, 1, 8);
                        PotionUtil.updatePotion(le, PotionEffectType.BLINDNESS, 1, 5);
                        if (level > 1) {
                            WeaknessUtil.setWeakness(p, 2, 5);
                        }
                    });
        } else {
            ChatUtil.sendString(p, ChatColor.AQUA + "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use Ice Barrage.");
        }
    }

    @Override
    public boolean run(Player player) {
        return false;
    }

    @Override
    public String getName() {
        return "Ice Barrage";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.ICEBARRAGE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.SNOW_BALL;
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
        return 35;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.AURORA);
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
                desc = new String[]{"Throw a snowball to blind and slow enemies for a short time."};
                break;
            case 2:
                desc = new String[]{"Enemies will be weakened by your snowballs now."};
                break;
            case 3:
                desc = new String[]{"It will rain snowballs that will freeze enemies."};
                break;
            case 4:
                desc = new String[]{"The raining snowballs now poison enemies as well."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
