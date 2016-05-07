package com.minegusta.mgracesredone.listeners.racelisteners;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.util.RandomUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class VampireListener implements Listener {

	private static final ItemStack monsterBloodEssence = new ItemStack(Material.INK_SACK, 1, (short) 1) {
		{
			ItemMeta meta = getItemMeta();
			meta.setLore(Lists.newArrayList(ChatColor.RED + "Monster Blood Essence"));
			meta.setDisplayName(ChatColor.RED + "Monster Blood Essence");
			setItemMeta(meta);
		}
	};

	private static final ItemStack humanBloodEssence = new ItemStack(Material.INK_SACK, 1, (short) 1) {
		{
			ItemMeta meta = getItemMeta();
			meta.setLore(Lists.newArrayList(ChatColor.RED + "Human Blood Essence"));
			meta.setDisplayName(ChatColor.RED + "Human Blood Essence");
			setItemMeta(meta);
		}
	};

	//Drop blood essence
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		if (!WorldCheck.isEnabled(e.getEntity().getWorld())) {
			return;
		}

		if (e.getEntity() instanceof Player && RandomUtil.chance(10)) {
			//Drop item
			e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), humanBloodEssence);

		} else if (e.getEntity() instanceof Monster && RandomUtil.chance(3)) {
			//Drop item
			e.getEntity().getWorld().dropItemNaturally(e.getEntity().getLocation(), monsterBloodEssence);
		}
	}
}
