package com.minegusta.mgracesredone.util;

import org.bukkit.Material;
import org.bukkit.block.Block;

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
}
