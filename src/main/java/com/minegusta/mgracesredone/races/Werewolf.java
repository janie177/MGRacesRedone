package com.minegusta.mgracesredone.races;

import org.bukkit.entity.Player;

public class Werewolf extends Race
{
    @Override
    public double getHealth() {
        return 22;
    }

    @Override
    public String getName() {
        return "Werewolf";
    }

    @Override
    public String[] getInfectionInfo() {
        return new String[]
                {
                        "To become a Werewolf, follow these steps:",
                        "Craft a Wolf Bone: /Race Recipes.",
                        "Use the bone on a wolf during a full moon.",
                        "You will absorb the wolf aspect into your body.",
                        "You are now a werewolf."
                };
    }

    @Override
    public String[] getInfo() {
        return new String[]
                {
                        "Werewolves are a mythical race.",
                        "They are closely related to wolves, and possess some of their powers.",
                        "Werewolves can leap forward fast.",
                        "They will also be aided by wolves in combat wherever they go.",
                        "A Werewolf can also instantly add a wolf to his pack by taming them.",
                        "The wolves in the pack are stronger than regular ones.",
                        "When a werewolf crouch-right clicks one of his fighter wolves, the wolf will",
                        "die but the werewolf will absorb part of its health.",
                        "Werewolves will even take the shape of a wolf during a full moon.",
                        "This is when they are at their strongest.",
                        "When there is moon, they are weakened slightly.",
                        "Werewolves have one weakness: gold.",
                        "When a werewolf is hit by golden weapons, they take insane damage.",
                };
    }

    @Override
    public void passiveBoost(Player p) {

    }
}
