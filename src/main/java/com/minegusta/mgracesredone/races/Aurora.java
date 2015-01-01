package com.minegusta.mgracesredone.races;

import com.minegusta.mgracesredone.util.*;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Aurora extends Race {
    @Override
    public double getHealth() {
        return 22;
    }

    @Override
    public String getName() {
        return "Aurora";
    }

    @Override
    public String[] getInfectionInfo()
    {
        return new String[]
                {
                "To become an Aurora, you have to follow these steps:",
                "Craft an ice crystal (/Race Recipes).",
                "Drown in an ice biome with the crystal on you.",
                "You are now an Aurora!."
        };
    }

    @Override
    public String[] getInfo() {
        return new String[]{
                "Aurora's are the race of ice and water.",
                "Their body temperature is to be kept low, or they will weaken.",
                "They are natural swimmers and can breathe underwater.",
                "They are at their strongest in ice environments.",
                "Aurora do not getConfig fall damage in snow.",
                "Snowballs can be used to slow and weaken enemies for a short period of time.",
                "Heat weakens them, so you wont find them near a desert or savannah.",
                "The moon empowers the Aurora. It will grant them more power.",
                "Aurora's can rely on the power of ice and traps."
        };
    }

    @Override
    public void passiveBoost(Player p) {

        Material mat = BlockUtil.getBlockAtLocation(p.getLocation());

        if(PlayerUtil.isInWater(p))
        {
            PotionUtil.updatePotion(p, PotionEffectType.WATER_BREATHING, 0, 3);
        }

        if(WeatherUtil.isFullMoon(p.getWorld()))
        {
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 1, 3);
            PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 3);
        }

        if(PlayerUtil.isInRain(p))
        {
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 0, 3);
            PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 3);
        }

        WeatherUtil.BiomeType biome = WeatherUtil.getBiomeType(p.getLocation());

        if(biome == WeatherUtil.BiomeType.ICE)
        {
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 1, 3);
            PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 3);
            PotionUtil.updatePotion(p, PotionEffectType.JUMP, 0, 3);
            PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 0, 3);
            EffectUtil.playParticle(p, Effect.SNOW_SHOVEL);
        }
        else if(biome == WeatherUtil.BiomeType.COLD)
        {
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 0, 3);
            EffectUtil.playParticle(p, Effect.SNOW_SHOVEL);
        }
        else if(biome == WeatherUtil.BiomeType.HOT || biome == WeatherUtil.BiomeType.WARM)
        {
            PotionUtil.updatePotion(p, PotionEffectType.SLOW, 1, 3);
            PotionUtil.updatePotion(p, PotionEffectType.WEAKNESS, 1, 3);
            PotionUtil.updatePotion(p, PotionEffectType.SLOW_DIGGING, 1, 3);
            EffectUtil.playParticle(p, Effect.WATERDRIP);
        }
    }
}
