package com.minegusta.mgracesredone.races;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora.Glacious;
import com.minegusta.mgracesredone.util.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Aurora implements Race {
    @Override
    public double getHealth() {
        return 22;
    }

    @Override
    public String getName() {
        return "Aurora";
    }

    @Override
    public String[] getInfectionInfo() {
        return new String[]
                {
                        "To become an Aurora, you have to follow these steps:",
                        "Craft an ice crystal (/Race Recipes).",
                        "Drown in an ice biome with the crystal on you.",
                        "You are now an Aurora!."
                };
    }

    @Override
    public int getPerkPointCap() {
        return 26;
    }

    @Override
    public String[] getInfo() {
        return new String[]{
                "Aurora's are the race of ice and water.",
                "Their body temperature is to be kept low, or they will weaken.",
                "Aurora perks are split between ice biome and water benefits.",
                "They have a few strong abilities that involve mostly freezing."
        };
    }

    @Override
    public String getColoredName() {
        return ChatColor.DARK_AQUA + getName();
    }

    @Override
    public void passiveBoost(Player p) {
        MGPlayer mgp = Races.getMGPlayer(p);

        int feeshLevel = mgp.getAbilityLevel(AbilityType.Fish);

        if (PlayerUtil.isInWater(p) && feeshLevel > 0) {
            PotionUtil.updatePotion(p, PotionEffectType.WATER_BREATHING, 0, 10);
        }

        if (PlayerUtil.isInWater(p) && feeshLevel > 1) {
            PotionUtil.updatePotion(p, PotionEffectType.NIGHT_VISION, 0, 20);
        }

        if (feeshLevel > 4 && WeatherUtil.isFullMoon(p.getWorld())) {
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 1, 10);
            PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 10);
        }

        if (feeshLevel > 2 && (PlayerUtil.isInRain(p) || PlayerUtil.isInWater(p))) {
            PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 10);
            if (feeshLevel > 3) {
                PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 0, 10);
            }
        }

        WeatherUtil.BiomeType biome = WeatherUtil.getBiomeType(p.getLocation());

        int glaceLevel = mgp.getAbilityLevel(AbilityType.GLACIOUS);

        if (biome == WeatherUtil.BiomeType.ICE && glaceLevel > 0 && Glacious.isToggled(p)) {
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 0, 5);
            if (glaceLevel > 4) {
                PotionUtil.updatePotion(p, PotionEffectType.SPEED, 1, 5);
                PotionUtil.updatePotion(p, PotionEffectType.JUMP, 0, 5);
            }
            if (glaceLevel > 2) PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 5);
            if (glaceLevel > 3) PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 0, 5);
            EffectUtil.playParticle(p, Effect.SNOW_SHOVEL);
        } else if (glaceLevel > 0 && biome == WeatherUtil.BiomeType.COLD && Glacious.isToggled(p)) {
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 0, 5);
            EffectUtil.playParticle(p, Effect.SNOW_SHOVEL);
        } else if (biome == WeatherUtil.BiomeType.HOT || biome == WeatherUtil.BiomeType.WARM) {
            int heatresistLevel = mgp.getAbilityLevel(AbilityType.HEATTOLLERANCE);

            int slow = 2;
            int weakness = 2;
            int digging = 1;
            boolean confusion = heatresistLevel < 1;

            if (!confusion) {
                if (heatresistLevel > 2) weakness = 1;
                if (heatresistLevel > 1) slow = 0;
                if (heatresistLevel > 3) digging = 0;
            }

            PotionUtil.updatePotion(p, PotionEffectType.SLOW, slow, 5);
            WeaknessUtil.setWeakness(p, weakness + 1, 5);
            if (digging > 0) PotionUtil.updatePotion(p, PotionEffectType.SLOW_DIGGING, digging, 5);
            EffectUtil.playParticle(p, Effect.WATERDRIP);
            if (biome == WeatherUtil.BiomeType.HOT && confusion) {
                PotionUtil.updatePotion(p, PotionEffectType.CONFUSION, 0, 10);
            }
        }
    }
}
