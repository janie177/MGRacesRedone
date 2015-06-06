package com.minegusta.mgracesredone.util;

import com.google.common.collect.Lists;
import org.bukkit.entity.EntityType;

import java.util.List;

public class MonsterUtil {
    private static final List<EntityType> unholy = Lists.newArrayList(EntityType.SKELETON, EntityType.ZOMBIE, EntityType.WITCH, EntityType.BLAZE, EntityType.GHAST, EntityType.ENDERMAN, EntityType.PIG_ZOMBIE, EntityType.CAVE_SPIDER, EntityType.SPIDER, EntityType.CREEPER, EntityType.ENDERMITE, EntityType.GUARDIAN, EntityType.WITHER);

    public static boolean isUnholy(EntityType t) {
        return unholy.contains(t);
    }

    public static boolean isHoly(EntityType t) {
        return !isUnholy(t);
    }
}
