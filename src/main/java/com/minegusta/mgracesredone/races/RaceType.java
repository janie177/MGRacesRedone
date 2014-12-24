package com.minegusta.mgracesredone.races;

import org.bukkit.entity.Player;

public enum RaceType
{
    HUMAN(new Human()),
    ELF(new Elf()),
    DWARF(new Dwarf()),
    WEREWOLF(new Werewolf()),
    AURORA(new Aurora()),
    ENDERBORN(new EnderBorn()),
    DEMON(new Demon());

    private Race race;

    private RaceType(Race race)
    {
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

    public void passiveBoost(Player p)
    {
        race.passiveBoost(p);
    }

    public void setHealth(Player p)
    {
        race.setHealth(p);
    }
}
