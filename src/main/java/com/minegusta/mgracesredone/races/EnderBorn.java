package com.minegusta.mgracesredone.races;


import org.bukkit.entity.Player;

public class EnderBorn extends Race {
    @Override
    public double getHealth() {
        return 22;
    }

    @Override
    public String getName() {
        return "Enderborn";
    }

    @Override
    public String[] getInfectionInfo() {
        return new String[]
                {
                        "To become Enderborn, follow these steps:",
                        "Craft an Ender Crystal (/Race recipes).",
                        "Use the Ender Crystal on an enderman.",
                        "Maintain the spiritual connection. Don't wander too far!",
                        "After a wile, your souls will merge and you are Enderborn."
                };
    }

    @Override
    public String[] getInfo() {
        return new String[]
        {
                "Enderborn are a sneaky race from The End.",
                "They pray on weak animals and gain strength from",
                "eating raw meat.",
                "Enderborn can turn to shadow to stay unseen.",
                "The sharp claws of the enderborn will cause bleeding to opponents.",
                "Though strong, they also have a weakness. Like all creatures from The End,",
                "they fear water most. Rain and water burn through the Enderborn like lava."

        };
    }

    @Override
    public void passiveBoost(Player p) {

    }
}
