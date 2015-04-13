package com.minegusta.mgracesredone.races.skilltree.abilities.elf;

import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;

public class Ranger implements IAbility
{

    @Override
    public void run(int level) {

    }

    @Override
    public String getName() {
        return "Ranger";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.RANGER;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }
}
