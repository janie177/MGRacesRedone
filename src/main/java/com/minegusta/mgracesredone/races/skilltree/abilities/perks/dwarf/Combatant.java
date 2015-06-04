package com.minegusta.mgracesredone.races.skilltree.abilities.perks.dwarf;


import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.EffectUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Combatant implements IAbility
{

    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player)
    {
        EffectUtil.playSound(player, Sound.ANVIL_USE);

        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());


        //The boosts

        PotionUtil.updatePotion(player, PotionEffectType.INCREASE_DAMAGE, 0, 6);

        if(level > 1)
        {
            PotionUtil.updatePotion(player, PotionEffectType.SPEED, 0, 7);
        }
        if(level > 2)
        {
            PotionUtil.updatePotion(player, PotionEffectType.INCREASE_DAMAGE, 1, 7);
        }
        if(level > 3)
        {
            PotionUtil.updatePotion(player, PotionEffectType.REGENERATION, 0, 5);
        }

    }

    @Override
    public String getName() {
        return "Combatant";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.COMBATANT;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.STONE_AXE;
    }

    @Override
    public int getPrice(int level) {
        if(level == 4)return 2;
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
        return Lists.newArrayList(RaceType.DWARF);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level)
        {
            case 1: desc = new String[]{"When killing something, you gain a short strength boost."};
                break;
            case 2: desc = new String[]{"Killing an enemy grants you a short speed boost."};
                break;
            case 3: desc = new String[]{"When you kill an enemy, you gain a short speed 2 boost."};
                break;
            case 4: desc = new String[]{"You gain a short regeneration effect after killing an enemy."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
