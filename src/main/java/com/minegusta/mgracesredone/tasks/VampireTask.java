package com.minegusta.mgracesredone.tasks;

import com.massivecraft.vampire.entity.UPlayer;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

public class VampireTask {
    private static int id = -1;

    public static void start() {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), () -> Bukkit.getOnlinePlayers().stream().forEach(p ->
        {
            UPlayer player = UPlayer.get(p);
            if (Races.getRace(p) != RaceType.HUMAN && (player.isVampire() || player.isInfected())) {
                player.setVampire(false);
                p.sendMessage(ChatColor.YELLOW + "You cannot be a vampire in combination with a race!");
                p.sendMessage(ChatColor.RED + "Your vampirism has been cured.");
            }
        }), 20 * 20, 20 * 20);
    }

    public static void stop() {
        if (id != -1) {
            Bukkit.getScheduler().cancelTask(id);
        }
    }
}
