package com.minegusta.mgracesredone.races.skilltree.abilities.perks.angel;

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

        Cooldown.newCoolDown(name, id, getCooldown(mgp.getAbilityLevel(getType())));

        //Get the target a block above the floor.
        Block target = player.getTargetBlock(Sets.newHashSet(Material.AIR), 20).getRelative(0, 2, 0);

        int level = mgp.getAbilityLevel(getType());

        int duration = 10 + ((level / 3) * 8);

        boolean lightning = level > 3;

        ChatUtil.sendString(player, "You summon a Whirlwind!");

        runWhirlWind(target, duration, lightning);
    }

    private void runWhirlWind(final Block target, int duration, boolean lightning)
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
                        }

                        EffectUtil.playParticle(center, Effect.ENDER_SIGNAL);

                        //The sucking people in effect
                        Entity dummy = center.getWorld().spawnEntity(center, EntityType.EXPERIENCE_ORB);

                        for(Entity ent : dummy.getNearbyEntities(15,10,15))
                        {
                            if(ent instanceof LivingEntity || ent instanceof Item || ent instanceof Projectile)
                            {
                                //Angels are immune
                                if(ent instanceof Player && Races.getRace((Player) ent) == RaceType.ANGEL)
                                {
                                    continue;
                                }

                                double angle = Math.toRadians(15);
                                double radius = Math.abs(ent.getLocation().distance(center));

                                double x = ent.getLocation().getX() - center.getX();
                                double z = ent.getLocation().getZ() - center.getZ();

                                double dx = x * Math.cos(angle) - z * Math.sin(angle);
                                double dz = x * Math.sin(angle) + z * Math.cos(angle);

                                Location target = new Location(ent.getWorld(), dx + center.getX(), ent.getLocation().getY(), dz + center.getZ());

                                double ix = ent.getLocation().getX() - target.getX();
                                double iz = ent.getLocation().getZ() - target.getZ();

                                Vector v = new Vector(ix, -0.1, iz);
                                v.normalize();

                                //The closer to the center, the stronger the force.
                                double amplifier = 0.25 + 2/radius;
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
