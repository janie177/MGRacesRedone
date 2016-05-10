package com.minegusta.mgracesredone.listeners.general;

import com.minegusta.mgracesredone.util.WeaknessUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class WeaknessListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onWeaknessAffectedDamageDealt(EntityDamageByEntityEvent e) {
		if (!WorldCheck.isEnabled(e.getEntity().getWorld()) || e.isCancelled()) return;

		if (e.getDamager() instanceof LivingEntity && e.getEntity() instanceof LivingEntity) {
			LivingEntity damager = (LivingEntity) e.getDamager();
			LivingEntity noob = (LivingEntity) e.getEntity();

			long damagerWeakness;
			if ((damagerWeakness = WeaknessUtil.getWeakness(damager)) > 0) {
				e.setDamage(e.getDamage() - damagerWeakness);
			}

			long noobWeakness;
			if ((noobWeakness = WeaknessUtil.getWeakness(noob)) > 0) {
				e.setDamage(e.getDamage() + noobWeakness);
			}

		}
	}
}
