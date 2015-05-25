package com.minegusta.mgracesredone.races;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.EffectUtil;
import com.minegusta.mgracesredone.util.ItemUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Dwarf extends Race {
    @Override
    public double getHealth() {
        return 24;
    }

    @Override
    public String getName() {
        return "Dwarf";
    }

    @Override
    public String[] getInfectionInfo() {
        return new String[]
                {
                        "To become a dwarf, follow these steps:",
                        "Gather 2 each of the following ores: Diamond, Emerald, Lapis and Gold.",
                        "Place all these ores close together with a diamond ore centered.",
                        "Craft a Shiny Gem (/Race Recipes).",
                        "Right click the diamond ore with your gem.",
                        "The ore shall vaporize and change the structure of your blood.",
                        "You are now a dwarf."
                };
    }

    @Override
    public String[] getInfo() {
        return new String[]
                {
                        "Dwarves are the proud people of the mountain halls.",
                        "They prefer to live underground, and mine alot.",
                        "While deep underground, dwarves gain more strength.",
                        "The Dwarf prefers axes above all other weapons, and is quite",
                        "skilled in combat with them.",
                        "Arrows and other projectiles are one of the few weaknesses of a Dwarf.",
                        "Dwarves have the blood of a miner, and mine faster than other races.",
                        "When a dwarf mines ore, they gain even more of a mining boost.",
                        "Killstreaks are a dwarf's speciality. When they kill a foe using",
                        "an axe, they gain more power.",
                        "They also possess the power to knock back enemies using their axe."

                };
    }

    @Override
    public void passiveBoost(Player p)
    {
        double height = p.getLocation().getY();
        MGPlayer mgp = Races.getMGPlayer(p);

        int tunnlerLevel = mgp.getAbilityLevel(AbilityType.TUNNLER);

        if(height < 50 && tunnlerLevel > 0)
        {
            if(height < 26)
            {
                if(tunnlerLevel > 3)
                {
                    PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 1, 5);
                    PotionUtil.updatePotion(p, PotionEffectType.FIRE_RESISTANCE, 0, 5);
                }
                else if(tunnlerLevel > 2)
                {
                    PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 1, 5);
                }
            }
            PotionUtil.updatePotion(p, PotionEffectType.DAMAGE_RESISTANCE, 0, 5);
            if(tunnlerLevel > 1) PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 0, 5);
        }

        int minerLevel = mgp.getAbilityLevel(AbilityType.MINER);

        if(minerLevel > 0 && ItemUtil.isPickAxe(p.getItemInHand().getType()))
        {
            PotionUtil.updatePotion(p, PotionEffectType.FAST_DIGGING, 2, 5);
        }
    }
}
