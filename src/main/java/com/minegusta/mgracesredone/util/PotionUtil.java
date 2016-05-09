package com.minegusta.mgracesredone.util;

import com.google.common.collect.Lists;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class PotionUtil {
    /**
     * @param p         The player involved.
     * @param type      The type of the potion.
     * @param amplifier The amplifier.
     * @param seconds   The duration in seconds.
     */
    public static void updatePotion(Player p, PotionEffectType type, int amplifier, int seconds) {
        p.getActivePotionEffects().stream().filter(pe -> pe.getType().equals(type) && pe.getAmplifier() <= amplifier).
                forEach(pe -> {
                    p.removePotionEffect(type);
                });
        p.addPotionEffect(new PotionEffect(type, 20 * seconds, amplifier, false, false));
    }

    public static void updatePotion(LivingEntity ent, PotionEffectType type, int amplifier, int seconds) {
        ent.getActivePotionEffects().stream().filter(pe -> pe.getType().equals(type) && pe.getAmplifier() <= amplifier).
                forEach(pe -> {
                    ent.removePotionEffect(type);
                });
        ent.addPotionEffect(new PotionEffect(type, 20 * seconds, amplifier, false, false));
    }

    private static final List<PotionEffectType> negative = Lists.newArrayList(PotionEffectType.POISON, PotionEffectType.WITHER, PotionEffectType.WEAKNESS, PotionEffectType.SLOW, PotionEffectType.SLOW_DIGGING, PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION, PotionEffectType.HARM, PotionEffectType.UNLUCK, PotionEffectType.HUNGER, PotionEffectType.LEVITATION, PotionEffectType.GLOWING);


    public static boolean isNegativeForPlayer(PotionEffectType type) {
        return negative.contains(type);
    }

    public static boolean isPositiveForPlayer(PotionEffectType type) {
        return !isNegativeForPlayer(type);
    }
}
