package com.minegusta.mgracesredone.listeners.cure;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CureListener implements Listener {
    private static Material altarBlock = Material.EMERALD_BLOCK;
    private static Material secondaryBlock = Material.QUARTZ_BLOCK;
    private static MGItem[] requiredItems = {new MGItem(Material.DIAMOND, 5), new MGItem(Material.EMERALD, 5), new MGItem(Material.MILK_BUCKET, 1), new MGItem(Material.BONE, 5)};
    private static int radius = 6;
    private static int secondaryBlockAmount = 12;


    @EventHandler
    public void onCure(PlayerInteractEvent event) {
        Player p = event.getPlayer();

        if (!WorldCheck.isEnabled(p.getWorld())) return;

        if (Races.getRace(p) == RaceType.HUMAN) return;

        if (event.hasBlock() && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getType() != altarBlock) return;

            if (BlockUtil.radiusCheck(event.getClickedBlock(), radius, secondaryBlock, secondaryBlockAmount)) {
                for (MGItem item : requiredItems) {
                    if (!p.getInventory().containsAtLeast(new ItemStack(item.getMaterial()), item.getAmount())) {
                        ChatUtil.sendString(p, "You do not have the required items to use this altar.");
                        return;
                    }
                }
                for (MGItem item : requiredItems) {
                    ItemUtil.removeAmount(p, item.getMaterial(), item.getAmount());
                }
                ChatUtil.sendString(p, "You are now human!");
                EffectUtil.playSound(p, Sound.VILLAGER_YES);
                EffectUtil.playParticle(p, Effect.ENDER_SIGNAL, 1, 1, 1, 6);
                EffectUtil.playParticle(p, Effect.POTION_SWIRL_TRANSPARENT, 1, 1, 1, 30);
                Races.setRace(p, RaceType.HUMAN);
            }

        }
    }

    public static MGItem[] getRequiredItems() {
        return requiredItems;
    }

    public static int getSecondaryBlockAmount() {
        return secondaryBlockAmount;
    }

    public static Material getAltarBlock() {
        return altarBlock;
    }

    public static Material getSecondaryBlock() {
        return secondaryBlock;
    }


}
