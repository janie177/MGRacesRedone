package com.minegusta.mgracesredone.playerdata;

import com.minegusta.mgracesredone.files.FileManager;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MGPlayer
{

    private String uuid;
    private String name;
    private RaceType raceType;

    public MGPlayer(UUID uuid)
    {
        this.uuid = uuid.toString();
        this.name = Bukkit.getPlayer(uuid).getName();
        this.raceType = FileManager.getRace(uuid.toString());
        updateHealth();
    }

    public MGPlayer(String uuid)
    {
        this.uuid = uuid;
        this.name = Bukkit.getPlayer(uuid).getName();
        this.raceType = FileManager.getRace(uuid);
        updateHealth();
    }

    public MGPlayer(Player p)
    {
        this.uuid = p.getUniqueId().toString();
        this.name = p.getName();
        this.raceType = FileManager.getRace(uuid);
        updateHealth();
    }

    public Player getPlayer()
    {
        return Bukkit.getPlayer(UUID.fromString(uuid));
    }

    public RaceType getRaceType()
    {
        return raceType;
    }

    public void setRaceType(RaceType raceType)
    {
        this.raceType = raceType;
        FileManager.setRace(uuid, raceType);
    }

    public UUID getUniqueId()
    {
        return UUID.fromString(uuid);
    }

    public String getUniqueIdAsString()
    {
        return uuid;
    }

    public String getName()
    {
        return name;
    }

    public void setHealth()
    {
        getRaceType().setHealth(getPlayer());
    }

    public void restoreHealth()
    {
        getPlayer().setHealth(20);
        getPlayer().setHealthScale(20);
    }

    public double getHealth()
    {
        return getPlayer().getHealth();
    }

    public void updateHealth()
    {
        if(WorldCheck.isEnabled(getPlayer().getWorld()))
        {
            setHealth();
        }
        restoreHealth();
    }
}
