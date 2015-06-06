package com.minegusta.mgracesredone.races.skilltree.abilities.perks.elf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.RandomUtil;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class ForestFriend implements IAbility {
    @Override
    public void run(Event event) {
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            Player p = (Player) e.getDamager();

            Animals animal = (Animals) e.getEntity();
            if (p.isSneaking()) {
                if (animal.isAdult()) animal.setBaby();
                else animal.setAdult();
                e.setCancelled(true);
            }
            if (animal instanceof Rabbit) {
                PotionUtil.updatePotion(p, PotionEffectType.JUMP, 3, 10);
            }
        } else if (event instanceof ProjectileHitEvent) {
            ProjectileHitEvent e = (ProjectileHitEvent) event;
            MGPlayer mgp = Races.getMGPlayer((Player) e.getEntity().getShooter());

            if (mgp.getAbilityLevel(getType()) > 1 && RandomUtil.fiftyfifty()) {
                Chicken chicken = (Chicken) e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.CHICKEN);
                chicken.setBaby();
            }
        }

    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Forest Friend";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.FORESTFRIEND;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.YELLOW_FLOWER;
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
        return Lists.newArrayList(RaceType.ELF);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"Hit a bunny to gain jump IV for 10 seconds."};
                break;
            case 2:
                desc = new String[]{"When throwing a chicken egg, chickens spawn 50% of the time."};
                break;
            case 3:
                desc = new String[]{"In forest biomes, you will get a speed II and jump II boost during the day."};
                break;
            case 4:
                desc = new String[]{"Crouch-hit baby animals to make them grow instantly."};
                break;
            default:
                desc = new String[]{"ERROR", "Report this to Jan!"};
                break;

        }
        return desc;
    }
}
