package com.minegusta.mgracesredone.util;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemUtil
{

    //Static lists
    private final static List<Material> axes = Lists.newArrayList(Material.DIAMOND_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.WOOD_AXE, Material.STONE_AXE);
    private final static List<Material> swords = Lists.newArrayList(Material.DIAMOND_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOOD_SWORD);
    private final static List<Material> bows = Lists.newArrayList(Material.BOW);
    private final static List<Material> goldItems = Lists.newArrayList(Material.GOLD_SWORD, Material.GOLD_AXE, Material.GOLD_HOE, Material.GOLD_PICKAXE, Material.GOLD_SPADE);
    private final static List<Material> pickAxes = Lists.newArrayList(Material.DIAMOND_PICKAXE, Material.GOLD_PICKAXE, Material.IRON_PICKAXE, Material.STONE_PICKAXE, Material.WOOD_PICKAXE);
    private final static List<Material> fruits = Lists.newArrayList(Material.APPLE, Material.CARROT_ITEM, Material.POTATO_ITEM, Material.GOLDEN_APPLE, Material.GOLDEN_CARROT, Material.COOKIE, Material.MUSHROOM_SOUP, Material.MELON, Material.PUMPKIN_PIE);
    private final static List<Material> rawMeat = Lists.newArrayList(Material.RAW_BEEF, Material.RAW_CHICKEN, Material.RAW_FISH, Material.PORK);
    private static final List<Material> ores = Lists.newArrayList(Material.DIAMOND_ORE, Material.IRON_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.EMERALD_ORE, Material.COAL_ORE, Material.REDSTONE_ORE, Material.GLOWING_REDSTONE_ORE, Material.QUARTZ_ORE);

    //Methods


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

    public static boolean isRawMeat(Material m)
    {
        return rawMeat.contains(m);
    }

    public static boolean isAxe(Material m)
    {
        return axes.contains(m);
    }

    public static boolean isOre(Material m)
    {
        return ores.contains(m);
    }

    public static boolean isGoldTool(Material m)
    {
        return goldItems.contains(m);
    }

    public static boolean isBow(Material m)
    {
        return bows.contains(m);
    }

    public static boolean isPickAxe(Material m)
    {
        return pickAxes.contains(m);
    }

    public static boolean isSword(Material m)
    {
        return swords.contains(m);
    }

    public static boolean isFruit(Material m)
    {
        return fruits.contains(m);
    }
}
