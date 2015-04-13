package com.minegusta.mgracesredone.playerdata;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.files.FileManager;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.manager.AbilityFileManager;
import com.minegusta.mgracesredone.util.WorldCheck;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class MGPlayer
{

    private String uuid;
    private String name;
    private RaceType raceType;
    private FileConfiguration conf;
    private int perkpoints;
    private ConcurrentMap<AbilityType, Integer> abilities = Maps.newConcurrentMap();

    private void buildMGPlayer(String uuid, FileConfiguration f)
    {
        this.uuid = uuid;
        this.name = Bukkit.getPlayer(UUID.fromString(uuid)).getName();
        this.conf = f;
        this.raceType = RaceType.valueOf(conf.getString("racetype", "HUMAN"));
        this.perkpoints = conf.getInt("perkpoints", 0);
        updateHealth();
        AbilityFileManager.loadAbilities(this);
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


    //------------------------------------------------------------------------------//

    public void addAbility(AbilityType type, int level)
    {
        abilities.put(type, level);
    }

    public void removeAbility(AbilityType type)
    {
        if(abilities.containsKey(type));
    }

    public void clearAbilities()
    {
        abilities.clear();
    }

    public boolean hasAbility(AbilityType ability)
    {
        return abilities.containsKey(ability);
    }

    public ConcurrentMap<AbilityType, Integer> getAbilities()
    {
        return abilities;
    }

    public int getAbilityLevel(AbilityType ability)
    {
        try {
            return abilities.get(ability);
        } catch (Exception Ignored)
        {
            return 0;
        }
    }

    public int getPerkPoints()
    {
        return perkpoints;
    }

    public void setPerkPoints(int newPoints)
    {
        this.perkpoints = newPoints;
    }

    public void addPerkPoints(int added)
    {
        perkpoints = perkpoints + added;
    }

    //Only removes points when you can afford it (wont drop below 0).
    public boolean removePerkPoints(int removed)
    {
        if(perkpoints - removed >= 0)
        {
            perkpoints = perkpoints - removed;
            return true;
        }
        return false;
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
        perkpoints = 0;
        abilities.clear();
        saveFile();
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
        conf.set("perkpoints", perkpoints);
        AbilityFileManager.saveAbilities(this);
    }

    public FileConfiguration getConfig()
    {
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
