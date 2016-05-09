package com.minegusta.mgracesredone.util;

import com.google.common.collect.Lists;
import org.bukkit.Material;

import java.util.List;

public class BindUtil {

	private static final List<Material> ignoreData = Lists.newArrayList(Material.FLINT_AND_STEEL,
			Material.FISHING_ROD,
			Material.BOW,
			Material.ELYTRA,
			Material.CHAINMAIL_BOOTS,
			Material.CHAINMAIL_CHESTPLATE,
			Material.CHAINMAIL_HELMET,
			Material.CHAINMAIL_LEGGINGS,
			Material.DIAMOND_AXE,
			Material.DIAMOND_BOOTS,
			Material.DIAMOND_CHESTPLATE,
			Material.DIAMOND_HELMET,
			Material.DIAMOND_LEGGINGS,
			Material.DIAMOND_PICKAXE,
			Material.DIAMOND_SPADE,
			Material.DIAMOND_SWORD,
			Material.IRON_AXE,
			Material.IRON_BOOTS,
			Material.IRON_CHESTPLATE,
			Material.IRON_HELMET,
			Material.IRON_LEGGINGS,
			Material.IRON_PICKAXE,
			Material.IRON_SPADE,
			Material.IRON_SWORD,
			Material.GOLD_AXE,
			Material.GOLD_BOOTS,
			Material.GOLD_CHESTPLATE,
			Material.GOLD_HELMET,
			Material.GOLD_LEGGINGS,
			Material.GOLD_PICKAXE,
			Material.GOLD_SPADE,
			Material.GOLD_SWORD,
			Material.STONE_PICKAXE,
			Material.STONE_SPADE,
			Material.STONE_SWORD,
			Material.STONE_AXE,
			Material.WOOD_PICKAXE,
			Material.WOOD_SPADE,
			Material.WOOD_SWORD,
			Material.WOOD_AXE,
			Material.LEATHER_BOOTS,
			Material.LEATHER_CHESTPLATE,
			Material.LEATHER_HELMET,
			Material.LEATHER_LEGGINGS,
			Material.WOOD_HOE,
			Material.STONE_HOE,
			Material.IRON_HOE,
			Material.GOLD_HOE,
			Material.DIAMOND_HOE,
			Material.SHEARS);

	public static boolean ignoreItemData(Material m) {
		return ignoreData.contains(m);
	}
}
