package com.minegusta.mgracesredone.util;

import org.bukkit.Material;

public class MGItem {
    private Material material;
    private int amount;
    private String lore = "";
    private String name = "";

    public MGItem(Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public MGItem(Material material, int amount, String lore, String name) {
        this.material = material;
        this.amount = amount;
        this.name = name;
        this.lore = lore;
    }


    public int getID() {
        return material.getId();
    }

    public String getLore() {
        return lore;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }
}
