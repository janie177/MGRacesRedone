package com.minegusta.mgracesredone.listeners.perkpoints;

import com.google.common.collect.Lists;
import org.bukkit.entity.Player;

import java.util.List;

public class KillData
{
    private List<String> killedPlayers = Lists.newArrayList();
    private String uuid;

    public KillData(Player p)
    {
        this.uuid = p.getUniqueId().toString();
    }

    public void addPlayerToKilled(Player p)
    {
       killedPlayers.add(p.getUniqueId().toString());
    }

    public void addPlayerToKilled(String uuid)
    {
        killedPlayers.add(uuid);
    }

    public boolean hasKilled(Player p)
    {
        return killedPlayers.contains(p.getUniqueId().toString());
    }

    public boolean hasKilled(String uuid)
    {
        return killedPlayers.contains(uuid);
    }



}
