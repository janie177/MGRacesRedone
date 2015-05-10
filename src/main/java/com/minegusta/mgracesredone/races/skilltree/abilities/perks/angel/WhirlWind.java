package com.minegusta.mgracesredone.races.skilltree.abilities.perks.angel;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import java.util.List;

public class WhirlWind implements IAbility{
    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player)
    {
        MGPlayer mgp = Races.getMGPlayer(player);

        String name = "wwind";
        String id = player.getUniqueId().toString();

        if(!Cooldown.isCooledDown(name, id)) {
            ChatUtil.sendString(player, ChatColor.RED + "WhirlWind will be ready in " + Cooldown.getRemaining(name, id) + " seconds.");
            return;
        }

        //Get the target a block above the floor.
        Block target = player.getTargetBlock(Sets.newHashSet(Material.AIR), 40).getRelative(0, 2, 0);

        //Only in non-building areas.
        if(!WGUtil.canBuild(player, target.getLocation()))
        {
            ChatUtil.sendString(player, "You cannot use this here!");
            return;
        }


        //Start the new cooldown.
        Cooldown.newCoolDown(name, id, getCooldown(mgp.getAbilityLevel(getType())));


        int level = mgp.getAbilityLevel(getType());

        int duration = 10 + ((level / 3) * 8);

        boolean lightning = level > 4;

        boolean launch = level > 3;

        double startingStrength = 0.12;
        if(level > 1) startingStrength = 0.25;

        ChatUtil.sendString(player, "You summon a Whirlwind!");

        runWhirlWind(target, duration, launch, lightning, startingStrength);
    }

    private void runWhirlWind(final Block target, int duration, final boolean launch, final boolean lightning, final double startingStrength)
    {
        final Location center = target.getLocation();
        for(int i = 0; i <= 20 * duration; i++)
        {
            if(i%2 == 0)
            {
                final int k = i;

                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run()
                    {
                        if(k % 20 == 0)
                        {
                            EffectUtil.playSound(center, Sound.ENDERDRAGON_WINGS);
                            if(lightning && RandomUtil.chance(30))
                            {
                                double x = center.getX() + (RandomUtil.randomNumber(14) - 7);
                                double z = center.getZ() + (RandomUtil.randomNumber(14) - 7);
                                double y = center.getY();
                                Location l = new Location(center.getWorld(),x, y, z);

                                center.getWorld().strikeLightning(l);
                            }
                        }
                        EffectUtil.playParticle(center, Effect.PARTICLE_SMOKE, 1, 10, 1, 30, 50);



                        //The sucking people in effect
                        Entity dummy = center.getWorld().spawnEntity(center, EntityType.EXPERIENCE_ORB);

                        for(Entity ent : dummy.getNearbyEntities(15,15,15))
                        {
                            if(ent instanceof LivingEntity || ent instanceof Item || ent instanceof Projectile)
                            {
                                //Angels are immune
                                if(ent instanceof Player && Races.getRace((Player) ent) == RaceType.ANGEL)
                                {
                                    continue;
                                }

                                double angle = Math.toRadians(14);
                                double radius = Math.abs(ent.getLocation().distance(center));

                                double x = ent.getLocation().getX() - center.getX();
                                double z = ent.getLocation().getZ() - center.getZ();

                                double dx = x * Math.cos(angle) - z * Math.sin(angle);
                                double dz = x * Math.sin(angle) + z * Math.cos(angle);

                                Location target = new Location(ent.getWorld(), dx + center.getX(), ent.getLocation().getY(), dz + center.getZ());

                                double ix = ent.getLocation().getX() - target.getX();
                                double iz = ent.getLocation().getZ() - target.getZ();

                                Vector v = new Vector(ix, -0.2, iz);
                                v.normalize();

                                if(launch && radius < 2)
                                {
                                    v.setY(-2.3);
                                }

                                //The closer to the center, the stronger the force.
                                double amplifier = startingStrength + 2/radius;
                                ent.setVelocity(ent.getVelocity().add(v).multiply(-amplifier));
                            }
                        }
                        dummy.remove();
                    }
                }, i);
            }
        }
    }

    @Override
    public String getName() {
        return "WhirlWind";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.WHIRLWIND;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.NETHER_STAR;
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
        return 120;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.ANGEL);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level)
        {
            case 1: desc = new String[]{"Right click a feather to start a tornado on that location.", "Your tornado lasts for 10 seconds."};
                break;
            case 2: desc = new String[]{"Your tornado is now twice as strong."};
                break;
            case 3: desc = new String[]{"Your tornado will last for 18 seconds."};
                break;
            case 4: desc = new String[]{"When reaching the center of the tornado, entities will be launched up."};
                break;
            case 5: desc = new String[]{"Your tornado will summon lightning strikes."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
