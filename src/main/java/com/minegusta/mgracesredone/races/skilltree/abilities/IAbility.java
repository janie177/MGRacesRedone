package com.minegusta.mgracesredone.races.skilltree.abilities;

public interface IAbility
{
    public void run(int level);

    public String getName();

    public AbilityType getType();

    public int getID();

    public int getMaxLevel();
}
