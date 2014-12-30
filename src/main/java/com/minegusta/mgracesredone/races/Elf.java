package com.minegusta.mgracesredone.races;

import com.minegusta.mgracesredone.util.*;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Elf extends Race {
    @Override
    public double getHealth() {
        return 20;
    }

    @Override
    public String getName() {
        return "Elf";
    }

    @Override
    public String[] getInfectionInfo() {
        return new String[]
                {
                        "To become an Elf, follow these steps:",
                        "Get a bow, and kill 100 creatures with it.",
                        "It will announce how many kills you have.",
                        "When you have 100 kills, craft an Elf Stew.",
                        "To see the recipe for Elf Stew, type /Race Recipes",
                        "Now eat the stew and you will be an Elf."
                };
    }

    @Override
    public String[] getInfo() {
        return new String[]
                {
                        "Elves are closely related to nature.",
                        "They gain regeneration from eating vegetarian food.",
                        "Elves regenerate in water and are skilled with bows.",
                        "All bow damage is thus increased.",
                        "Elves are masters of taming.",
                        "Fire is one of the weaknesses to elves, though the sun empowers them.",
                        "Elves are most active during the day.",
                        "Elves may have less health than other races, but they are also fast.",
                        "They can avoid face-to-face combat by running and using a bow.",
                        "Other than that they feel at home in neutral temperature biomes."
                };
    }

    @Override
    public void passiveBoost(Player p) {

        Location loc = p.getLocation();
        Material mat = BlockUtil.getBlockAtLocation(loc);
        WeatherUtil.BiomeType biome = WeatherUtil.getBiomeType(loc);

        if(PlayerUtil.isInRain(p) && PlayerUtil.isInWater(p))
        {
            EffectUtil.playParticle(p, Effect.HEART);
            PotionUtil.updatePotion(p, PotionEffectType.REGENERATION, 0, 2);
        }

        if(biome == WeatherUtil.BiomeType.NEUTRAL && WeatherUtil.isDay(p.getWorld()))
        {
            EffectUtil.playParticle(p, Effect.HAPPY_VILLAGER);
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 1, 3);
            PotionUtil.updatePotion(p, PotionEffectType.JUMP, 1, 3);
        }
        else
        {
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 0, 3);
            PotionUtil.updatePotion(p, PotionEffectType.JUMP, 0, 3);
        }
    }
}
