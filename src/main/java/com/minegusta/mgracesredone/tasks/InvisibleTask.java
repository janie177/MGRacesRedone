package com.minegusta.mgracesredone.tasks;

import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.ShadowInvisibility;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class InvisibleTask {
    private static int count = 0;
    private static int TASK = -1;

    public static void start() {
        TASK = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), InvisibleTask::boost, 20, 5);
    }

    public static void stop() {
        if (TASK != -1) Bukkit.getScheduler().cancelTask(TASK);

        //make everyone visible
        Collection<? extends Player> online = Bukkit.getOnlinePlayers();
        ShadowInvisibility.values().stream().map(UUID::fromString).map(Bukkit::getPlayer).
                filter(Objects::nonNull).forEach(invisible -> online.forEach(player -> player.showPlayer(invisible)));
    }

    private static void boost() {
        ShadowInvisibility.values().stream().map(UUID::fromString).map(Bukkit::getPlayer).
                filter(Objects::nonNull).map(Player::getLocation).forEach(location -> {
            location.getWorld().spigot().playEffect(location, Effect.LARGE_SMOKE, 0, 0, 1, 0, 1, 1 / 10, 20, 30);
        });

        count++;

        if (count % 4 == 0) {
            ShadowInvisibility.values().forEach(id -> {
                ShadowInvisibility.add(id, ShadowInvisibility.getRaining(id) - 1);
                if (ShadowInvisibility.getRaining(id) < 1) {
                    ShadowInvisibility.remove(id);
                    ChatUtil.sendString(Bukkit.getPlayer(UUID.fromString(id)), "Your invisibility has ran out!");
                }
            });
        }

        if (count > 7) {
            count = 0;

            ShadowInvisibility.values().stream().map(UUID::fromString).map(Bukkit::getPlayer).
                    filter(Objects::nonNull).forEach(player -> {
                int level = Races.getMGPlayer(player).getAbilityLevel(AbilityType.SHADOW);
                if (level > 3) {
                    PotionUtil.updatePotion(player, PotionEffectType.SPEED, 0, 5);
                }
            });
        }
    }
}
