package com.minegusta.mgracesredone.util;

import org.bukkit.Material;

public class MGItem {
    private Material material;
    private int amount;

    public MGItem(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }


    public int getID() {
        return material.getId();
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }
}
