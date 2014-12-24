package com.minegusta.mgracesredone.util;

import com.minegusta.mgracesredone.data.Storage;
import com.minegusta.mgracesredone.files.FileManager;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Races
{
    /**
     *
     * This class is here to supply all the basic methods in a central location.
     *
     */

    private static String[] raceAmounts;


    public static Plugin getPlugin()
    {
        return Main.getPlugin();
    }

    public static RaceType getRace(Player p)
    {
        return RaceCheck.getRace(p);
    }

    public static void setRace(Player p, RaceType race)
    {
        Storage.getPlayer(p).setRaceType(race);
    }

    public static MGPlayer getMGPlayer(Player p)
    {
        return Storage.getPlayer(p);
    }

    public static void countRaces()
    {
        int count = 0;
        String[] list = new String[RaceType.values().length];

        for(RaceType type : RaceType.values())
        {
            int amount = 0;
            for(String s : FileManager.get().getKeys(false))
            {
                if(FileManager.get().getString(s).equalsIgnoreCase(type.name()))
                {
                    amount++;
                }
            }
            String info = type.getName() + ": " + ChatColor.YELLOW + amount;
            list[count] = info;
            count++;
        }

        raceAmounts = list;
    }

    public static String[] getRaceAmounts()
    {
        return raceAmounts;
    }
}
