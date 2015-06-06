package com.minegusta.mgracesredone.util;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
}
