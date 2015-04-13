package com.minegusta.mgracesredone.races.skilltree.racemenu;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.ChatUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MenuListener implements Listener
{
    @EventHandler
    public void buyItem(InventoryClickEvent e)
    {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getName() != null && e.getInventory().getName().contains(ChatColor.YELLOW + "Perk Shop. " + ChatColor.RED + "Perk-Points: ")) {
            if (!e.getCursor().getType().equals(Material.AIR))
            {
                e.setCancelled(true);
                p.updateInventory();
                return;
            }
            if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR)
            {
                e.setCancelled(true);
                return;
            }
            if (!e.getCurrentItem().hasItemMeta() || e.getCurrentItem().getItemMeta().getDisplayName() == null)
            {
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
            apply(p, e.getCurrentItem());
        }
    }

    public void apply(Player p, ItemStack i)
    {
        MGPlayer mgp = Races.getMGPlayer(p);
        AbilityType bought = null;

        if(i.equals(AbilityMenu.getResetStack()))
        {
            ChatUtil.sendString(p, "To reset your perks, use: " + ChatColor.DARK_RED + "/perkreset");
            p.closeInventory();
            return;
        }

        for (AbilityType ability : AbilityType.values())
        {
            if (!(ability.getName().equals(i.getItemMeta().getDisplayName()))) continue;
            bought = ability;
            break;
        }

        if(bought == null)return;

        int pointsPresent = mgp.getPerkPoints();
        List<String> lore = i.getItemMeta().getLore();
        int level = Integer.parseInt(lore.get(1).replace(ChatColor.YELLOW + "Level: ", ""));

        int totalAbilities = 0;
        for(AbilityType t : mgp.getAbilities().keySet())
        {
            totalAbilities = totalAbilities + t.getCost(level);
        }

        //The cap for perks
        int cap = 12;

        if(totalAbilities >= cap || totalAbilities + bought.getCost(level) > cap)
        {
            ChatUtil.sendString(p, "You cannot unlock this. Perk-point-cap: " + cap + ".");
            return;
        }

        if (bought.getCost(level) > pointsPresent) {
            ChatUtil.sendString(p, "You do not have enough perk-points to buy this.");
            p.closeInventory();
            return;
        }

        int playerlevel = 0;
        if(mgp.hasAbility(bought))
        {
            playerlevel = mgp.getAbilityLevel(bought);
        }


        if(level != playerlevel + 1)
        {
            if(level <= playerlevel)
            {
                ChatUtil.sendString(p, "You already unlocked that!");
            }
            else
            {
                ChatUtil.sendString(p, "You need to unlock the lower perks in this series first.");
            }
            p.closeInventory();
            return;
        }

        if(mgp.removePerkPoints(bought.getCost(level)))
        {
            ChatUtil.sendString(p, "You have unlocked " + bought.getName() + " level " + level + "!");
            mgp.addAbility(bought, level);
            p.closeInventory();
        } else
        {
            ChatUtil.sendString(p, "You did not have enough perk-points after all.");
        }
    }
}
