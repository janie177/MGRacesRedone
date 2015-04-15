package com.minegusta.mgracesredone.races.skilltree.abilities;

import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.elf.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public enum AbilityType
{
    RANGER(new Ranger()),
    FORESTFRIEND(new ForestFriend()),
    ARROWRAIN(new ArrowRain()),
    POINTYSHOOTY(new PointyShooty()),
    ANIMALRIDER(new AnimalRider()),
    NATURALIST(new Naturalist()),
    FLAMERESISTANCE(new FlameResistance()),
    FRUITFANATIC(new FruitFanatic());


    private IAbility ability;

    private AbilityType(IAbility ability)
    {
        this.ability = ability;
    }

    public IAbility getAbility()
    {
        return ability;
    }

    public String getName()
    {
        return ability.getName();
    }

    public List<RaceType> getRaces()
    {
        return ability.getRaces();
    }

    public int getCost(int level)
    {
        return ability.getPrice(level);
    }

    public int getID()
    {
        return ability.getID();
    }

    public int getMaxLevel()
    {
        return ability.getMaxLevel();
    }

    public void run(Player player)
    {
        ability.run(player);
    }

    public void run(Event event)
    {
        ability.run(event);
    }
}
