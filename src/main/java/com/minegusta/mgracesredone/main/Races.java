package com.minegusta.mgracesredone.main;

import com.minegusta.mgracesredone.data.Storage;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class Races
{
    /**
     *
     * This class is here to supply all the basic methods in a central location.
     *
     */



    public static Plugin getPlugin()
    {
        return Main.getPlugin();
    }

    public static RaceType getRace(UUID uuid)
    {
        return getRace(uuid.toString());
    }

    public static RaceType getRace(Player p)
    {
        return getRace(p.getUniqueId().toString());
    }

    public static RaceType getRace(String uuid)
    {
        return Storage.getRace(uuid);
    }

    public static void setRace(Player p, RaceType race)
    {
        Storage.getPlayer(p).setRaceType(race);
    }

    public static void setRace(UUID uuid, RaceType race)
    {
        Storage.getPlayer(uuid.toString()).setRaceType(race);
    }

    public static void setRace(String uuid, RaceType race)
    {
        Storage.getPlayer(uuid).setRaceType(race);
    }

    public static MGPlayer getMGPlayer(Player p)
    {
        return Storage.getPlayer(p);
    }

    public static void savePlayerFile(String uuid)
    {
        Storage.getPlayer(uuid).saveFile();
    }
}
