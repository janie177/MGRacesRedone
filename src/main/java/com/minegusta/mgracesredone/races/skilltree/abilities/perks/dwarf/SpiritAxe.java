package com.minegusta.mgracesredone.races.skilltree.abilities.perks.dwarf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class SpiritAxe implements IAbility
{

    @Override
    public void run(Event event) {

    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Spirit Axe";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.SPIRITAXE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.GOLD_AXE;
    }

    @Override
    public int getPrice(int level) {
        return 2;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.ACTIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 100;
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
            case 1: desc = new String[]{"Summon a flying iron axe that attacks your target.", "Activate by crouch hitting a target.", "Lasts for 6 seconds."};
                break;
            case 2: desc = new String[]{"Your axe has a strength boost."};
                break;
            case 3: desc = new String[]{"Your axe is now diamond instead of iron."};
                break;
            case 4: desc = new String[]{"Two weaker iron axes will be summoned besides your main axe."};
                break;
            case 5: desc = new String[]{"Your axe will not die as easily."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
