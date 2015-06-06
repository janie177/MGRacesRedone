package com.minegusta.mgracesredone.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatUtil {
    private static String prefix = ChatColor.DARK_PURPLE + "- - - - - " + ChatColor.YELLOW + "" + ChatColor.BOLD + "Races" + ChatColor.DARK_PURPLE + " - - - - -";

    public static void sendString(Player p, String message) {
        p.sendMessage(prefix);
        p.sendMessage(ChatColor.LIGHT_PURPLE + message);

    }

    public static void sendList(Player p, String[] messages) {
        p.sendMessage(prefix);
        for (String s : messages) {
            p.sendMessage(ChatColor.LIGHT_PURPLE + s);
        }
    }


    private static String s1 = ChatColor.DARK_PURPLE + "=====";
    private static String s2 = ChatColor.YELLOW + "||";
    private static String s3 = ChatColor.YELLOW + "" + ChatColor.BOLD + "        R   A   C   E   S";
    private static String s4 = s2 + s1 + s2 + s1 + s2 + s1 + s2 + s1 + s2 + s1 + s2;

    public static void sendFancyBanner(Player p) {
        p.sendMessage(s4);
        p.sendMessage(s3);
        p.sendMessage(s4);
    }

    public static void sendFooter(Player p) {
        p.sendMessage(s2 + s1 + s2 + s1 + s2 + s1 + s2 + s1 + s2);
    }


}
