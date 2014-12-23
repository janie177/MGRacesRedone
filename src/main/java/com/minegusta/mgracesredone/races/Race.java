package com.minegusta.mgracesredone.races;

import org.bukkit.entity.Player;

public abstract class Race
{
    public abstract double getHealth();

    public abstract String getName();

    public abstract String[] getInfo();

    public abstract void passiveBoost(Player p);

    public abstract void setHealth(Player p);

}
