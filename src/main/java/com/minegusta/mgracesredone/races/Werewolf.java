package com.minegusta.mgracesredone.races;

import com.minegusta.mgracesredone.util.EffectUtil;
import com.minegusta.mgracesredone.util.PlayerUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.WeatherUtil;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Werewolf extends Race
{
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
                        "Werewolves can leap forward fast.",
                        "They will also be aided by wolves in combat wherever they go.",
                        "A Werewolf can also instantly add a wolf to his pack by taming them.",
                        "The wolves in the pack are stronger than regular ones.",
                        "When a werewolf crouch-right clicks one of his fighter wolves, the wolf will",
                        "die but the werewolf will absorb part of its health.",
                        "In combat, werewolves feel more comfortable using their claws at night.",
                        "Damage done by other items is reduced while fist damage is increased.",
                        "This only applies during the night.",
                        "When a werewolf crouches and clicks the air, they will leap forward.",
                        "During a full moon, the werewolf gets extreme power. This is when they are at their strongest.",
                        "Armour will weaken them during a full moon though.",
                        "When there is no moon, Werewolves have a weakness effect.",
                        "When there is a moon between new and full, werewolves gain boosts at night.",
                        "Werewolves have another weakness: gold.",
                        "When a werewolf is hit by golden weapons, they take insane damage.",
                };
    }

    @Override
    public void passiveBoost(Player p)
    {
        WeatherUtil.MoonPhase phase = WeatherUtil.getMoonPhase(p.getWorld());

        if(phase == WeatherUtil.MoonPhase.FULL)
        {
            EffectUtil.playParticle(p, Effect.WITCH_MAGIC);
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 2, 3);
            PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 2, 3);
            PotionUtil.updatePotion(p, PotionEffectType.JUMP, 2, 3);

            int armour = PlayerUtil.getArmorAmounr(p);
            if(armour != 0)
            {
                PotionUtil.updatePotion(p, PotionEffectType.WEAKNESS, armour - 1, 3);
            }
            else
            {
                PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 2, 3);
            }
        }
        else if(phase == WeatherUtil.MoonPhase.NEW)
        {
            PotionUtil.updatePotion(p, PotionEffectType.WEAKNESS, 0, 3);
        }
        else if(WeatherUtil.isNight(p.getWorld()))
        {
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 0, 3);
            PotionUtil.updatePotion(p, PotionEffectType.JUMP, 0 , 3);
            PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 0, 3);
            PotionUtil.updatePotion(p, PotionEffectType.NIGHT_VISION, 0 ,10);
        }
    }
}
