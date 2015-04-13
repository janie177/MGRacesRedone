package com.minegusta.mgracesredone.races.skilltree.abilities;

import com.minegusta.mgracesredone.races.RaceType;

import java.util.List;

public interface IAbility
{
    public void run(int level);

    public String getName();

    public AbilityType getType();

    public int getID();

    public int getPrice();

    public List<RaceType> getRaces();

    public int getMaxLevel();
}
