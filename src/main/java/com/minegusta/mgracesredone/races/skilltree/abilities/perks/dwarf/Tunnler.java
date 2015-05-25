package com.minegusta.mgracesredone.races.skilltree.abilities.perks.dwarf;


import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.EffectUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Tunnler implements IAbility
{

    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player)
    {

        //Give effects when the player hits a gold block.
        PotionUtil.updatePotion(player, PotionEffectType.SPEED, 2, 5);
        PotionUtil.updatePotion(player, PotionEffectType.JUMP, 1, 5);
        EffectUtil.playParticle(player, Effect.FLAME);

    }

    @Override
    public String getName() {
        return "Tunnler";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.TUNNLER;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.IRON_HELMET;
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
        return Lists.newArrayList(RaceType.DWARF);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level)
        {
            case 1: desc = new String[]{"When underground, you gain a defensive boost."};
                break;
            case 2: desc = new String[]{"When underground, you gain a strength boost."};
                break;
            case 3: desc = new String[]{"At extreme depths you will gain a stronger defensive boost."};
                break;
            case 4: desc = new String[]{"At extreme depths you will gain a resistance to fire."};
                break;
            case 5: desc = new String[]{"When hitting a gold block, you will gain a speed and jump boost."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
