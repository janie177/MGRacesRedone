package com.minegusta.mgracesredone.listeners.bind;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.BindUtil;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.Cooldown;
import com.minegusta.mgracesredone.util.WorldCheck;
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
		if (!WorldCheck.isEnabled(p.getWorld()) || e.isCancelled() || e.getHand() != EquipmentSlot.HAND) return;

		MGPlayer mgp = Races.getMGPlayer(p);

		Material item = e.getItem().getType();
		short data = e.getItem().getDurability();
		boolean ignoreData = BindUtil.ignoreItemData(item);

		Optional<AbilityType> ability = mgp.getBindForItem(item, data, ignoreData);

		if (ability.isPresent() && ability.get().canBind() && mgp.hasAbility(ability.get())) {

			if (ability.get() == AbilityType.ENDRIFT && e.getAction() != Action.PHYSICAL) {
				//EndRift has special activation and controls. Has to be an exception.
				ability.get().run(e);
				return;
			}

			if ((e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)) return;

			AbilityType a = ability.get();
			//----// COOL DOWN //----//
			String name = a.name();
			String uuid = p.getUniqueId().toString();
			if (!Cooldown.isCooledDown(name, uuid)) {
				ChatUtil.sendString(p, "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use " + a.getName() + ".");
				return;
			}
			if (ability.get() != AbilityType.TELEKINESIS) ChatUtil.sendString(p, "You used " + a.getName() + "!");
			//----//           //----//

			//Start cooldown if ability returns true. Returns false when blocked by worldguard for example.
			if (ability.get().run(p)) {
				Cooldown.newCoolDown(name, uuid, a.getCooldown(mgp.getAbilityLevel(a)));
			}
		}


	}

	@EventHandler
	public void onBindClickEntity(PlayerInteractEntityEvent e) {

		Player p = e.getPlayer();
		if (!WorldCheck.isEnabled(p.getWorld()) || e.isCancelled() || e.getHand() != EquipmentSlot.HAND) return;
		MGPlayer mgp = Races.getMGPlayer(p);

		Material item = e.getPlayer().getInventory().getItemInMainHand().getType();
		short data = e.getPlayer().getInventory().getItemInMainHand().getDurability();
		boolean ignoreData = BindUtil.ignoreItemData(item);

		Optional<AbilityType> ability = mgp.getBindForItem(item, data, ignoreData);

		if (ability.isPresent() && ability.get().canBind() && mgp.hasAbility(ability.get())) {

			if (ability.get() == AbilityType.ENDRIFT) {
				//Do not do anything for end rift, only activate on the other method above.
				return;
			}

			AbilityType a = ability.get();
			//----// COOL DOWN //----//
			String name = a.name();
			String uuid = p.getUniqueId().toString();
			if (!Cooldown.isCooledDown(name, uuid)) {
				ChatUtil.sendString(p, "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use " + a.getName() + ".");
				return;
			}
			ChatUtil.sendString(p, "You used " + a.getName() + "!");
			Cooldown.newCoolDown(name, uuid, a.getCooldown(mgp.getAbilityLevel(a)));
			//----//           //----//

			//Start cooldown if ability returns true. Returns false when blocked by worldguard for example.
			if (ability.get().run(p)) {
				Cooldown.newCoolDown(name, uuid, a.getCooldown(mgp.getAbilityLevel(a)));
			}
		}
	}
}
