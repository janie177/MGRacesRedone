package com.minegusta.mgracesredone.races;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.EffectUtil;
import com.minegusta.mgracesredone.util.PlayerUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.WeatherUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Werewolf implements Race {
    @Override
    public double getHealth() {
        return 22;
    }

    @Override
    public String getName() {
        return "Werewolf";
    }

    @Override
    public String[] getInfectionInfo() {
        return new String[]
                {
                        "To become a Werewolf, follow these steps:",
                        "Craft a Wolf Bone: /Race Recipes.",
                        "Use the bone on a wolf during a full moon.",
                        "You will absorb the wolf aspect into your body.",
                        "You are now a werewolf."
                };
    }

    @Override
    public String[] getInfo() {
        return new String[]
                {
                        "Werewolves are a mythical race.",
                        "They are closely related to wolves, and possess some of their powers.",
                        "Werewolves are weak to gold and have a disadvantage when there's no moon.",
                        "Most perks are related to the night and moon.",
                        "During a full moon, werewolves should not wear armour. It weakens them.",
                        "during a full moon, anything other than claws will do only half damage.",
                        "It is strongly advised to use your claws during a full moon.",
                        "Not wearing armour during a full moon will give you strong defensive boosts.",
                        "The nether and end do not have nights, and thus do not give the night boosts."
                };
    }

    @Override
    public String getColoredName() {
        return ChatColor.BLACK + getName();
    }

    @Override
    public int getPerkPointCap() {
        return 26;
    }

    @Override
    public void passiveBoost(Player p) {
        WeatherUtil.MoonPhase phase = WeatherUtil.getMoonPhase(p.getWorld());
        MGPlayer mgp = Races.getMGPlayer(p);

        if (!WeatherUtil.isOverWorld(p.getLocation())) return;

        if (phase == WeatherUtil.MoonPhase.FULL && WeatherUtil.isNight(p.getWorld())) {
            if (mgp.hasAbility(AbilityType.LUNA)) {
                int level = mgp.getAbilityLevel(AbilityType.LUNA);
                EffectUtil.playParticle(p, Effect.WITCH_MAGIC);
                PotionUtil.updatePotion(p, PotionEffectType.SPEED, 2, 5);

                if (level > 1) {
                    PotionUtil.updatePotion(p, PotionEffectType.JUMP, 2, 5);
                    if (level > 2) {
                        PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 2, 5);
                        if (level > 3) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 1, 6));
                        }
                    }
                }
            }


            int armour = PlayerUtil.getArmorAmount(p);
            if (armour != 0) {
                PotionUtil.updatePotion(p, PotionEffectType.WEAKNESS, (armour * 2) + 2, 5);
            } else {
                PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 2, 5);
            }
        } else if (phase == WeatherUtil.MoonPhase.NEW) {
            PotionUtil.updatePotion(p, PotionEffectType.WEAKNESS, 2, 5);
        }

        if (WeatherUtil.isNight(p.getWorld()) && mgp.hasAbility(AbilityType.NOCTURNAL)) {
            int level = mgp.getAbilityLevel(AbilityType.NOCTURNAL);

            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 1, 5);
            if (level > 1) {
                PotionUtil.updatePotion(p, PotionEffectType.NIGHT_VISION, 0, 20);
                if (level > 2) {
                    PotionUtil.updatePotion(p, PotionEffectType.JUMP, 0, 5);
                    if (level > 3) {
                        PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 0, 5);
                    }
                }
            }
        }
    }
}
