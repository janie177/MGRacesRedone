package com.minegusta.mgracesredone.races;

import com.minegusta.mgracesredone.util.BlockUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.WeatherUtil;
import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;


public class Angel extends Race {
    @Override
    public double getHealth() {
        return 20;
    }

    @Override
    public String getName() {
        return "Angel";
    }

    @Override
    public String[] getInfectionInfo() {
        return new String[]
                {
                        "To become an Angel, follow these steps:",
                        "Craft a Holy Feather (/Race Recipe).",
                        "Fall to your death while holding the feather.",
                        "You are now an angel."
                };
    }

    @Override
    public String[] getInfo() {
        return new String[]
                {
                        "Angels are holy mysterious creatures.",
                        "They live in the sky and are peaceful.",
                        "Angels and unholy creatures do not go together well.",
                        "Angels prefer to live in light areas at great heights.",
                        "Angels can float through the sky using feathers.",
                        "When an Angel is low on health, they gain boosts to flee.",
                        "Right clicking with a sword while crouching will activate",
                        "holy rain. This will damage unholy creatures and heal others.",
                        "In dark areas and on lower grounds, Angels are weakened.",
                        "Another downside is that angels can only do damage when",
                        "holding a sword or bow."
                };
    }

    @Override
    public void passiveBoost(Player p)
    {
        int height = (int) p.getLocation().getY();

        //Weakness in the nether and end
        if(WeatherUtil.isHell(p.getLocation()) || WeatherUtil.isEnd(p.getLocation()))
        {
            PotionUtil.updatePotion(p, PotionEffectType.SLOW_DIGGING, 2, 5);
            PotionUtil.updatePotion(p, PotionEffectType.WEAKNESS, 2, 5);
        }


        //String at high areas
        if(height > 90)
        {
            PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 5);
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 0, 5);
            PotionUtil.updatePotion(p, PotionEffectType.JUMP, 2, 5);
        }
        //Weak in low areas and dark ones
        else if(height < 50 || BlockUtil.getLightLevel(p.getLocation()) == BlockUtil.LightLevel.DARK)
        {
            PotionUtil.updatePotion(p, PotionEffectType.WEAKNESS, 1, 5);
        }

        //At low health they will be able to escape
        if(p.getHealth() < 6)
        {
            PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 5);
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 1, 5);
        }

        if(BlockUtil.getLightLevel(p.getLocation()) == BlockUtil.LightLevel.LIGHT)
        {
            PotionUtil.updatePotion(p, PotionEffectType.REGENERATION, 0, 4);
        }

    }
}
