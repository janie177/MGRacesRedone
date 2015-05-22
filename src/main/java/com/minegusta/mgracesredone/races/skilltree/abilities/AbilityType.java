package com.minegusta.mgracesredone.races.skilltree.abilities;

import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.angel.*;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora.*;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.demon.*;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.elf.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public enum AbilityType
{
    AQUAMAN(new AquaMan()),
    DROWNINGPOOL(new DrowningPool()),
    FEESH(new Feesh()),
    FROST(new Frost()),
    GLACIOUS(new Glacious()),
    HEATTOLLERANCE(new HeatTollerance()),
    ICEBARRAGE(new IceBarrage()),
    TIDALWAVE(new TidalWave()),
    GLIDE(new Glide()),
    HOLYNESS(new Holyness()),
    HOLYRAIN(new HolyRain()),
    JUSTICE(new Justice()),
    NYCTOPHOBIA(new Nyctophobia()),
    PRAYER(new Prayer()),
    PURGE(new Purge()),
    STEELSKIN(new SteelSkin()),
    WHIRLWIND(new WhirlWind()),
    FIREPROOF(new FireProof()),
    HELLRIFT(new HellRift()),
    HELLISHTRUCE(new HellishTruce()),
    HELLSPAWN(new HellSpawn()),
    LAVALOVER(new LavaLover()),
    METEORSTORM(new MeteorStorm()),
    MINIONMASTER(new MinionMaster()),
    UNHOLYRAIN(new UnholyRain()),
    ENVIRONMENTALIST(new Environmentalist()),
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

    public int getCooldown(int level)
    {
        return ability.getCooldown(level);
    }

    public IAbility.AbilityGroup getGroup()
    {
        return ability.getGroup();
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
