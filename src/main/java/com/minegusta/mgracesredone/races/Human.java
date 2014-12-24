package com.minegusta.mgracesredone.races;

import org.bukkit.entity.Player;

public class Human extends Race {
    @Override
    public double getHealth() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String[] getInfectionInfo() {
        return new String[0];
    }

    @Override
    public String[] getInfo() {
        return new String[0];
    }

    @Override
    public void passiveBoost(Player p) {

    }
}
