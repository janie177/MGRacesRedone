package com.minegusta.mgracesredone.races.skilltree.abilities.perks.werewolf;


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

public class Nocturnal implements IAbility {

    private static ConcurrentMap<String, Boolean> toggled = Maps.newConcurrentMap();

    @Override
    public void run(Event event) {

    }

    @Override
    public boolean run(Player player) {

        //Is enabled
        if (toggled.containsKey(player.getUniqueId().toString())) {
            //Disable
            toggled.remove(player.getUniqueId().toString());
            player.sendMessage(ChatColor.RED + "You disabled your Nocturnal effects.");

            //Renew all pots for 0 seconds so they turn off.
            PotionUtil.updatePotion(player, PotionEffectType.SPEED, 2, 0);
            PotionUtil.updatePotion(player, PotionEffectType.NIGHT_VISION, 1, 0);
            PotionUtil.updatePotion(player, PotionEffectType.JUMP, 1, 0);
            PotionUtil.updatePotion(player, PotionEffectType.INCREASE_DAMAGE, 1, 0);
        }
        //Is disabled
        else {
            //Enable
            toggled.put(player.getUniqueId().toString(), true);
            player.sendMessage(ChatColor.RED + "You enabled your Nocturnal effects.");
        }
        return true;
    }

    public static boolean isToggled(Player player) {
        return toggled.containsKey(player.getUniqueId().toString());
    }

    @Override
    public String getName() {
        return "Nocturnal";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.NOCTURNAL;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.TORCH;
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
        return Lists.newArrayList(RaceType.WEREWOLF);
    }

    @Override
    public boolean canBind() {
        return true;
    }

    @Override
    public String getBindDescription() {
        return "Toggle nightly boosts for werewolf.";
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
                desc = new String[]{"At night, you gain a speed boost.", "Bind to an item to toggle using /Bind."};
                break;
            case 2:
                desc = new String[]{"At night, you gain night vision."};
                break;
            case 3:
                desc = new String[]{"At night you have a jump boost."};
                break;
            case 4:
                desc = new String[]{"At night you gain a strength boost."};
                break;
            case 5:
                desc = new String[]{"You no longer take fall damage at night"};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;
        }
        return desc;
    }
}