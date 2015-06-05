package com.minegusta.mgracesredone.races;


import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class EnderBorn extends Race {
    @Override
    public double getHealth() {
        return 20;
    }

    @Override
    public String getName() {
        return "Enderborn";
    }

    @Override
    public String[] getInfectionInfo() {
        return new String[]
                {
                        "To become Enderborn, follow these steps:",
                        "Craft an Ender Crystal (/Race recipe).",
                        "Use the Ender Crystal on an enderman.",
                        "Maintain the spiritual connection. Don't wander too far!",
                        "After a wile, your souls will merge and you are Enderborn."
                };
    }

    @Override
    public String[] getInfo() {
        return new String[]
        {
                "Enderborn are a sneaky race from The End.",
                "They are stealth assassins that eat raw flesh.",
                "Most perks are End and stealth related."

        };
    }

    @Override
    public void passiveBoost(Player p)
    {
        Location loc = p.getLocation();
        WeatherUtil.BiomeType biome = WeatherUtil.getBiomeType(loc);
        MGPlayer mgp = Races.getMGPlayer(p);

        //End biome check
        if(WeatherUtil.isEnd(loc))
        {
            int level = mgp.getAbilityLevel(AbilityType.OTHERWORLDLY);
            if(level > 0)
            {
                EffectUtil.playParticle(p, Effect.PORTAL);
                PotionUtil.updatePotion(p, PotionEffectType.SPEED, 0, 5);
                if(level > 1)
                {
                    PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 0, 5);
                    if(level > 2)
                    {
                        PotionUtil.updatePotion(p, PotionEffectType.REGENERATION, 0, 5);
                        if(level > 3)
                        {
                            PotionUtil.updatePotion(p, PotionEffectType.JUMP, 0, 5);
                            if(level > 4)
                            {
                                PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 5);
                            }
                        }
                    }
                }
            }
        }

        //Water and rain damage checks. Stacks.
        if(PlayerUtil.isInRain(p) && WGUtil.canGetDamage(p) &&  biome != WeatherUtil.BiomeType.HOT && biome != WeatherUtil.BiomeType.WARM)
        {
            int damage = 2;
            if(mgp.getAbilityLevel(AbilityType.WATERRESISTANCE) > 0) damage = 1;
            p.damage(damage);
        }
        if(PlayerUtil.isInWater(p) && WGUtil.canGetDamage(p))
        {
            int damage = 2;
            if(mgp.getAbilityLevel(AbilityType.WATERRESISTANCE) > 1) damage = 1;
            p.damage(damage);
        }

        //Darkness check
        if(BlockUtil.getLightLevel(loc) == BlockUtil.LightLevel.DARK && mgp.hasAbility(AbilityType.COLDBLOODED))
        {
            PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 5);
            if(mgp.getAbilityLevel(AbilityType.COLDBLOODED) > 1)
            {
                PotionUtil.updatePotion(p, PotionEffectType.NIGHT_VISION, 0, 18);
            }
        }
    }
}
