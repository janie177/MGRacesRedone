package com.minegusta.mgracesredone.commands;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.Bind;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.BindUtil;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BindCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if (!WorldCheck.isEnabled(p.getWorld())) {
            ChatUtil.sendString(p, ChatColor.RED + "This world does not have races enabled.");
            return true;
        }

        if (args.length < 1) {
            sendHelp(p);
            return true;
        }

        MGPlayer mgp = Races.getMGPlayer(p);

        if (args[0].equalsIgnoreCase("List")) {
            //Send all perks
            if (args.length > 1) {
                List<AbilityType> abilityTypeList = Lists.newArrayList();
                for (AbilityType a : mgp.getAbilities().keySet()) {
                    if (a.canBind()) {
                        abilityTypeList.add(a);
                    }
                }

                ChatUtil.sendFancyBanner(p);
                if (abilityTypeList.isEmpty()) {
                    p.sendMessage(ChatColor.RED + "You do not have any abilities available!");
                    p.sendMessage(ChatColor.RED + "Unlock them using /Perk.");
                } else {
                    p.sendMessage(ChatColor.YELLOW + "Abilities you can bind:");
                    for (AbilityType a : abilityTypeList) {
                        p.sendMessage(ChatColor.GREEN + " - " + a.name() + ChatColor.GRAY + " - " + a.getBindDescription());
                    }
                }

                ChatUtil.sendFooter(p);
                return true;
            }
            //Send the list of currently set binds
            else {
                ChatUtil.sendFancyBanner(p);
                p.sendMessage(ChatColor.YELLOW + "You currently have the following binds set: ");
                for (Bind b : mgp.getBinds()) {
                    boolean ignoreData = BindUtil.ignoreItemData(b.getItem());
                    p.sendMessage(ChatColor.YELLOW + "Item: " + ChatColor.GRAY + b.getItem().name() + ChatColor.GREEN + (!ignoreData ? " Data:" + ChatColor.GRAY + b.getData() : "") + ChatColor.YELLOW + " Abilities: ");
                    for (AbilityType type : b.getAbilityTypes()) {
                        p.sendMessage(ChatColor.GRAY + "     - " + ChatColor.GREEN + type.name());
                    }
                }
                ChatUtil.sendFooter(p);
                return true;
            }
        } else {
            String abilityName = args[0].toUpperCase();
            try {
                AbilityType type = AbilityType.valueOf(abilityName);
                if (!mgp.hasAbility(type)) {
                    ChatUtil.sendString(p, "You do not own that ability!");
                    return true;
                }
                if (!type.canBind()) {
                    ChatUtil.sendString(p, ChatColor.RED + "That ability cannot be bound to an item!");
                    return true;
                }

                Material item = p.getInventory().getItemInMainHand().getType();
                boolean ignoreData = BindUtil.ignoreItemData(item);
                short data = item != Material.AIR ? p.getInventory().getItemInMainHand().getDurability() : 0;

                mgp.addBind(item, Lists.newArrayList(type), data);
                ChatUtil.sendString(p, ChatColor.GREEN + "You bound " + ChatColor.YELLOW + type.getName() + ChatColor.GREEN + " to " + ChatColor.YELLOW + item.name() + (!ignoreData ? ":" + data : "") + ChatColor.GREEN + ".");

                return true;


            } catch (Exception ignored) {
                ChatUtil.sendString(p, "That ability could not be found.");
            }

        }


        return true;
    }

    private void sendHelp(Player p) {
        ChatUtil.sendFancyBanner(p);
        p.sendMessage(ChatColor.YELLOW + "Use this command to bind abilities to items.");
        p.sendMessage(ChatColor.LIGHT_PURPLE + " /Bind List All" + ChatColor.GRAY + " - List all abilities you have that can be bound.");
        p.sendMessage(ChatColor.LIGHT_PURPLE + " /Bind List" + ChatColor.GRAY + " - List all binds you have currently set.");
        p.sendMessage(ChatColor.LIGHT_PURPLE + " /Bind <Name>" + ChatColor.GRAY + " - Bind ability with said name to the item in your hand.");
        p.sendMessage(ChatColor.LIGHT_PURPLE + " /Unbind" + ChatColor.GRAY + " - Remove binds from the item in your hand.");
        p.sendMessage(ChatColor.LIGHT_PURPLE + " /Unbind All" + ChatColor.GRAY + " - Unbind all bound abilities.");
        p.sendMessage(ChatColor.YELLOW + "You can bind multiple abilities to a single item.");
        p.sendMessage(ChatColor.GRAY + "- Left click to scroll through abilities.");
        p.sendMessage(ChatColor.GRAY + "- Right click to activate the selected ability.");
        ChatUtil.sendFooter(p);
    }
}
