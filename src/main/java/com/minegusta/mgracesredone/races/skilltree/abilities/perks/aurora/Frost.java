package com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.Cooldown;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class Frost implements IAbility {

    @Override
    public void run(Event event) {

    }

    private static ConcurrentMap<String, List<Block>> frozenMap = Maps.newConcurrentMap();

    @Override
    public void run(Player player) {

        if (!WGUtil.canBuild(player)) {
            ChatUtil.sendString(player, "You cannot use Frost here!");
            return;
        }

        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());
        Location l = player.getLocation();
        String uuid = player.getUniqueId().toString();
        String cooldownName = "frost";

        if (Cooldown.isCooledDown(cooldownName, uuid)) {
            ChatUtil.sendString(player, "You use frost on your location!");
            Cooldown.newCoolDown(cooldownName, uuid, getCooldown(level));
            int radius = 5;
            boolean weaken = level > 2;
            int time = 6;
            if (level > 1) time = 15;
            if (level > 3) radius = 8;

            start(l, radius, time, weaken, uuid, player);
        } else {
            ChatUtil.sendString(player, "You need to wait another " + Cooldown.getRemaining(cooldownName, uuid) + " seconds to use Frost.");
        }
    }

    private void start(Location l, int radius, int time, boolean weaken, final String uuid, Player p) {
        List<Block> blocks = Lists.newArrayList();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                Block b = l.getWorld().getBlockAt((int) l.getX() + x, (int) l.getY(), (int) l.getZ() + z);
                if (b.getLocation().distance(l) > radius) continue;
                if (b.getType() == Material.AIR && b.getRelative(BlockFace.DOWN).getType() != Material.AIR) {
                    b.setType(Material.SNOW);
                    blocks.add(b);

                    p.getWorld().getEntitiesByClass(LivingEntity.class).stream().filter(le ->
                            le.getLocation().distance(p.getLocation()) <= radius).filter(le ->
                            !(le instanceof Player && Races.getRace((Player) le).equals(RaceType.AURORA))).
                            forEach(le -> {
                                //Freeze
                                PotionUtil.updatePotion(le, PotionEffectType.SLOW, 10, time);

                                //Weaken
                                if (weaken) {
                                    PotionUtil.updatePotion(le, PotionEffectType.WEAKNESS, 1, time);
                                }
                            });
                }
            }
        }

        frozenMap.put(uuid, blocks);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
            frozenMap.get(uuid).stream().filter(block -> block.getType() == Material.SNOW).forEach(block -> {
                block.setType(Material.AIR);
            });
            frozenMap.remove(uuid);
        }, time * 20);
    }

    @Override
    public String getName() {
        return "Frost";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.FROST;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.IRON_SWORD;
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
        return 80;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.AURORA);
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
                desc = new String[]{"Freeze the floor around you, freezing enemies.", "Activate by hitting the floor with a sword."};
                break;
            case 2:
                desc = new String[]{"Enemies stay frozen twice as long."};
                break;
            case 3:
                desc = new String[]{"The frozen area also weakens enemies around you."};
                break;
            case 4:
                desc = new String[]{"The radius is 50% larger."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
