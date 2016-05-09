package com.minegusta.mgracesredone.races.skilltree.racemenu;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MenuListener implements Listener {
    @EventHandler
    public void buyItem(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getName() != null && e.getInventory().getName().contains(ChatColor.YELLOW + "Perk Shop. " + ChatColor.RED + "Perk-Points: ")) {
            if (!e.getCursor().getType().equals(Material.AIR)) {
                e.setCancelled(true);
                p.updateInventory();
                return;
            }
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) {
                e.setCancelled(true);
                return;
            }
            if (!e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName()) {
                e.setCancelled(true);
                return;
            }
            if (!WorldCheck.isEnabled(e.getWhoClicked().getWorld())) {
                ChatUtil.sendString((Player) e.getWhoClicked(), "This is not a races world!");
                e.setCancelled(true);
                return;
            }
            apply(p, e.getCurrentItem());
            e.setCancelled(true);
        }
    }

    private static void apply(Player p, ItemStack i) {
        MGPlayer mgp = Races.getMGPlayer(p);
        AbilityType bought = null;

        if (i == AbilityMenu.getResetStack()) {
            p.chat("/perkreset");
            p.closeInventory();
            return;
        }
        if (i == AbilityMenu.getInfoStack(mgp)) {
            return;
        }

        for (AbilityType ability : AbilityType.values()) {
            if (!(ChatColor.stripColor(ability.getAbility().getName()).equals(ChatColor.stripColor(i.getItemMeta().getDisplayName()))))
                continue;
            bought = ability;
            break;
        }

        if (bought == null) return;

        int pointsPresent = mgp.getPerkPoints();
        List<String> lore = i.getItemMeta().getLore();
        int level = Integer.parseInt(ChatColor.stripColor(lore.get(1).replace("Level: ", "")));

        int totalSpentPoints = AbilityMenu.getTotalSpentBasePoints(mgp);

        //The cap for perks

        /*int extraPerkPoints = 0;

        for (PermissionAttachmentInfo info : p.getEffectivePermissions()) {
            if (info.getPermission().toLowerCase().startsWith("mgraces.perks.")) {
                try {
                    int extra = Integer.parseInt(info.getPermission().toLowerCase().replace("mgraces.perks.", ""));
                    if (extra > extraPerkPoints) extraPerkPoints = extra;
                } catch (Exception ignored) {
                }
            }
        }


        //Cap is disabled because you can now unlock every perk, just at an increasing price.

        int cap = mgp.getRaceType().getPerkPointCap() + extraPerkPoints;

        if (totalSpentPoints >= cap || totalSpentPoints + bought.getCost(level, totalSpentPoints) > cap) {
            int newAmountSpent = bought.getCost(level, totalSpentPoints) + totalSpentPoints;
            ChatUtil.sendList(p, new String[]{"Buying this perk would exceed the Perk-Point cap.", "You spent " + totalSpentPoints + " Perk-Points.", "The maximum of Perk-Points spent is: " + cap + ".", "When buying this perk, your total would be: " + newAmountSpent + "."});
            return;
        } */

        if (bought.getCost(level, totalSpentPoints) > pointsPresent) {
            ChatUtil.sendString(p, "You do not have enough perk-points to buy this.");
            return;
        }

        if (!bought.getRaces().contains(mgp.getRaceType())) {
            ChatUtil.sendString(p, "That perk is unavailable to your race!");
            return;
        }

        int playerlevel = 0;
        if (mgp.hasAbility(bought)) {
            playerlevel = mgp.getAbilityLevel(bought);
        }


        if (level != playerlevel + 1) {
            if (level <= playerlevel) {
                ChatUtil.sendString(p, "You already unlocked that!");
            } else {
                ChatUtil.sendString(p, "You need to unlock the lower perks in this series first.");
            }
            return;
        }

        if (mgp.removePerkPoints(bought.getCost(level, totalSpentPoints))) {
            ChatUtil.sendString(p, "You have unlocked " + bought.getName() + " level " + level + "!");
            mgp.addAbility(bought, level);
            AbilityMenu.buildInventory(p);
        } else {
            ChatUtil.sendString(p, "You did not have enough perk-points after all.");
        }
    }
}
