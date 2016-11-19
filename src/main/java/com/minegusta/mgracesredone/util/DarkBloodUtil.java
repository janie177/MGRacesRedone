package com.minegusta.mgracesredone.util;

import com.google.common.collect.Maps;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ConcurrentMap;

public class DarkBloodUtil {
    private static ConcurrentMap<String, Boolean> players = Maps.newConcurrentMap();

    public static boolean isToggledOn(Player p) {
        return !players.containsKey(p.getName().toLowerCase());
    }

    public static void setForPlayer(Player p) {
        players.put(p.getName().toLowerCase(), true);
        //Set the night vision to 1 second remaining so it will go off right after toggling, rather than having it stay on for another 20 seconds.
        PotionUtil.updatePotion(p, PotionEffectType.NIGHT_VISION, 0, 1);

    }

    public static boolean toggle(Player p) {
        if (isToggledOn(p)) {
            setForPlayer(p);
            return false;
        } else {
            players.remove(p.getName().toLowerCase());
            return true;
        }
    }
}
