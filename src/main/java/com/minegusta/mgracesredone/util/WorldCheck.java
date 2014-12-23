package com.minegusta.mgracesredone.util;

import com.google.common.collect.Lists;
import org.bukkit.World;

import java.util.List;

public class WorldCheck
{
    private static List<String> enabledWorlds = Lists.newArrayList("world", "world_nether", "world_the_end", "donor");

    public static boolean isEnabled(String world)
    {
        return enabledWorlds.contains(world);
    }

    public static boolean isEnabled(World world)
    {
        return enabledWorlds.contains(world.getName());
    }
}
