package com.minegusta.mgracesredone.races;

import com.minegusta.mgracesredone.main.Races;
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

        double health = Races.getMGPlayer(p).getStoredHealth();

        if (health > p.getMaxHealth() || health == 0) {
            p.setMaxHealth(p.getMaxHealth());
        } else p.setHealth(health);
    }
}
