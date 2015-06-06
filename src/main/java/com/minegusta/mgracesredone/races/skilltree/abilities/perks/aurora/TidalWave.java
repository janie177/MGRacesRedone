package com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.Cooldown;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

// TODO Check for lag issues...

public class TidalWave implements IAbility {

    public static ConcurrentMap<Location, Boolean> blockMap = Maps.newConcurrentMap();

    @Override
    public void run(Event event) {
        // Do nothing
    }

    @Override
    public void run(Player player) {
        if (!WGUtil.canBuild(player)) {
            ChatUtil.sendString(player, "You cannot use TidalWave here!");
            return;
        }

        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());
        String uuid = player.getUniqueId().toString();
        String cooldownName = "wave";

        if (Cooldown.isCooledDown(cooldownName, uuid)) {
            ChatUtil.sendString(player, "You use tidal wave on your location!");
            Cooldown.newCoolDown(cooldownName, uuid, getCooldown(level));

            int radius = 7;
            boolean damage = level > 1;
            if (level > 2) radius = 14;

            start(radius, damage, player);
        } else {
            ChatUtil.sendString(player, "You need to wait another " + Cooldown.getRemaining(cooldownName, uuid) + " seconds to use TidalWave.");
        }
    }

    private void start(int radius, final boolean damage, Player p) {
        //Add all blocks for the wave in a row here.
        final Location center = p.getTargetBlock(Sets.newHashSet(Material.AIR), 7).getLocation();
        final Vector v = p.getLocation().getDirection();
        v.normalize();
        v.multiply(1.25);

        for (int i = 0; i <= radius; i++) {
            final Location start = new Location(center.getWorld(), center.getX() + i * v.getX(), center.getY() + i * v.getY(), center.getZ() + i * v.getZ());

            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
                final List<Block> blocks = Lists.newArrayList();

                //Do the wave stuff
                for (int x = -5; x < 6; x++) {
                    for (int y = -5; y < 6; y++) {
                        for (int z = -5; z < 6; z++) {
                            Block target = start.getWorld().getBlockAt((int) start.getX() + x, (int) start.getY() + y, (int) start.getZ() + z);
                            if (target.getLocation().distance(start) < 3 && target.getType() == Material.AIR) {
                                blockMap.put(target.getLocation(), true);
                                blocks.add(target);
                            }
                        }
                    }
                }

                //Set to water and then task to make it air again

                start.getWorld().getEntitiesByClass(Player.class).stream().filter(player -> player.getLocation().distance(start) <= 100).forEach(player -> {
                    blocks.stream().filter(b -> b.getType() == Material.AIR).forEach(b -> {
                        player.sendBlockChange(b.getLocation(), Material.STATIONARY_WATER, (byte) 0);
                        // b.setType(Material.STATIONARY_WATER);

                        //Return to normal after 2/3 second.
                        Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getPlugin(), () -> {
                            player.sendBlockChange(b.getLocation(), Material.AIR, (byte) 0);
                            blockMap.remove(b.getLocation());
                        }, 10);
                    });
                });

                //Apply the effects
                start.getWorld().getEntitiesByClass(LivingEntity.class).stream().
                        filter(le -> le.getLocation().distance(start) <= 4).forEach(le -> {
                    double x = le.getLocation().getX() - start.getX();
                    double y = le.getLocation().getY() - start.getY();
                    double z = le.getLocation().getZ() - start.getZ();
                    Vector v1 = new Vector(x, y, z);
                    v1.normalize();
                    le.setVelocity(le.getVelocity().add(v1.multiply(1.4)));

                    if (damage && WGUtil.canGetDamage(le)) {
                        le.damage(1);
                    }
                });

                /*Return to normal after 2/3 second.
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
                    blocks.stream().forEach(b -> {
                        b.setType(Material.AIR);
                        blockMap.remove(b.getLocation());
                    });
                }, 10);*/
            }, 6 * i);
        }
    }

    @Override
    public String getName() {
        return "Tidal Wave";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.TIDALWAVE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.POTION;
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
        return Lists.newArrayList(RaceType.AURORA);
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
                desc = new String[]{"Cast a wall of water in any of four directions.", "Activate by left-clicking a snowball."};
                break;
            case 2:
                desc = new String[]{"Your wave will cause drown damage to anyone in it."};
                break;
            case 3:
                desc = new String[]{"Your wave will reach twice as far."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
