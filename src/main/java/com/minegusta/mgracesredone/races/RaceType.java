package com.minegusta.mgracesredone.races;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;

public enum RaceType
{
    HUMAN(new Human(), ChatColor.GRAY + "[Hu]"),
    ELF(new Elf(), ChatColor.GREEN + "[El]"),
    DWARF(new Dwarf(), ChatColor.DARK_GRAY + "[Dw]"),
    WEREWOLF(new Werewolf(), ChatColor.DARK_GRAY + "[WW]"),
    AURORA(new Aurora(), ChatColor.AQUA + "[Au]"),
    ENDERBORN(new EnderBorn(), ChatColor.DARK_PURPLE + "[EB]"),
    ANGEL(new Angel(), ChatColor.YELLOW + "[An]"),
    DEMON(new Demon(), ChatColor.RED + "[De]");

    private Race race;
    private String tag;

    private RaceType(Race race, String tag)
    {
        this.tag = tag;
        this.race = race;
    }

    public double getHealth()
    {
        return race.getHealth();
    }

    public String getName()
    {
        return race.getName();
    }

    public String[] getInfo()
    {
        return race.getInfo();
    }

    public String getTag()
    {
        return tag;
    }

    public String[] getInfectionInfo()
    {
        return race.getInfectionInfo();
    }

    public void passiveBoost(Player p)
    {
        race.passiveBoost(p);
    }

    public void setHealth(Player p)
    {
        race.setHealth(p);
    }
}
