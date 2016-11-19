package com.minegusta.mgracesredone.util;

import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class ElfShooter implements ProjectileSource {
    private String uuid;

    public ElfShooter(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
        return null;
    }

    @Override
    public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity) {
        return null;
    }

    public String getUuid() {
        return uuid;
    }
}
