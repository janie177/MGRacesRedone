package com.minegusta.mgracesredone.races.skilltree.abilities.perks.enderborn;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class Telekinesis implements IAbility {

    @Override
    public void run(Event event) {

    }

    public static ConcurrentMap<String, Long> cooldown = Maps.newConcurrentMap();

    @Override
    public boolean run(Player player) {
        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());

        if (!WGUtil.canBuild(player)) {
            player.sendMessage(ChatColor.RED + "You cannot use Telekinesis in this protected zone.");
            return false;
        }

        String uuid = player.getUniqueId().toString();

        if (cooldown.containsKey(uuid) && System.currentTimeMillis() - cooldown.get(uuid) < 100) {
            return false;
        }

        if (player.isSneaking()) {
            player.sendMessage(ChatColor.RED + "You cannot use this while crouching! Don't ask me why.");
            return false;
        }

        //Setting the attraction strength.
        double strength = 0.21;
        if (level > 2) {
            strength = 2 * strength;
        }

        final double finalStrength = strength;

        boolean players = level > 3;
        boolean mobs = level > 1;

        Block target = player.getTargetBlock(Sets.newHashSet(Material.AIR), 20);
        Block target2 = player.getTargetBlock(Sets.newHashSet(Material.AIR), 6);

        final Location l = player.getLocation();

        //Run the ability
        player.getWorld().getEntities().stream().filter(ent ->
                !ent.getUniqueId().equals(player.getUniqueId()) &&
                (ent.getLocation().distance(target.getLocation()) <= 12 ||
                        ent.getLocation().distance(target2.getLocation()) < 6) && (ent instanceof Item ||
                        ent instanceof Projectile || (mobs && ent instanceof LivingEntity &&
                        !(ent instanceof Player)) || (players && ent instanceof Player && !((Player) ent).isFlying())) &&
                        WGUtil.canBuild(player, ent.getLocation())).forEach(ent -> {
            double x = ent.getLocation().getX() - l.getX();
            double y = ent.getLocation().getY() - l.getY();
            double z = ent.getLocation().getZ() - l.getZ();

            Vector v = new Vector(x, y, z);
            v.normalize();
            v.multiply(-finalStrength);

            ent.setVelocity(ent.getVelocity().add(v));
        });

        cooldown.put(uuid, System.currentTimeMillis());

        return false;
    }

    @Override
    public String getName() {
        return "Telekinesis";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.TELEKINESIS;
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
        return 1;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.ACTIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 0;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.ENDERBORN);
    }

    @Override
    public boolean canBind() {
        return true;
    }

    @Override
    public String getBindDescription() {
        return "Attract items and entities towards you.";
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
                desc = new String[]{"Slowly attract items towards you.", "Bind to an item using /Bind."};
                break;
            case 2:
                desc = new String[]{"You will now attract mobs as well."};
                break;
            case 3:
                desc = new String[]{"The attracting effect is twice as strong."};
                break;
            case 4:
                desc = new String[]{"You will now attract players too."};
                break;
            default:
                desc = new String[]{"This is an error!"};
        }
        return desc;
    }
}
