package com.minegusta.mgracesredone.races.skilltree.abilities.perks.demon;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.Cooldown;
import com.minegusta.mgracesredone.util.EffectUtil;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import java.util.List;

public class HellRift implements IAbility {
    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player) {
        MGPlayer mgp = Races.getMGPlayer(player);

        String name = "hrift";
        String id = player.getUniqueId().toString();

        if (!Cooldown.isCooledDown(name, id)) {
            ChatUtil.sendString(player, ChatColor.RED + "Hell Rift will be ready in " + Cooldown.getRemaining(name, id) + " seconds.");
            return;
        }

        //Get the target a block above the floor.
        Block target = player.getTargetBlock(Sets.newHashSet(Material.AIR), 20).getRelative(0, 2, 0);

        //Only in non-building areas.
        if (!WGUtil.canBuild(player, target.getLocation())) {
            ChatUtil.sendString(player, "You cannot use this here!");
            return;
        }


        //Start the cooldown
        Cooldown.newCoolDown(name, id, getCooldown(mgp.getAbilityLevel(getType())));


        int level = mgp.getAbilityLevel(getType());

        int duration = level * 6;

        if (level > 2) duration = 12;

        boolean explode = level > 2;

        ChatUtil.sendString(player, "You opened a Hell Rift!");

        runHellRift(target, duration, explode);
    }

    private void runHellRift(final Block target, int duration, boolean explode) {

        final Location l = target.getLocation();
        for (int i = 0; i <= 20 * duration; i++) {
            if (i % 4 == 0) {
                final int k = i;
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
                        //Effects lol
                        EffectUtil.playParticle(l, Effect.PORTAL, 30);
                        EffectUtil.playParticle(l, Effect.LARGE_SMOKE, 30);
                        EffectUtil.playParticle(l, Effect.LARGE_SMOKE, 30);
                        EffectUtil.playParticle(l, Effect.FLAME, 30);
                        EffectUtil.playParticle(l, Effect.FLYING_GLYPH, 30);
                        EffectUtil.playParticle(l, Effect.LAVADRIP, 30);

                        if (k % 20 == 0) {
                            EffectUtil.playSound(l, Sound.PORTAL);
                        }

                        //The sucking people in effect
                    l.getWorld().getEntities().stream().filter(ent -> !(ent instanceof Player && Races.getRace((Player) ent) == RaceType.DEMON) && (ent instanceof Player || ent instanceof LivingEntity || ent instanceof Item || ent instanceof Projectile)).forEach(ent ->
                    {
                        double amplifier = 0.05 + 1 / ent.getLocation().distance(l);
                        if (amplifier > 1.05) amplifier = 1.05;

                        double x = ent.getLocation().getX() - l.getX();
                        double y = ent.getLocation().getY() - l.getY();
                        double z = ent.getLocation().getZ() - l.getZ();

                        Vector v = new Vector(x, y, z);
                        v.normalize();

                        ent.setVelocity(ent.getVelocity().add(v.multiply(-amplifier)));
                    });
                }, i);
            }
        }
        if (explode) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), 4, false, false), 20 * duration);
        }
    }

    @Override
    public String getName() {
        return "Hell Rift";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.HELLRIFT;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.EYE_OF_ENDER;
    }

    @Override
    public int getPrice(int level) {
        int price = 1;
        switch (level) {
            case 1:
                price = 2;
                break;
            case 2:
                price = 1;
                break;
            case 3:
                price = 1;
                break;
        }
        return price;
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
        return Lists.newArrayList(RaceType.DEMON);
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
                desc = new String[]{"Open a rift that sucks in loose entities.", "Activate using a blazerod.", "The rift stays open for 6 seconds.", "Demons are immune."};
                break;
            case 2:
                desc = new String[]{"Your rift will now stay twice as long."};
                break;
            case 3:
                desc = new String[]{"Your rift will explode when closing."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
