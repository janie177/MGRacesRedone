package com.minegusta.mgracesredone.races.skilltree.abilities.perks.elf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.PotionUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class FruitFanatic implements IAbility {
    @Override
    public void run(Event event) {
        if (event instanceof PlayerItemConsumeEvent) {
            PlayerItemConsumeEvent e = (PlayerItemConsumeEvent) event;
            Player p = e.getPlayer();
            MGPlayer mgp = Races.getMGPlayer(p);
            int level = mgp.getAbilityLevel(getType());

            int duration1, duration2, amp1, amp2;

            switch (level) {
                case 1: {
                    amp1 = 0;
                    amp2 = 0;
                    duration1 = 2;
                    duration2 = 0;
                }
                break;
                case 2: {
                    amp1 = 0;
                    amp2 = 0;
                    duration1 = 4;
                    duration2 = 0;
                }
                break;
                case 3: {
                    amp1 = 0;
                    amp2 = 1;
                    duration1 = 6;
                    duration2 = 4;
                }
                break;
                case 4: {
                    amp1 = 0;
                    amp2 = 1;
                    duration1 = 6;
                    duration2 = 6;
                }
                break;
                case 5: {
                    amp1 = 0;
                    amp2 = 1;
                    duration1 = 6;
                    duration2 = 8;
                }
                break;
                default: {
                    amp1 = 0;
                    amp2 = 0;
                    duration1 = 2;
                    duration2 = 2;
                }
            }

            PotionUtil.updatePotion(p, PotionEffectType.REGENERATION, amp1, duration1);
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, amp2, duration2);
        }
    }

    @Override
    public boolean run(Player player) {
        return false;
    }

    @Override
    public String getName() {
        return "FruitFanatic";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.FRUITFANATIC;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.MELON;
    }

    @Override
    public int getPrice(int level) {
        return 1;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.PASSIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 0;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.ELF);
    }

    @Override
    public boolean canBind() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"When eating fruit, you gain 2 seconds of regeneration."};
                break;
            case 2:
                desc = new String[]{"When eating fruit, you gain 4 seconds of regeneration."};
                break;
            case 3:
                desc = new String[]{"When eating fruit, you gain 6 seconds of regeneration.", "You will also gain 4 seconds of speed II."};
                break;
            case 4:
                desc = new String[]{"When eating fruit, you gain 6 seconds of regeneration.", "You will also gain 6 seconds of speed II."};
                break;
            case 5:
                desc = new String[]{"When eating fruit, you gain 6 seconds of regeneration.", "You will also gain 8 seconds of speed II."};
                break;
            default:
                desc = new String[]{"This is an error.", "Report it to Jan!"};
                break;

        }
        return desc;
    }
}
