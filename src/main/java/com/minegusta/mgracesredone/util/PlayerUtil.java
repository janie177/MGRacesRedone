package com.minegusta.mgracesredone.util;

import com.minegusta.mgracesredone.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtil
{
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
