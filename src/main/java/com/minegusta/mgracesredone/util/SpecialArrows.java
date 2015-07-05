package com.minegusta.mgracesredone.util;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.listeners.general.FallDamageManager;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ConcurrentMap;

public class SpecialArrows {
    public enum ArrowType {
        NORMAL(1, 0), ICE(2, 2), GRAPPLE(3, 4), EXPLODE(4, 4);

        private int index;
        private int levelReq;

        private ArrowType(int index, int levelReq) {
            this.index = index;
            this.levelReq = levelReq;
        }

        public int getLevel() {
            return levelReq;
        }

        public int getIndex() {
            return index;
        }
    }

    public static ConcurrentMap<String, ArrowType> arrowMap = Maps.newConcurrentMap();

    public static ArrowType getArrowType(Player p) {
        if (arrowMap.containsKey(p.getUniqueId().toString())) {
            return arrowMap.get(p.getUniqueId().toString());
        }
        return ArrowType.NORMAL;
    }

    public static void setArrowType(Player p, ArrowType type) {
        ChatUtil.sendString(p, "You are now using " + type.name() + " arrows.");
        arrowMap.put(p.getUniqueId().toString(), type);
    }

    public static void nextArrow(Player p) {
        MGPlayer mgp = Races.getMGPlayer(p);

        int index = getArrowType(p).getIndex();
        if (index == ArrowType.values().length) {
            setArrowType(p, ArrowType.NORMAL);
        } else {
            ArrowType next = ArrowType.NORMAL;
            for (ArrowType type : ArrowType.values()) {
                if (type.getIndex() == index + 1) {
                    if (mgp.getAbilityLevel(AbilityType.POINTYSHOOTY) >= type.getLevel()) {
                        next = type;
                    }
                }
            }
            setArrowType(p, next);
        }
    }

    public static void runEffect(Location l, Player p, Entity arrow) {
        ArrowType type = getArrowType(p);

        switch (type) {
            case ICE:
                runIce(l);
                break;
            case GRAPPLE:
                runGrapple(l, p, arrow);
                break;
            case EXPLODE:
                runExplode(l, p);
                break;
        }
    }

    private static void runIce(Location l) {
        l.getWorld().spigot().playEffect(l, Effect.SNOWBALL_BREAK, 1, 1, 2, 2, 2, 1, 20, 50);

        l.getWorld().getEntitiesByClass(LivingEntity.class).stream().filter(le -> le.getLocation().distance(l) <= 6).
                forEach(near -> {
                    PotionUtil.updatePotion(near, PotionEffectType.SLOW, 0, 4);
                    PotionUtil.updatePotion(near, PotionEffectType.BLINDNESS, 0, 1);
                });
    }

    private static void runGrapple(Location l, Player p, Entity arrow) {
        String name = "grapplearrow";
        String uuid = p.getUniqueId().toString();

        if (!Cooldown.isCooledDown(name, uuid)) {
            ChatUtil.sendString(p, "Grappling arrow will be ready in " + Cooldown.getRemaining(name, uuid) + " seconds.");
            return;
        }

        Cooldown.newCoolDown(name, uuid, 8);

        FallDamageManager.addToFallMap(p);
        p.getLocation().setY(p.getLocation().getY() + 0.05);
        p.getLocation().setDirection(l.getDirection());
        p.setVelocity(p.getLocation().getDirection().multiply(2.5));
        arrow.remove();
    }

    private static void runExplode(Location l, Player p) {
        if (RandomUtil.chance(20)) {
            if (!WGUtil.canGetDamage(p)) return;
            l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), 3, false, false);
        }
    }
}
