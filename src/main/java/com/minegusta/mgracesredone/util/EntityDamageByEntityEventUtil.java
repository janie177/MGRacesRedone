package com.minegusta.mgracesredone.util;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageByEntityEventUtil {

    public static EntityDamageByEntityEvent createEvent(LivingEntity damager, LivingEntity damagee, EntityDamageEvent.DamageCause cause, double damage) {
        //Return a fresh new Event.
        return new EntityDamageByEntityEvent(damager, damagee, cause, damage);
    }
}
