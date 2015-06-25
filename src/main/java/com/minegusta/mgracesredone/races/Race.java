package com.minegusta.mgracesredone.races;

import org.bukkit.entity.Player;

public interface Race {
    double getHealth();

    String getName();

    String[] getInfectionInfo();

    int getPerkPointCap();

    String[] getInfo();

    void passiveBoost(Player p);

    default void setHealth(Player p) {
        p.setHealthScaled(true);
        p.setHealthScale(getHealth());
        p.setMaxHealth(getHealth());
    }
}
