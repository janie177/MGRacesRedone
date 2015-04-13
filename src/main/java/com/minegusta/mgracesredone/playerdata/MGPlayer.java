package com.minegusta.mgracesredone.playerdata;

import com.minegusta.mgracesredone.data.Storage;
import com.minegusta.mgracesredone.files.FileManager;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MGPlayer
{

    private String uuid;
    private String name;
    private RaceType raceType;
    private FileConfiguration conf;

    private void buildMGPlayer(String uuid, FileConfiguration f)
    {
        this.uuid = uuid;
        this.name = Bukkit.getPlayer(UUID.fromString(uuid)).getName();
        this.conf = f;
        this.raceType = RaceType.valueOf(conf.getString("racetype", "HUMAN"));
        updateHealth();
    }

    public MGPlayer(Player p, FileConfiguration f)
    {
        buildMGPlayer(p.getUniqueId().toString(), f);
    }

    public MGPlayer(UUID uuid, FileConfiguration f)
    {
        buildMGPlayer(uuid.toString(), f);
    }

    public MGPlayer(String uuid, FileConfiguration f)
    {
        buildMGPlayer(uuid, f);
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
        updateHealth();
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
        getPlayer().setHealthScaled(true);
        getPlayer().setHealthScale(20);
        getPlayer().setMaxHealth(20);
    }

    public double getHealth()
    {
        return getPlayer().getHealth();
    }

    //Update all the values here
    public void updateConfig()
    {
        conf.set("racetype", raceType.name());
    }

    public FileConfiguration getConfig()
    {
        updateConfig();
        return conf;
    }

    public void saveFile()
    {
        updateConfig();
        FileManager.save(uuid, conf);
    }

    public void updateHealth()
    {
        if(WorldCheck.isEnabled(getPlayer().getWorld()))
        {
            setHealth();
            return;
        }
        restoreHealth();
    }
}
