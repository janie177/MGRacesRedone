package com.minegusta.mgracesredone.races.skilltree.abilities.perks.dwarf;


import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.omg.PortableInterceptor.ACTIVE;

import java.util.List;

public class BattleCry implements IAbility
{

    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Battle Cry";
    }

    @Override
    public AbilityType getType() {
        return null;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.IRON_AXE;
    }

    @Override
    public int getPrice(int level) {
        return 1;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.ACTIVE;
    }

    @Override
    public int getCooldown(int level)
    {
        if(level > 4)return 50;
        return 80;
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
            case 1: desc = new String[]{"Yell to intimidate enemies, knocking them back.", "Activate by right clicking an axe."};
                break;
            case 2: desc = new String[]{"Affected enemies are now stunned for 4 seconds."};
                break;
            case 3: desc = new String[]{"Enemies are weakened for 4 seconds."};
                break;
            case 4: desc = new String[]{"The knock-back effect is 50% stronger."};
                break;
            case 5: desc = new String[]{"The cool-down is reduced to 50 seconds."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
