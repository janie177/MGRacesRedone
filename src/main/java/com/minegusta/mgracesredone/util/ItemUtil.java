package com.minegusta.mgracesredone.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemUtil
{
    public static void removeOne(Player p, Material m)
    {
        int slot = 0;
        for(ItemStack i : p.getInventory().getContents())
        {
            if(i != null && i.getType() == m)
            {
                if(i.getAmount() > 1)
                {
                    i.setAmount(i.getAmount() - 1);
                }
                else
                {
                    p.getInventory().setItem(slot, new ItemStack(Material.AIR));
                }
                break;
            }
            slot++;
        }
        p.updateInventory();
    }

    public static void removeOne(Player p, ItemStack is)
    {
        int slot = 0;
        for(ItemStack i : p.getInventory().getContents())
        {
            if(i != null && i.equals(is))
            {
                if(i.getAmount() > 1)
                {
                    i.setAmount(i.getAmount() - 1);
                }
                else
                {
                    p.getInventory().setItem(slot, new ItemStack(Material.AIR));
                }
                break;
            }
            slot++;
        }
        p.updateInventory();
    }
}
