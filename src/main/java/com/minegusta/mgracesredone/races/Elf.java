package com.minegusta.mgracesredone.races;

import org.bukkit.entity.Player;

public class Elf extends Race {
    @Override
    public double getHealth() {
        return 20;
    }

    @Override
    public String getName() {
        return "Elf";
    }

    @Override
    public String[] getInfectionInfo() {
        return new String[]
                {
                        "To become an Elf, follow these steps:",
                        "Get a bow, and kill 100 creatures with it.",
                        "It will announce how many kills you have.",
                        "When you have 100 kills, craft an Elf Stew.",
                        "To see the recipe for Elf Stew, type /Race Recipes",
                        "Now eat the stew and you will be an Elf."
                };
    }

    @Override
    public String[] getInfo() {
        return new String[]
                {
                        "Elves are closely related to nature.",
                        "They gain regeneration from eating fruits.",
                        "Elves regenerate in water and are skilled with bows.",
                        "All bow damage is thus increased.",
                        "Animals will follow elves into battle. Elves are masters of taming.",
                        "Fire is one of the weaknesses to elves, though the sun empowers them.",
                        "Elves are most active during the day.",
                        "Elves may have less health than other races, but they are also fast.",
                        "They can avoid face-to-face combat by running and using a bow."
                };
    }

    @Override
    public void passiveBoost(Player p) {

    }
}
