package com.minegusta.mgracesredone.races.skilltree.abilities.perks.angel;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.AngelInvincibility;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.Cooldown;
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
    public void run(Player player) {
        String uuid = player.getUniqueId().toString();
        String name = "invincible";
        if (Cooldown.isCooledDown(name, uuid)) {
            int duration = 5;
            int level = Races.getMGPlayer(player).getAbilityLevel(getType());
            if (level > 1) duration = 8;
            if (level > 2) duration = 10;

            int endHealth = 4 - level;

            Cooldown.newCoolDown(name, uuid, getCooldown(level));
            player.sendMessage(ChatColor.GOLD + "You are invincible for 8 seconds!");
            AngelInvincibility.startInvincibility(player, duration, endHealth);
        } else {
            ChatUtil.sendString(player, "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use Invincibility.");
        }
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
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"Become invincible for 5 seconds.", "When time runs out, your health is set to 3.", "Activate by right-clicking an iron ingot."};
                break;
            case 2:
                desc = new String[]{"Your invincibility lasts for 8 seconds.", "When time runs out, your health is set to 2."};
                break;
            case 3:
                desc = new String[]{"Your invincibility lasts for 10 seconds.", "When time runs out your health is set to 1.", "You will also obtain a weakness effect for 6 seconds."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}