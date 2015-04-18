package com.minegusta.mgracesredone.util;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentMap;

public class DemonPowers
{

    private static ConcurrentMap<String, DemonPower> demonMap = Maps.newConcurrentMap();

    public enum DemonPower
    {
        UNHOLY_RAIN(1, AbilityType.UNHOLYRAIN),HELL_RIFT(2, AbilityType.HELLRIFT),METEOR_STORM(3, AbilityType.METEORSTORM);

        private int index;
        private AbilityType type;

        private DemonPower(int index, AbilityType type)
        {
            this.index = index;
            this.type = type;
        }

        public AbilityType getType()
        {
            return type;
        }

        public int getIndex()
        {
            return index;
        }

        public static DemonPower getFromIndex(int i, Player p)
        {
            for(DemonPower power : DemonPower.values())
            {
                if(power.getIndex() > i && Races.getMGPlayer(p).hasAbility(power.getType()))
                {
                    p.sendMessage(ChatColor.RED + "You are now using " + power.getType().getName() + ".");
                    return power;
                }
            }
            return UNHOLY_RAIN;
        }

        public static DemonPower getNext(Player p)
        {
            return getFromIndex(getPower(p).getIndex(), p);
        }
    }

    public static DemonPower getPower(Player p)
    {
        String uuid = p.getUniqueId().toString();

        if(!demonMap.containsKey(uuid))
        {
            MGPlayer mgp = Races.getMGPlayer(p);
            if(mgp.hasAbility(DemonPower.HELL_RIFT.getType())) demonMap.put(uuid, DemonPower.HELL_RIFT);
            else if(mgp.hasAbility(DemonPower.UNHOLY_RAIN.getType())) demonMap.put(uuid, DemonPower.UNHOLY_RAIN);
            else if(mgp.hasAbility(DemonPower.METEOR_STORM.getType())) demonMap.put(uuid, DemonPower.METEOR_STORM);
        }
        return demonMap.get(uuid);
    }

    public static DemonPower nextPower(Player p)
    {
        demonMap.put(p.getUniqueId().toString(), DemonPower.getNext(p));
        return demonMap.get(p.getUniqueId().toString());
    }


}
