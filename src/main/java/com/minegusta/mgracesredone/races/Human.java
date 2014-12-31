package com.minegusta.mgracesredone.races;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.listeners.cure.CureListener;
import com.minegusta.mgracesredone.util.MGItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class Human extends Race
{
    @Override
    public double getHealth() {
        return 26;
    }

    @Override
    public String getName() {
        return "Human";
    }

    @Override
    public String[] getInfectionInfo()
    {
        List<String> list = Lists.newArrayList(
                "To become a human once more, you have to build an altar.",
                "The altar will need the following blocks:",
                " - " + ChatColor.GRAY + "1 " + CureListener.getAltarBlock().name(),
                " - " + ChatColor.GRAY + CureListener.getSecondaryBlockAmount() + " " + CureListener.getSecondaryBlock().name(),
                "After you made the altar, right click it to be cured.",
                "You will need the following items in your inventory:"
                );
        for(MGItem item : CureListener.getRequiredItems())
        {
            list.add(" - " + ChatColor.GRAY + item.getAmount() + " " + item.getMaterial());
        }

        return list.toArray(new String[list.size()]);
    }

    @Override
    public String[] getInfo() {
        return new String[]
                {
                        "Humans are the default race.",
                        "Humans have no real downsides nor advantages.",
                        "When you cure yourself at a cure altar, you become Human."
                };
    }

    @Override
    public void passiveBoost(Player p) {

        //You have no power here, Gandalf Greyhame!

    }



}
