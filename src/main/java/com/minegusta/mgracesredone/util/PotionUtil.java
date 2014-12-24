package com.minegusta.mgracesredone.util;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionUtil
{
    /**
     *
     * @param p The player involved.
     * @param type The type of the potion.
     * @param amplifier The amplifier.
     * @param seconds The duration in seconds.
     */
    public static void updatePotion(Player p, PotionEffectType type, int amplifier, int seconds)
    {
        for (PotionEffect pe : p.getActivePotionEffects()) {
            if (pe.getType().equals(type) && pe.getAmplifier() <= amplifier) {
                p.removePotionEffect(type);
            }
        }
        p.addPotionEffect(new PotionEffect(type, 20 * seconds, amplifier, false));
    }
}
