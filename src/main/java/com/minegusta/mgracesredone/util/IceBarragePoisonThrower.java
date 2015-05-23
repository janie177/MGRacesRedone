package com.minegusta.mgracesredone.util;

import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class IceBarragePoisonThrower implements ProjectileSource
{
    /**
     * This class is here simply to assign to snowballs so they can play an effect in the aurora listener.
     */

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> aClass) {
        return null;
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> aClass, Vector vector) {
        return null;
    }
}
