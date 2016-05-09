package com.minegusta.mgracesredone.races.skilltree.abilities.perks.werewolf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.EffectUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Howl implements IAbility {


    @Override
    public void run(Event event) {

    }

    @Override
    public boolean run(Player player) {
        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());

        int amount = 1;
        if (level > 2) amount = 2;
        if (level > 4) amount = 3;
        boolean strength = level > 1;
        boolean speed = level > 3;

        EffectUtil.playSound(player, Sound.ENTITY_WOLF_HOWL);

        for (int i = 0; i < amount; i++) {
            Wolf w = (Wolf) player.getWorld().spawnEntity(player.getLocation(), EntityType.WOLF);
            w.setOwner(player);
            w.setCustomName(ChatColor.DARK_GRAY + "Pooch");
            w.setCustomNameVisible(true);
            w.setCollarColor(DyeColor.MAGENTA);
            EffectUtil.playSound(player, Sound.ENTITY_WOLF_PANT);

            if (strength) PotionUtil.updatePotion(w, PotionEffectType.INCREASE_DAMAGE, 0, 6000);
            if (speed) PotionUtil.updatePotion(w, PotionEffectType.SPEED, 0, 6000);
        }
        return true;
    }

    @Override
    public String getName() {
        return "Howl";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.HOWL;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.LEASH;
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
        return 260;
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
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"Summon a tamed wolf to your aid.", "Bind to an item using //Bind."};
                break;
            case 2:
                desc = new String[]{"Your wolf now has a strength boost."};
                break;
            case 3:
                desc = new String[]{"You now get two wolves."};
                break;
            case 4:
                desc = new String[]{"Your wolves have a speed boost."};
                break;
            case 5:
                desc = new String[]{"You now summon three wolves."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;
        }
        return desc;
    }
}