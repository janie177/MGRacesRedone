package com.minegusta.mgracesredone.listeners.bind;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.Bind;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.BindUtil;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.Cooldown;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Optional;

public class BindListener implements Listener {

	@EventHandler
	public void onBindClick(PlayerInteractEvent e) {

		Player p = e.getPlayer();
		if (!WorldCheck.isEnabled(p.getWorld()) || e.getHand() != EquipmentSlot.HAND) return;

		MGPlayer mgp = Races.getMGPlayer(p);

		Material item = e.hasItem() ? e.getItem().getType() : Material.AIR;
		short data = item != Material.AIR ? e.getItem().getDurability() : 0;
		boolean ignoreData = BindUtil.ignoreItemData(item);

		Optional<Bind> b = mgp.getBindForItem(item, data, ignoreData);
		if (!b.isPresent()) return;

		Bind bind = b.get();
		AbilityType active = bind.getActive();

		if (active.canBind() && mgp.hasAbility(active)) {

			if (active == AbilityType.ENDRIFT && e.getAction() != Action.PHYSICAL) {
				//EndRift has special activation and controls. Has to be an exception.
				active.run(e);
				return;
			}

			if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (bind.scrollActive()) {
					p.sendMessage(ChatColor.DARK_PURPLE + "[" + ChatColor.LIGHT_PURPLE + "MG" + ChatColor.DARK_PURPLE + "] " + ChatColor.GREEN + "You selected " + ChatColor.LIGHT_PURPLE + bind.getActive() + ChatColor.GREEN + ".");
				}
				return;
			}

			if ((e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)) return;

			//----// COOL DOWN //----//
			String name = active.name();
			String uuid = p.getUniqueId().toString();
			if (!Cooldown.isCooledDown(name, uuid)) {
				ChatUtil.sendString(p, "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use " + active.getName() + ".");
				return;
			}
			if (active != AbilityType.TELEKINESIS) ChatUtil.sendString(p, "You used " + active.getName() + "!");
			//----//           //----//

			//Start cooldown if ability returns true. Returns false when blocked by worldguard for example.
			if (active.run(p)) {
				Cooldown.newCoolDown(name, uuid, active.getCooldown(mgp.getAbilityLevel(active)));
			}
		}


	}

	@EventHandler
	public void onBindClickEntity(PlayerInteractEntityEvent e) {

		Player p = e.getPlayer();
		if (!WorldCheck.isEnabled(p.getWorld()) || e.isCancelled()) return;
		MGPlayer mgp = Races.getMGPlayer(p);

		Material item = e.getPlayer().getInventory().getItemInMainHand().getType();
		short data = e.getPlayer().getInventory().getItemInMainHand().getDurability();
		boolean ignoreData = BindUtil.ignoreItemData(item);

		Optional<Bind> b = mgp.getBindForItem(item, data, ignoreData);
		if (!b.isPresent()) return;
		Bind bind = b.get();

		AbilityType active = bind.getActive();

		if (active.canBind() && mgp.hasAbility(active)) {

			if (active == AbilityType.ENDRIFT) {
				//Do not do anything for end rift, only activate on the other method above.
				return;
			}

			//----// COOL DOWN //----//
			String name = active.name();
			String uuid = p.getUniqueId().toString();
			if (!Cooldown.isCooledDown(name, uuid)) {
				ChatUtil.sendString(p, "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use " + active.getName() + ".");
				return;
			}
			if (active != AbilityType.TELEKINESIS) ChatUtil.sendString(p, "You used " + active.getName() + "!");
			Cooldown.newCoolDown(name, uuid, active.getCooldown(mgp.getAbilityLevel(active)));
			//----//           //----//

			//Start cooldown if ability returns true. Returns false when blocked by worldguard for example.
			if (active.run(p)) {
				Cooldown.newCoolDown(name, uuid, active.getCooldown(mgp.getAbilityLevel(active)));
			}
		}
	}
}
