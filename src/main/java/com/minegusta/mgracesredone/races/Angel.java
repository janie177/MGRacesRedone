package com.minegusta.mgracesredone.races;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.BlockUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.WeatherUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Angel implements Race {
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
    public int getPerkPointCap() {
        return 26;
    }

    @Override
    public String[] getInfo() {
        return new String[]
                {
                        "Angels are holy mysterious creatures.",
                        "They prefer to live in the sky.",
                        "Though mostly passive and defensive, angels have some strong perks.",
                        "Most of them related to light and height.",
                };
    }

    @Override
    public String getColoredName() {
        return ChatColor.YELLOW + getName();
    }

    @Override
    public void passiveBoost(Player p) {
        int height = (int) p.getLocation().getY();

        MGPlayer mgp = Races.getMGPlayer(p);

        //Weakness in the nether and end
        if (WeatherUtil.isHell(p.getLocation()) || WeatherUtil.isEnd(p.getLocation())) {
            PotionUtil.updatePotion(p, PotionEffectType.SLOW_DIGGING, 0, 5);
            if (mgp.getAbilityLevel(AbilityType.NYCTOPHOBIA) < 2) {
                PotionUtil.updatePotion(p, PotionEffectType.CONFUSION, 0, 10);
            }
            PotionUtil.updatePotion(p, PotionEffectType.WEAKNESS, 2, 5);
        }


        int holinesslevel = mgp.getAbilityLevel(AbilityType.HOLINESS);

        if (height > 100 && holinesslevel > 3) {
            PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 5);
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 0, 5);
            PotionUtil.updatePotion(p, PotionEffectType.JUMP, 2, 5);
        }
        //Weak in low areas and dark ones
        else if (height < 50) {
            PotionUtil.updatePotion(p, PotionEffectType.WEAKNESS, 1, 5);
        } else if (BlockUtil.getLightLevel(p.getLocation()) == BlockUtil.LightLevel.DARK && (height < 100 || holinesslevel < 3)) {
            int strength = 1;
            if (mgp.getAbilityLevel(AbilityType.NYCTOPHOBIA) > 0) {
                strength = 0;
            }
            PotionUtil.updatePotion(p, PotionEffectType.WEAKNESS, strength, 5);
        }

        //At low health they will be able to escape
        if (p.getHealth() <= 6 && holinesslevel > 4) {
            PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 5);
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 1, 5);
        }

        //Heal in light areas with the holiness perk.
        if (BlockUtil.getLightLevel(p.getLocation()) == BlockUtil.LightLevel.LIGHT && mgp.getAbilityLevel(AbilityType.HOLINESS) > 1) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 3, 0, false, false));
        }
    }
}
