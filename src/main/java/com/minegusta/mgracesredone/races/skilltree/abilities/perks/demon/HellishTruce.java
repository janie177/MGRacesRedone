package com.minegusta.mgracesredone.races.skilltree.abilities.perks.demon;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.RandomUtil;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.List;

public class HellishTruce implements IAbility {

    private final static List<EntityType> hellMobs = Lists.newArrayList(EntityType.MAGMA_CUBE, EntityType.GHAST, EntityType.PIG_ZOMBIE, EntityType.BLAZE);

    @Override
    public void run(Event event) {
        if (event instanceof EntityTargetLivingEntityEvent) {
            EntityTargetLivingEntityEvent e = (EntityTargetLivingEntityEvent) event;
            if (hellMobs.contains(e.getEntityType())) {
                e.setCancelled(true);
            }
        }

        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            Player p = (Player) e.getDamager();
            MGPlayer mgp = Races.getMGPlayer(p);

            int level = mgp.getAbilityLevel(getType());

            if (level < 2) return;

            if (RandomUtil.chance(10 * (level - 1))) {
                p.getWorld().getEntities().stream().filter(ent -> ent.getLocation().distance(p.getLocation()) <= 10).
                        filter(ent -> hellMobs.contains(ent.getType())).forEach(ent -> {
                    ((Creature) ent).setTarget((LivingEntity) e.getEntity());
                });
            }
        }
    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Hellish Truce";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.HELLISHTRUCE;
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
        return level;
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
        return Lists.newArrayList(RaceType.DEMON);
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
                desc = new String[]{"Hell mobs will no longer target you."};
                break;
            case 2:
                desc = new String[]{"When attacking an enemy, there's a 10% chance nearby hell-mobs will help you fight."};
                break;
            case 3:
                desc = new String[]{"When attacking an enemy, there's a 20% chance nearby hell-mobs will help you fight."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
