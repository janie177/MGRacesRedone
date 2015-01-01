package com.minegusta.mgracesredone.races;

import com.minegusta.mgracesredone.util.EffectUtil;
import com.minegusta.mgracesredone.util.PlayerUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.WeatherUtil;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Demon extends Race {
    @Override
    public double getHealth() {
        return 22;
    }

    @Override
    public String getName() {
        return "Demon";
    }

    @Override
    public String[] getInfectionInfo() {
        return new String[]
                {
                        "To become a Demon, you have to follow these steps:",
                        "Make an altar out of at least 55 obsidian blocks.",
                        "Get a baby sheep to stand on the altar.",
                        "Stand on your altar around the center.",
                        "Say: " + getChant(),
                        "The sheep will be sacrificed and you will be a Demon!"
                };
    }

    @Override
    public String[] getInfo() {
        return new String[]
                {
                        "Demons are the race from hell.",
                        "They feel at home in warmer environments, and have a natural",
                        "weakness for cold climates.",
                        "When Demons are in the Nether, they are at their strongest.",
                        "In hell, most mobs are friendly towards the Demon.",
                        "In the overworld, Demons are weakened. only the hottest biomes may support them.",
                        "Fire or Lava cannot harm Demons. As a downside, water and rain are fatal.",
                        "Hellish minions will aid the Demon when he is in need."
                };
    }

    @Override
    public void passiveBoost(Player p)
    {
        Location loc = p.getLocation();
        WeatherUtil.BiomeType biome = WeatherUtil.getBiomeType(loc);

        if(WeatherUtil.isHell(loc))
        {
            EffectUtil.playParticle(p, Effect.MOBSPAWNER_FLAMES);
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 1, 3);
            PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 3);
            PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 0, 3);
        }
        else if(biome == WeatherUtil.BiomeType.HOT)
        {
            EffectUtil.playParticle(p, Effect.MOBSPAWNER_FLAMES);
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 0, 3);
        }
        else if(biome == WeatherUtil.BiomeType.COLD || biome == WeatherUtil.BiomeType.ICE)
        {
            EffectUtil.playParticle(p, Effect.LAVADRIP);
            PotionUtil.updatePotion(p, PotionEffectType.SLOW, 1, 3);
            PotionUtil.updatePotion(p, PotionEffectType.WEAKNESS, 1, 3);
            PotionUtil.updatePotion(p, PotionEffectType.CONFUSION, 0, 3);
        }
        else
        {
            EffectUtil.playParticle(p, Effect.LAVADRIP);
            PotionUtil.updatePotion(p, PotionEffectType.WEAKNESS, 0, 3);
        }

        if(PlayerUtil.isInWater(p) || PlayerUtil.isInRain(p))
        {
            p.damage(1.0);
        }

        PotionUtil.updatePotion(p, PotionEffectType.FIRE_RESISTANCE, 1, 3);

    }
    public static String getChant()
    {
        return "Flames shall consume the last light, in fire I bind my soul.";
    }
}
