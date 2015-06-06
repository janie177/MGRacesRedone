package com.minegusta.mgracesredone.races;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.*;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Demon implements Race {
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
                        "Say: " + ChatColor.DARK_GRAY + getChant(),
                        "The sheep will be sacrificed and you will be a Demon!"
                };
    }

    @Override
    public String[] getInfo() {
        return new String[]
                {
                        "Demons are the race from hell.",
                        "Their perks are nether-based.",
                        "Heat is a demon's best friend."
                };
    }

    @Override
    public void passiveBoost(Player p) {
        Location loc = p.getLocation();
        WeatherUtil.BiomeType biome = WeatherUtil.getBiomeType(loc);

        MGPlayer mgp = Races.getMGPlayer(p);

        //Check for obsidian
        if (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.OBSIDIAN && mgp.hasAbility(AbilityType.HELLSPAWN) && mgp.getAbilityLevel(AbilityType.HELLSPAWN) > 1) {
            PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 5);
        }

        if (WeatherUtil.isHell(loc)) {
            if (mgp.hasAbility(AbilityType.HELLSPAWN)) {
                int level = mgp.getAbilityLevel(AbilityType.HELLSPAWN);
                EffectUtil.playParticle(p, Effect.MOBSPAWNER_FLAMES);

                if (level > 2) {
                    PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 0, 5);
                }
                if (level > 3) {
                    PotionUtil.updatePotion(p, PotionEffectType.SPEED, 2, 5);
                }
                if (level > 4) {
                    PotionUtil.updatePotion(p, PotionEffectType.JUMP, 2, 5);
                    PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 5);
                }
            }

        } else if (biome == WeatherUtil.BiomeType.HOT || biome == WeatherUtil.BiomeType.WARM) {
            if (mgp.hasAbility(AbilityType.ENVIRONMENTALIST) && mgp.getAbilityLevel(AbilityType.ENVIRONMENTALIST) > 4) {
                EffectUtil.playParticle(p, Effect.MOBSPAWNER_FLAMES);
                PotionUtil.updatePotion(p, PotionEffectType.SPEED, 0, 5);
            }
        } else if (biome == WeatherUtil.BiomeType.COLD || biome == WeatherUtil.BiomeType.ICE) {
            int slowAmp = 1;
            if (mgp.hasAbility(AbilityType.ENVIRONMENTALIST)) {
                int level = mgp.getAbilityLevel(AbilityType.ENVIRONMENTALIST);
                slowAmp = 0;
                if (level < 2) {
                    PotionUtil.updatePotion(p, PotionEffectType.CONFUSION, 0, 5);
                }
            }
            PotionUtil.updatePotion(p, PotionEffectType.WEAKNESS, 1, 5);
            PotionUtil.updatePotion(p, PotionEffectType.SLOW, slowAmp, 5);
            EffectUtil.playParticle(p, Effect.LAVADRIP);
        } else {
            EffectUtil.playParticle(p, Effect.LAVADRIP);
            PotionUtil.updatePotion(p, PotionEffectType.WEAKNESS, 0, 5);
        }


        if ((PlayerUtil.isInWater(p) && WGUtil.canGetDamage(p))) {
            int damage = 2;
            if (mgp.hasAbility(AbilityType.ENVIRONMENTALIST) && mgp.getAbilityLevel(AbilityType.ENVIRONMENTALIST) > 3)
                damage = 1;
            p.damage(damage);
        } else if (PlayerUtil.isInRain(p) && WGUtil.canGetDamage(p) && biome != WeatherUtil.BiomeType.HOT && biome != WeatherUtil.BiomeType.WARM) {
            int damage = 2;
            if (mgp.hasAbility(AbilityType.ENVIRONMENTALIST) && mgp.getAbilityLevel(AbilityType.ENVIRONMENTALIST) > 2)
                damage = 1;
            p.damage(damage);
        }
    }

    public static String getChant() {
        return "Flames shall consume the last light, in fire I bind my soul.";
    }
}
