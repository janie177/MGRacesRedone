package com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.PotionUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class Glacious implements IAbility {

    private static ConcurrentMap<String, Boolean> toggled = Maps.newConcurrentMap();

    @Override
    public void run(Event event) {

    }

    @Override
    public boolean run(Player player) {

        if (toggled.containsKey(player.getUniqueId().toString())) {
            toggled.remove(player.getUniqueId().toString());
            player.sendMessage(ChatColor.AQUA + "You disabled your Glacious buffs.");
        } else {
            toggled.put(player.getUniqueId().toString(), true);
            player.sendMessage(ChatColor.AQUA + "You enabled your Glacious buffs.");
            PotionUtil.updatePotion(player, PotionEffectType.SPEED, 4, 0);
            PotionUtil.updatePotion(player, PotionEffectType.JUMP, 4, 0);
        }

        return false;
    }

    public static boolean isToggled(Player player) {
        return !toggled.containsKey(player.getUniqueId().toString());
    }

    @Override
    public String getName() {
        return "Glacious";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.GLACIOUS;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.ICE;
    }

    @Override
    public int getPrice(int level) {
        return 1;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.PASSIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 0;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.AURORA);
    }

    @Override
    public boolean canBind() {
        return true;
    }

    @Override
    public String getBindDescription() {
        return "Toggle boosts in cold biomes.";
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"In cold biomes, you will gain a speed boost.", "Bind to an item to toggle using /Bind."};
                break;
            case 2:
                desc = new String[]{"In ice biomes you will gain a defence boost."};
                break;
            case 3:
                desc = new String[]{"You will no longer take fall damage on snow or ice."};
                break;
            case 4:
                desc = new String[]{"In ice biomes you will gain a strength boost."};
                break;
            case 5:
                desc = new String[]{"In ice biomes you will gain a speed and jump boost."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
