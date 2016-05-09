package com.minegusta.mgracesredone.races.skilltree.abilities.perks.dwarf;


import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.RandomUtil;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class BattleAxe implements IAbility {

    @Override
    public void run(Event event) {
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        Player p = (Player) e.getDamager();
        MGPlayer mgp = Races.getMGPlayer(p);
        int level = mgp.getAbilityLevel(getType());
        Entity victim = e.getEntity();

        PotionUtil.updatePotion(p, PotionEffectType.FAST_DIGGING, 0, 5);
        if (level > 3) {
            PotionUtil.updatePotion(p, PotionEffectType.FAST_DIGGING, 0, 5);
        }

        int added = 1;
        if (level > 2) {
            added = 2;
        }

        e.setDamage(e.getDamage() + added);

        if (level > 1 && RandomUtil.chance(10)) {
            int add = 5;
            if (level > 3) add = 10;

            e.setDamage(e.getDamage() + (e.getDamage() / 100 * add));
        }

        if (level > 4) {
            // Acceptable use of Entity::getNearbyEntities according to Spigot
            victim.getNearbyEntities(2, 2, 2).stream().filter(ent -> ent instanceof LivingEntity &&
                    !(ent instanceof Player) && WGUtil.canFightEachother(p, ent)).forEach(ent -> {
                ((LivingEntity) ent).damage(1);
            });
        }

    }

    @Override
    public boolean run(Player player) {
        return false;
    }

    @Override
    public String getName() {
        return "Battle Axe";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.BATTLEAXE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.DIAMOND_AXE;
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
        return Lists.newArrayList(RaceType.DWARF);
    }

    @Override
    public boolean canBind() {
        return false;
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
                desc = new String[]{"You do an additional 1 damage with axes.", "Axes attack 10% faster on consecutive hits."};
                break;
            case 2:
                desc = new String[]{"There's a 10% chance you will do a critical hit, adding 5% damage."};
                break;
            case 3:
                desc = new String[]{"You do an additional 1 damage with axes."};
                break;
            case 4:
                desc = new String[]{"There's a 10% chance you will do a critical hit, adding 10% damage.", "Axes attack 20% faster on consecutive hits."};
                break;
            case 5:
                desc = new String[]{"You will now hit multiple mobs at once when using an axe."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
