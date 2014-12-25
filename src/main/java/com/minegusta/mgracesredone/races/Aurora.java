package com.minegusta.mgracesredone.races;

import org.bukkit.entity.Player;

public class Aurora extends Race {
    @Override
    public double getHealth() {
        return 22;
    }

    @Override
    public String getName() {
        return "Aurora";
    }

    @Override
    public String[] getInfectionInfo()
    {
        return new String[]
                {
                "To become an Aurora, you have to follow these steps:",
                "Craft an ice crystal (/Race Recipes).",
                "Drown in an ice biome with the crystal on you.",
                "You are now an Aurora!."
        };
    }

    @Override
    public String[] getInfo() {
        return new String[]{
                "Aurora's are the race of ice and water.",
                "Their body temperature is to be kept low, or they will weaken.",
                "They are natural swimmers and can breath underwater.",
                "They are at their strongest in ice environments.",
                "Heat weakens them, so you wont find them near a desert or savannah.",
                "The moon empowers the Aurora. It will grant them more power.",
                "Aurora's can rely on the power of ice and traps."
        };
    }

    @Override
    public void passiveBoost(Player p) {

    }
}
