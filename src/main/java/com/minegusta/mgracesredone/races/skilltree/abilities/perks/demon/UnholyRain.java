package com.minegusta.mgracesredone.races.skilltree.abilities.perks.demon;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.Cooldown;
import com.minegusta.mgracesredone.util.EffectUtil;
import com.minegusta.mgracesredone.util.Missile;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class UnholyRain implements IAbility {
    @Override
    public void run(Event event) {
    }

    private void startRain(Location l)
    {
        com.minegusta.mgracesredone.util.UnholyRain rain = new com.minegusta.mgracesredone.util.UnholyRain(18, l);
        rain.start();
    }

    @Override
    public void run(Player player) {

        String uuid = player.getUniqueId().toString();
        String name = "uhrain";
        if (Cooldown.isCooledDown(name, uuid)) {
            Missile.createMissile(player.getLocation(), player.getLocation().getDirection().multiply(1.1), new Effect[]{Effect.MOBSPAWNER_FLAMES, Effect.FLAME}, 30);
            Cooldown.newCoolDown(name, uuid, 180);
            EffectUtil.playParticle(player, Effect.MAGIC_CRIT);
            EffectUtil.playSound(player, Sound.AMBIENCE_THUNDER);
            player.sendMessage(ChatColor.DARK_RED + "You call an unholy rain on your location!");

            startRain(player.getLocation().add(0, 9, 0));
        } else {
            ChatUtil.sendString(player, "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use Unholy Rain.");
        }
    }

    @Override
    public String getName() {
        return "Unholy Rain";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.UNHOLYRAIN;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.POISONOUS_POTATO;
    }

    @Override
    public int getPrice(int level) {
        return 1;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.DEMON);
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level)
        {
            case 1: desc = new String[]{"You gain a speed I and jump I boost permanently."};
                break;
            case 2: desc = new String[]{"You regenerate health in water."};
                break;
            case 3: desc = new String[]{"You regenerate health in the rain."};
                break;
            case 4: desc = new String[]{"When nearly dead, you absorb life from nearby animals."};
                break;
            case 5: desc = new String[]{"Right-clicking grass with your hands acts as bone meal."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
