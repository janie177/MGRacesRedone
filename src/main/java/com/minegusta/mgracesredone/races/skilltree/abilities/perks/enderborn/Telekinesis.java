package com.minegusta.mgracesredone.races.skilltree.abilities.perks.enderborn;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;

import java.util.List;

public class Telekinesis implements IAbility
{
    @Override
    public void run(Event event)
    {

    }

    @Override
    public void run(Player player)
    {
        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());

        if(!WGUtil.canBuild(player))
        {
            ChatUtil.sendString(player, "You cannot use Telekinesis in this protected zone.");
            return;
        }

        //Setting the attraction strength.
        double strength = 0.10;
        if(level > 2) strength = 2*strength;

        boolean players = level > 3;
        boolean mobs = level > 1;

        Block target = player.getTargetBlock(Sets.newHashSet(Material.AIR), 20);
        Block target2 = player.getTargetBlock(Sets.newHashSet(Material.AIR), 6);

        List<Entity> entities = Lists.newArrayList();

        //Run the ability

        Entity dummy = target.getWorld().spawnEntity(target.getLocation(), EntityType.WEATHER);
        Entity dummy2 = target2.getWorld().spawnEntity(target2.getLocation(), EntityType.WEATHER);

        for(Entity ent : dummy.getNearbyEntities(12,3,12))
        {
            boolean add = ent instanceof Item || ent instanceof Projectile || (mobs && ent instanceof LivingEntity && !(ent instanceof Player)) || (players && ent instanceof Player);
            if(add)
            {
                entities.add(ent);
            }
        }

        for(Entity ent : dummy2.getNearbyEntities(6,3,6))
        {
            boolean add = ent instanceof Item || ent instanceof Projectile || (mobs && ent instanceof LivingEntity && !(ent instanceof Player)) || (players && ent instanceof Player);
            if(add)
            {
                entities.add(ent);
            }
        }
        dummy.remove();
        dummy2.remove();


        //Attracting time.
        for(Entity ent : entities)
        {

            double x = ent.getLocation().getX() - player.getLocation().getX();
            double y = ent.getLocation().getY() - player.getLocation().getY();
            double z = ent.getLocation().getZ() - player.getLocation().getZ();

            Vector v = new Vector(x,y,z);
            v.normalize();
            v.multiply(-strength);

            ent.setVelocity(ent.getVelocity().add(v));
        }


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
        return 2;
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
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"Slowly attract items towards you.", "Activate by right-clicking a blaze rod."};
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
                break;

        }
        return desc;
    }
}
