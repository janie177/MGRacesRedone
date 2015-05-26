package com.minegusta.mgracesredone.races.skilltree.abilities.perks.dwarf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.Cooldown;
import com.minegusta.mgracesredone.util.RandomUtil;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import java.util.List;

public class Earthquake implements IAbility
{

    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player)
    {
        //Standard data needed.
        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());
        int radius = 8;
        int strength = 1;
        if(level > 3)strength = 2;
        int duration = 10;
        if(level > 2)duration = 15;
        Location l = player.getLocation();
        String name = "";
        String uuid = player.getUniqueId().toString();

        //Cooldown?
        if(!Cooldown.isCooledDown(name, uuid))
        {
            ChatUtil.sendString(player, "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use " + getName() + ".");
            return;
        }

        //Worldguard?
        if(!WGUtil.canBuild(player))
        {
            ChatUtil.sendString(player, "You cannot use " + getName() + " here.");
            return;
        }

        //Message
        ChatUtil.sendString(player, "You used earthquake on your location!");

        //Run the ability here\
        task(l, duration, radius, strength);
        Cooldown.newCoolDown(name, uuid, getCooldown(level));
    }

    private void task(final Location l, int duration, final int radius, final double strength)
    {
        for(int i  = 0; i <= 20 * duration; i++)
        {
            if(i % 4 == 0)
            {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run()
                    {
                        shake(l, radius, strength);
                        if(RandomUtil.chance(30))
                        {
                            double x = RandomUtil.randomNumber(2 * radius) - radius;
                            double z = RandomUtil.randomNumber(2 * radius) - radius;
                            column(new Location(l.getWorld(),l.getX() + x , l.getY(), l.getZ() + z));
                        }
                    }
                }, i);
            }
        }
    }

    private void shake(final Location l, int radius, double strength)
    {
        Entity dummy = l.getWorld().spawnEntity(l, EntityType.ARROW);

        for(Entity ent : dummy.getNearbyEntities(radius, 2, radius))
        {
            if(!(ent instanceof Player && Races.getMGPlayer((Player) ent).getRaceType() == RaceType.DWARF) && (ent instanceof LivingEntity || ent instanceof Item))
            {
                double range = strength * 0.6;
                double min = range / 2;

                double x = RandomUtil.randomDouble(range, 0) - min;
                double y = RandomUtil.randomDouble(range, 0) - min;
                double z = RandomUtil.randomDouble(range, 0) - min;

                ent.setVelocity(ent.getVelocity().add(new Vector(x,y,z)));
            }
        }
        dummy.remove();
    }

    private void column(final Location l)
    {
        final int duration = 6 * 20;
        final int delay = 6;

        final Block b = findAir(l);

        //Spawn them
        for(int i = 0; i < 5; i ++)
        {
            final int up = i;
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                @Override
                public void run()
                {
                    Block changed = b.getRelative(BlockFace.UP, up);
                    if(changed.getType() == Material.AIR)
                    {
                        changed.setType(Material.STONE);
                        removeBlock(changed, duration - up * 20);
                    }
                }
            }, i * delay);
        }
    }

    private void removeBlock(final Block b, int delay)
    {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run()
            {
                if(b.getType() == Material.STONE)b.setType(Material.AIR);
            }
        }, delay);
    }

    private Block findAir(Location l)
    {
        if(l.getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR)
        {
            return l.getBlock();
        }
        for(int i = 2; i < 7; i++)
        {
            if(l.getBlock().getRelative(BlockFace.DOWN, i).getType() != Material.AIR)
            {
                return l.getBlock().getRelative(BlockFace.DOWN, i - 1);
            }
        }
        return l.getBlock().getRelative(BlockFace.DOWN, 3);
    }

    @Override
    public String getName() {
        return "Earthquake";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.EARTQUAKE;
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
        return 80;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.DWARF);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level)
        {
            case 1: desc = new String[]{"Cause an earthquake, unbalancing entities around you.", "Activate by right clicking a pickaxe.", "Lasts 10 seconds."};
                break;
            case 2: desc = new String[]{"The floor around enemies will distort."};
                break;
            case 3: desc = new String[]{"The duration is increased to 15 seconds."};
                break;
            case 4: desc = new String[]{"The disorienting effect is twice as strong."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
