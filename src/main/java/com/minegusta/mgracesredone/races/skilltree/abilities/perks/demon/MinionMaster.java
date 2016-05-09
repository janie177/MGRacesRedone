package com.minegusta.mgracesredone.races.skilltree.abilities.perks.demon;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.Cooldown;
import org.bukkit.*;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

public class MinionMaster implements IAbility {
    @Override
    public void run(Event event) {
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        Player p = (Player) e.getEntity();

        String name = "hellminion";
        String uuid = p.getUniqueId().toString();
        int level = Races.getMGPlayer(p).getAbilityLevel(getType());

        if (Cooldown.isCooledDown(name, uuid)) {
            p.sendMessage(ChatColor.RED + "The minions of hell are here to help you!");
            final Location l = p.getLocation();
            Cooldown.newCoolDown(name, uuid, getCooldown(level));

            //spawn the minions
            for (int n = 0; n < 3; n++) {
                Creature ent = (Creature) l.getWorld().spawnEntity(l, EntityType.PIG_ZOMBIE);
                ent.setTarget((LivingEntity) e.getDamager());
            }
            if (level > 1) {
                for (int n = 0; n < 4; n++) {
                    Creature ent = (Creature) l.getWorld().spawnEntity(l, EntityType.BLAZE);
                    ent.setTarget((LivingEntity) e.getDamager());
                }
            }
            if (level > 2) {
                for (int n = 0; n < 2; n++) {
                    l.getWorld().spawnEntity(l, EntityType.GHAST);
                }
            }
            if (level > 3) {
                for (int n = 0; n < 5; n++) {
                    Creature ent = (Creature) l.getWorld().spawnEntity(l, EntityType.PIG_ZOMBIE);
                    ent.setTarget((LivingEntity) e.getDamager());
                }
            }


            //Spawning the particles
            for (int le = -5; le < 5; le++) {
                for (int le2 = -5; le2 < 5; le2++) {
                    if (Math.abs(le2) + Math.abs(le) > 3 && Math.abs(le2) + Math.abs(le) < 5) {
                        final int loc1 = le2;
                        final int loc2 = le;
                        for (int i = 0; i < 20 * 6; i++) {
                            final int k = i;
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
                                l.getWorld().spigot().playEffect(l.getBlock().getRelative(loc1, 0, loc2).getLocation(),
                                        Effect.LAVADRIP, 1, 1, 0, k / 30, 0, 1, 25, 30);
                            }, i);
                        }
                    }
                }
            }
        }

    }

    @Override
    public boolean run(Player player) {
        return false;
    }

    @Override
    public String getName() {
        return "Minion Master";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.MINIONMASTER;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.MONSTER_EGG;
    }

    @Override
    public int getPrice(int level) {
        if (level > 2) return 2;
        return 1;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.PASSIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 180;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.DEMON);
    }

    @Override
    public boolean canBind() {
        return false;
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
                desc = new String[]{"When below 8 health, 3 zombie-pig-men will come to aid you.", "It is strongly advised to also have Hellish Truce perk 1."};
                break;
            case 2:
                desc = new String[]{"An additional blaze will spawn."};
                break;
            case 3:
                desc = new String[]{"two ghasts will also aid you now."};
                break;
            case 4:
                desc = new String[]{"There will be 5 additional zombie-pig-men."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
