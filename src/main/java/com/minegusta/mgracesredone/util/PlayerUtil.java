package com.minegusta.mgracesredone.util;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerUtil
{

    private static final List<RaceType> unholy = Lists.newArrayList(RaceType.DEMON, RaceType.ENDERBORN, RaceType.WEREWOLF);

    public static boolean isHoly(Player p)
    {
        return !isUnholy(p);
    }

    public static boolean isUnholy(Player p)
    {
        return unholy.contains(Races.getRace(p));
    }

    public static boolean isInOpenAir(Player p)
    {
        return BlockUtil.getHighestBlockYAt(p.getLocation()) <= p.getEyeLocation().getY();
    }

    public static boolean isInRain(Player p)
    {
        return WeatherUtil.isRaining(p.getWorld()) && PlayerUtil.isInOpenAir(p);
    }

    public static boolean isInWater(Player p)
    {
        Material mat = p.getLocation().getBlock().getType();
        return mat == Material.WATER || mat == Material.STATIONARY_WATER;
    }

    public static boolean isInLava(Player p)
    {
        Material mat = p.getLocation().getBlock().getType();
        return mat == Material.LAVA || mat == Material.STATIONARY_LAVA;
    }

    public static int getArmorAmounr(Player p)
    {
        int amount = 0;
        for(ItemStack is : p.getInventory().getArmorContents())
        {
            if(is != null && is.getType() != Material.AIR) amount++;
        }
        return amount;
    }
}
