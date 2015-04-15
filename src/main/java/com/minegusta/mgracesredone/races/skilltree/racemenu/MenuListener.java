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
            if (!e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName())
            {
                e.setCancelled(true);
                return;
            }
            apply(p, e.getCurrentItem());
            e.setCancelled(true);
        }
    }

    private static void apply(Player p, ItemStack i)
    {
        MGPlayer mgp = Races.getMGPlayer(p);
        AbilityType bought = null;

        if(i == AbilityMenu.getResetStack()) {
            p.chat("/perkreset");
            p.closeInventory();
            return;
        }
        if(i == AbilityMenu.getInfoStack()) {
            return;
        }

        for (AbilityType ability : AbilityType.values())
        {
            if (!(ChatColor.stripColor(ability.getAbility().getName()).equals(ChatColor.stripColor(i.getItemMeta().getDisplayName())))) continue;
            bought = ability;
            break;
        }

        if(bought == null)return;

        int pointsPresent = mgp.getPerkPoints();
        List<String> lore = i.getItemMeta().getLore();
        int level = Integer.parseInt(ChatColor.stripColor(lore.get(1).replace("Level: ", "")));

        int totalAbilities = 0;
        for(AbilityType t : mgp.getAbilities().keySet())
        {
            for(int levels = 1; levels <= mgp.getAbilityLevel(t); levels++)
            {
                totalAbilities = totalAbilities + t.getCost(levels);
            }
        }

        //The cap for perks
        int cap = 26;

        if(totalAbilities >= cap || totalAbilities + bought.getCost(level) > cap)
        {
            int newAmountSpent = bought.getCost(level) + totalAbilities;
            ChatUtil.sendList(p, new String[]{"Buying this perk would exceed the Perk-Point cap.", "You spent " + totalAbilities + " Perk-Points.", "The maximum of Perk-Points spent is: " + cap + ".", "When buying this perk, your total would be: " + newAmountSpent + "."});
            return;
        }

        if (bought.getCost(level) > pointsPresent) {
            ChatUtil.sendString(p, "You do not have enough perk-points to buy this.");
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
            return;
        }

        if(mgp.removePerkPoints(bought.getCost(level)))
        {
            ChatUtil.sendString(p, "You have unlocked " + bought.getName() + " level " + level + "!");
            mgp.addAbility(bought, level);
            AbilityMenu.buildInventory(p);
        } else
        {
            ChatUtil.sendString(p, "You did not have enough perk-points after all.");
        }
    }
}
