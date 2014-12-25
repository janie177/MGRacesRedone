package com.minegusta.mgracesredone.util;

import com.google.common.collect.Lists;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public class BlockUtil
{
    /**
     * Look in a radius around a block and count for a needed material. Return if there is enough.
     * @param center The block that is centered.
     * @param radius The distance to search in in all directions.
     * @param searched The desired material.
     * @param neededAmount The desired amount of the material specified.
     * @return If there is enough blocks.
     */
    public static boolean radiusCheck(Block center, int radius, Material searched, int neededAmount)
    {
        int count = 0;

        for (int x = -(radius); x <= radius; x ++)
        {
            for (int y = -(radius); y <= radius; y ++)
            {
                for (int z = -(radius); z <= radius; z ++)
                {
                    if (center.getRelative(x,y,z).getType() == searched)
                    {
                        count++;
                    }
                }
            }
        }
        return count >= neededAmount;
    }

    public static Block getHighestBlock(Location l)
    {
        return l.getWorld().getHighestBlockAt(l);
    }

    public static int getHighestBlockYAt(Location l)
    {
        return l.getWorld().getHighestBlockYAt(l);
    }

    public static Material getBlockAtLocation(Location l)
    {
        return l.getBlock().getType();
    }

    public static Material getBlockAtPlayer(Player p)
    {
        return getBlockAtLocation(p.getLocation());
    }

    public static List<Block> getCircle(Location center, int radius, boolean fill)
    {
        List<Block> list = Lists.newArrayList();
        int x = (int) center.getX();
        int y = (int) center.getY();
        int z = (int) center.getZ();

        if(fill)
        {

        }
        else
        {

        }
        return list;
    }


    public static void poofBlocks(Block center, int radius, List<Material> searched, Material replacement, Effect effect)
    {
        for (int x = -(radius); x <= radius; x ++)
        {
            for (int y = -(radius); y <= radius; y ++)
            {
                for (int z = -(radius); z <= radius; z ++)
                {
                    Block block = center.getRelative(x,y,z);
                    if (searched.contains(block.getType()))
                    {
                        EffectUtil.playParticle(block.getLocation(), effect, 1,1,1,10);
                        block.setType(replacement);
                    }
                }
            }
        }
    }
}
