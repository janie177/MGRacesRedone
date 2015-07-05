package com.minegusta.mgracesredone.races;

import org.bukkit.entity.Player;

public interface Race {
    double getHealth();

    String getName();

    String[] getInfectionInfo();

    int getPerkPointCap();

    String[] getInfo();

    void passiveBoost(Player p);

    default void setHealth(Player p, double health) {
        p.setHealthScaled(true);
        p.setHealthScale(getHealth());
        p.setMaxHealth(getHealth());

        if (health > p.getMaxHealth() || health == 0) {
            p.setMaxHealth(p.getMaxHealth());
        } else p.setHealth(health);
    }

    String getColoredName();
}
