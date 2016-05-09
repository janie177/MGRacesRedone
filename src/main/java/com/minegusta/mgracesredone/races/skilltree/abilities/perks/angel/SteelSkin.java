package com.minegusta.mgracesredone.races.skilltree.abilities.perks.angel;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.AngelInvincibility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;

public class SteelSkin implements IAbility {
    @Override
    public void run(Event event) {

    }

    @Override
    public boolean run(Player player) {

        int duration = 5;
        int level = Races.getMGPlayer(player).getAbilityLevel(getType());
        if (level > 1) {
            duration = 7;
        }
        if (level > 2) {
            duration = 9;
        }

        int endHealth = 4 - level;

        player.sendMessage(ChatColor.GOLD + "You are invincible for 8 seconds!");
        AngelInvincibility.startInvincibility(player, duration, endHealth);

        return true;
    }

    @Override
    public String getName() {
        return "Steel Skin";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.STEELSKIN;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.DIAMOND_CHESTPLATE;
    }

    @Override
    public int getPrice(int level) {
        return 2;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.ACTIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 160;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.ANGEL);
    }

    @Override
    public boolean canBind() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"Become invincible for 5 seconds.", "When time runs out, your health is set to 3.", "Your own damage output is halved.", "Bind using /Bind."};
                break;
            case 2:
                desc = new String[]{"Your invincibility lasts for 7 seconds.", "When time runs out, your health is set to 2."};
                break;
            case 3:
                desc = new String[]{"Your invincibility lasts for 9 seconds.", "When time runs out your health is set to 1.", "You will also obtain a weakness effect for 6 seconds."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}