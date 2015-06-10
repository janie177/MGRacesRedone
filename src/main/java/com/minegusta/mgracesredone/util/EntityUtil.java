package com.minegusta.mgracesredone.util;

import org.bukkit.Material;
import org.bukkit.entity.Entity;

public class EntityUtil {
    public static boolean isInWater(Entity ent) {
        Material mat = ent.getLocation().getBlock().getType();
        return mat == Material.WATER || mat == Material.STATIONARY_WATER;
    }

}
