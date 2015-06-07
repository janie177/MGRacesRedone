package com.minegusta.mgracesredone.races.skilltree.abilities.perks.werewolf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.Cooldown;
import com.minegusta.mgracesredone.util.EffectUtil;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class Pounce implements IAbility {


    @Override
    public void run(Event event) {
        PlayerInteractEvent e = (PlayerInteractEvent) event;
        Player p = e.getPlayer();
        MGPlayer mgp = Races.getMGPlayer(p);
        int level = mgp.getAbilityLevel(getType());

        double power = 2.6;
        if (level > 1) power = 3.2;

        String name = "wolfjump";
        String uuid = p.getUniqueId().toString();

        if (Cooldown.isCooledDown(name, uuid)) {
            //Jump
            EffectUtil.playSound(p, Sound.WOLF_HOWL);
            EffectUtil.playParticle(p, Effect.FLAME);

            p.setVelocity(p.getLocation().getDirection().normalize().multiply(power));

            Cooldown.newCoolDown(name, uuid, getCooldown(level));
        } else {
            ChatUtil.sendString(p, "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use that.");
        }

    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Pounce";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.POUNCE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.BLAZE_ROD;
    }

    @Override
    public int getPrice(int level) {
        return 1;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.ACTIVE;
    }

    @Override
    public int getCooldown(int level) {
        int returned = 30;
        switch (level) {
            case 1:
                returned = 30;
                break;
            case 2:
                returned = 22;
                break;
            case 3:
                returned = 22;
                break;
            case 4:
                returned = 15;
                break;
            case 5:
                returned = 8;
                break;
            default:
                returned = 30;
                break;
        }
        return returned;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.WEREWOLF);
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
                desc = new String[]{"You can leap forward.", "Activate by right clicking the air while crouching."};
                break;
            case 2:
                desc = new String[]{"The cooldown is reduced to 22 seconds."};
                break;
            case 3:
                desc = new String[]{"You leap 50% further."};
                break;
            case 4:
                desc = new String[]{"The cooldown is reduced to 15 seconds."};
                break;
            case 5:
                desc = new String[]{"The cooldown is reduced to 8 seconds."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;
        }
        return desc;
    }
}