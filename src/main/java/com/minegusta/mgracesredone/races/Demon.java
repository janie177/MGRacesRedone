package com.minegusta.mgracesredone.races;

import org.bukkit.entity.Player;

public class Demon extends Race {
    @Override
    public double getHealth() {
        return 22;
    }

    @Override
    public String getName() {
        return "Demon";
    }

    @Override
    public String[] getInfectionInfo() {
        return new String[]
                {
                        "To become a Demon, you have to follow these steps:",
                        "Make an altar out of at least 55 obsidian blocks.",
                        "Get a baby sheep to stand on the altar.",
                        "Stand on your altar around the center.",
                        "Say: " + getChant(),
                        "The sheep will be sacrificed and you will be a Demon!"
                };
    }

    @Override
    public String[] getInfo() {
        return new String[]
                {
                        "Demons are the race from hell.",
                        "They feel at home in warmer environments, and have a natural",
                        "weakness for cold climates.",
                        "When Demons are in the Nether, they are at their strongest.",
                        "Fire or Lava cannot harm Demons. As a downside, water and rain are fatal.",
                        "Hellish minions will aid the Demons when he is in need."
                };
    }

    @Override
    public void passiveBoost(Player p) {

    }
    public static String getChant()
    {
        return "Flames shall consume the last light, in fire I bind my soul. ";
    }
}
