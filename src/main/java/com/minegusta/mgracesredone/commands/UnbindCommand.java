package com.minegusta.mgracesredone.commands;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.util.BindUtil;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnbindCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

        if (!(s instanceof Player)) return true;
        Player p = (Player) s;
        if (!WorldCheck.isEnabled(p.getWorld())) {
            ChatUtil.sendString(p, ChatColor.RED + "Races is not enabled in this world.");
            return true;
        }

        MGPlayer mgp = Races.getMGPlayer(p);

        //Unbind specific
        if (args.length < 1) {
            try {
                Material item = p.getInventory().getItemInMainHand().getType();
                short data = item != Material.AIR ? p.getInventory().getItemInMainHand().getDurability() : 0;

                boolean ignoreData = BindUtil.ignoreItemData(item);

                mgp.removeBind(item, data, ignoreData);

                ChatUtil.sendString(p, "You have unbound " + item.name() + (!ignoreData ? ":" + data : "") + " from any abilities.");
                return true;

            } catch (Exception ignored) {
                ChatUtil.sendString(p, ChatColor.RED + "That item does not have anything bound to it.");
                return true;
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("all")) {
            mgp.resetBinds();
            ChatUtil.sendString(p, "You unbound all your abilities.");
            return true;
        } else {
            sendHelp(p);
        }


        return true;
    }

    private void sendHelp(Player p) {
        ChatUtil.sendFancyBanner(p);
        p.sendMessage(ChatColor.YELLOW + "Use this command to bind abilities to items.");
        p.sendMessage(ChatColor.LIGHT_PURPLE + " /Bind List All" + ChatColor.GRAY + " - List all abilities you have that can be bound.");
        p.sendMessage(ChatColor.LIGHT_PURPLE + " /Bind List" + ChatColor.GRAY + " - List all binds you have currently set.");
        p.sendMessage(ChatColor.LIGHT_PURPLE + " /Bind <Name>" + ChatColor.GRAY + " - Bind ability with said name to the item in your hand.");
        p.sendMessage(ChatColor.LIGHT_PURPLE + " /Unbind" + ChatColor.GRAY + " - Remove bind from the item in your hand.");
        p.sendMessage(ChatColor.LIGHT_PURPLE + " /Unbind All" + ChatColor.GRAY + " - Unbind all bound abilities.");
        ChatUtil.sendFooter(p);
    }
}
