package com.minegusta.mgracesredone.races;

import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class Race
{
    public abstract double getHealth();

    public abstract String getName();

    public abstract String[] getInfectionInfo();

    public abstract String[] getInfo();

    public abstract void passiveBoost(Player p);

    public void setHealth(Player p)
    {
        p.setMaxHealth(getHealth());
    }

}
