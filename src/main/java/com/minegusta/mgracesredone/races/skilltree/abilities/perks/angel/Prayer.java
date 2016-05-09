package com.minegusta.mgracesredone.races.skilltree.abilities.perks.angel;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Prayer implements IAbility {
    @Override
    public void run(Event event) {

    }

    @Override
    public boolean run(Player player) {
        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());

        if (!WGUtil.canGetDamage(player)) {
            ChatUtil.sendString(player, "You cannot use Prayer here!");
            return false;
        }

        ChatUtil.sendString(player, "You pray and your requests are answered!");

        //The bunny
        Location l = player.getLocation();
        int amount = 1;
        if (level > 1) amount++;

        for (int i = 0; i < amount; i++) {
            Rabbit rabbit = (Rabbit) l.getWorld().spawnEntity(l, EntityType.RABBIT);
            if (level > 2) {
                PotionUtil.updatePotion(rabbit, PotionEffectType.SPEED, 1, 600);
                PotionUtil.updatePotion(rabbit, PotionEffectType.SPEED, 0, 600);
                PotionUtil.updatePotion(rabbit, PotionEffectType.REGENERATION, 0, 60);
            }
            rabbit.setAdult();
            rabbit.setCustomName(ChatColor.RED + "Judas");
            rabbit.setCustomNameVisible(true);
            rabbit.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);

            rabbit.getWorld().getEntitiesByClass(LivingEntity.class).stream().filter(le -> le.getLocation().
                    distance(rabbit.getLocation()) <= 8).filter(le -> !(le instanceof Animals) &&
                    !(le instanceof Player && Races.getRace((Player) le).equals(RaceType.ANGEL))).
                    forEach(rabbit::setTarget);
        }
        return true;
    }

    @Override
    public String getName() {
        return "Prayer";
    }

    @Override
    public AbilityType getType() {
        //return AbilityType.PRAYER;
        return null;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.BOOK;
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
        return 120;
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
                desc = new String[]{"Right click a book to activate Prayer.", "This will summon a killer bunny to aid you."};
                break;
            case 2:
                desc = new String[]{"You will get two killer bunnies instead."};
                break;
            case 3:
                desc = new String[]{"Your killer bunnies will have a speed and strength boost."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}