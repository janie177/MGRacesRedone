package com.minegusta.mgracesredone.races.skilltree.abilities.perks.werewolf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.EffectUtil;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.List;

public class Canis implements IAbility {


    @Override
    public void run(Event event) {

        PlayerInteractEntityEvent e = (PlayerInteractEntityEvent) event;
        if (e.getHand() != EquipmentSlot.HAND) return;
        Player p = e.getPlayer();
        MGPlayer mgp = Races.getMGPlayer(p);
        int level = mgp.getAbilityLevel(getType());

        Wolf w = (Wolf) e.getRightClicked();

        if (!w.isTamed()) {
            w.setTamed(true);
            w.setOwner(p);
            EffectUtil.playParticle(w, Effect.HEART);
            EffectUtil.playSound(p, Sound.ENTITY_WOLF_HOWL);
            p.sendMessage(ChatColor.RED + "You tamed a wolf! It's now part of your pack.");
        } else if (level > 1 && p.isSneaking() && w.getOwner().getUniqueId().equals(p.getUniqueId())) {
            double health = p.getHealth();
            double maxHealed = p.getMaxHealth() - health;
            double healed = w.getHealth() / 4;
            if (level > 2) healed = healed * 2;


            if (healed > maxHealed) healed = maxHealed;

            p.setHealth(p.getHealth() + healed);

            EffectUtil.playParticle(p, Effect.HEART);

            EffectUtil.playSound(p, Sound.ENTITY_WOLF_GROWL);
            EffectUtil.playParticle(w, Effect.CRIT, 1, 1, 1, 30);
            w.damage(1000);
            p.sendMessage(ChatColor.DARK_GREEN + "You drained a wolf's life force and healed yourself!");
        }

    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Canis";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.CANIS;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.BONE;
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
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"Right clicking a wild wolf tames it."};
                break;
            case 2:
                desc = new String[]{"When crouch right clicking a tamed wolf you absorb 25% of it's health."};
                break;
            case 3:
                desc = new String[]{"When crouch right clicking a tamed wolf you absorb 50% of it's health."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;
        }
        return desc;
    }
}