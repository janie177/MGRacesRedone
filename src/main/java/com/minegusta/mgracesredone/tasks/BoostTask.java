package com.minegusta.mgracesredone.tasks;

import com.minegusta.mgracesredone.data.Storage;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.potion.PotionEffectType;

public class BoostTask {
    private static int TASK = -1;
    private static int count = 0;

    public static void start() {
        TASK = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), BoostTask::boost, 20, 20);
    }

    public static void stop() {
        if (TASK != -1) Bukkit.getScheduler().cancelTask(TASK);
    }

    private static void boost() {
        count++;
        Bukkit.getOnlinePlayers().stream().filter(p -> WorldCheck.isEnabled(p.getWorld())).forEach(p ->
                Races.getRace(p).passiveBoost(p));
        if (count > 20) {
            //Reset the count.
            count = 0;
            Storage.getPlayers().stream().filter(mgp -> RaceType.WEREWOLF.equals(mgp.getRaceType())).forEach(mgp -> {
                Player player = mgp.getPlayer();
                mgp.getPlayer().getWorld().getEntitiesByClass(Wolf.class).stream().
                        filter(wolf -> wolf.getLocation().distance(player.getLocation()) <= 15).
                        filter(Wolf::isTamed).
                        filter(wolf -> wolf.getOwner().equals(player)).forEach(wolf -> {
                    PotionUtil.updatePotion(wolf, PotionEffectType.INCREASE_DAMAGE, 1, 25);
                    PotionUtil.updatePotion(wolf, PotionEffectType.DAMAGE_RESISTANCE, 0, 25);
                });
            });
        }
    }
}
