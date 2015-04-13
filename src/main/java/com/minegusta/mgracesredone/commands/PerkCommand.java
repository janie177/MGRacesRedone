package com.minegusta.mgracesredone.commands;

import com.minegusta.mgracesredone.races.skilltree.racemenu.AbilityMenu;
import com.minegusta.mgracesredone.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PerkCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] strings)
    {
        if(s instanceof Player)
        {
            Player p = (Player) s;
            ChatUtil.sendString(p, "You opened the perk interface.");
            AbilityMenu.buildInventory(p);
        }
        return true;
    }
}
