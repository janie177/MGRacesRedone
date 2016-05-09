package com.minegusta.mgracesredone.races.skilltree.abilities.perks.enderborn;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class PearlPower implements IAbility {

    public static ConcurrentMap<String, PearlAbility> pmap = Maps.newConcurrentMap();

    private void addToMap(Player p, PearlAbility ability) {
        pmap.put(p.getUniqueId().toString(), ability);
        ChatUtil.sendString(p, "You are now using " + ability.getName() + " enderpearls.");
    }

    private void startCooldown(String name, String uuid) {
        Cooldown.newCoolDown(name, uuid, getCooldown(1));
    }

    private int getRemainingCooldown(String name, String uuid) {
        return Cooldown.getRemaining(name, uuid);
    }

    private boolean isCooledDown(String name, String uuid) {
        return Cooldown.isCooledDown(name, uuid);
    }

    public static PearlAbility getFromMap(Player p) {
        if (pmap.containsKey(p.getUniqueId().toString())) {
            return pmap.get(p.getUniqueId().toString());
        }
        pmap.put(p.getUniqueId().toString(), PearlAbility.NORMAL);
        return PearlAbility.NORMAL;
    }

    public enum PearlAbility {
        NORMAL(0, 1, "Normal"),
        VACUUM(1, 2, "Vacuum"),
        MINION(3, 3, "Minion"),
        EXPLODE(5, 4, "Exploding");

        private int level;
        private int order;
        private String name;

        PearlAbility(int level, int order, String name) {
            this.level = level;
            this.order = order;
            this.name = name;
        }

        public int getLevel() {
            return level;
        }

        public String getName() {
            return name;
        }

        public int getOrder() {
            return order;
        }

        public static PearlAbility getNext(int current, int level) {
            for (PearlAbility a : PearlAbility.values()) {
                if (a.getOrder() == current + 1) {
                    if (a.getLevel() <= level) return a;
                    break;
                }
            }
            return NORMAL;
        }
    }

    //The actual powers with the projectile hit event.
    @Override
    public void run(Event event) {
        ProjectileHitEvent e = (ProjectileHitEvent) event;

        Player p = (Player) e.getEntity().getShooter();
        MGPlayer mgp = Races.getMGPlayer(p);
        int level = mgp.getAbilityLevel(getType());
        PearlAbility a = getFromMap(p);
        String uuid = p.getUniqueId().toString();
        String name = a.getName();
        Location l = e.getEntity().getLocation();

        boolean refund = level > 3;
        boolean noDamage = level > 1;

        if (a != PearlAbility.NORMAL && !isCooledDown(name, uuid)) {
            ChatUtil.sendString(p, "You have to wait another " + getRemainingCooldown(name, uuid) + " seconds to use " + name + " pearls.");
            ((Player) e.getEntity().getShooter()).getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 1));
            return;
        }

        //Start the cooldown
        if (a != PearlAbility.NORMAL) startCooldown(name, uuid);

        switch (a) {
            case NORMAL:
                normal(l, p, noDamage);
                break;
            case VACUUM:
                vacuum(l, e.getEntity());
                break;
            case MINION:
                minion(l, e.getEntity());
                break;
            case EXPLODE:
                explode(l, p);
                break;
            default:
                refund = false;
                break;
        }


        //Chance to keep the pearl
        if (refund && RandomUtil.fiftyfifty()) {
            ((Player) e.getEntity().getShooter()).getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 1));
        }
    }

    //The abilities to run
    private void explode(Location l, Player p) {
        if (!WGUtil.canGetDamage(p)) return;
        l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), 3, false, false);
    }

    private void normal(Location l, Player p, boolean noDamage) {
        if (!noDamage) {
            p.damage(3);
        }
        p.teleport(l);
    }

    private void vacuum(Location l, Entity pearl) {
        for (Entity ent : pearl.getNearbyEntities(5, 5, 5)) {
            if (!WGUtil.canBuild(ent)) continue;
            if (ent instanceof LivingEntity || ent instanceof Projectile || ent instanceof Item) {
                double x = ent.getLocation().getX() - l.getX();
                double z = ent.getLocation().getZ() - l.getZ();

                Vector v = new Vector(x, 0.14, z);
                v.normalize();
                v.multiply(-1.7);

                ent.setVelocity(v);
            }
        }

        EffectUtil.playParticle(l, Effect.CLOUD, 3, 3, 3, 1 / 20, 40, 30);

    }

    private static final double[] directions = {0.5, -0.5, 1.0, -1.0};
    private static final Effect[] effects = {Effect.PORTAL};

    private void minion(Location l, Entity pearl) {
        Enderman man = (Enderman) l.getWorld().spawnEntity(l, EntityType.ENDERMAN);
        PotionUtil.updatePotion(man, PotionEffectType.INCREASE_DAMAGE, 2, 60);
        PotionUtil.updatePotion(man, PotionEffectType.DAMAGE_RESISTANCE, 1, 60);
        man.setCustomName(ChatColor.DARK_PURPLE + "Enderborn Minion");
        man.setCustomNameVisible(true);

        for (Entity ent : pearl.getNearbyEntities(7, 7, 7)) {
            if (ent instanceof LivingEntity) {
                ((Creature) man).setTarget((LivingEntity) ent);
                break;
            }
        }

        for (double x : directions) {
            for (double z : directions) {
                Missile.createMissile(l, x, 0.01, z, effects, 60);
            }
        }

        for (int i = 0; i < 2; i++) {
            Endermite mite = (Endermite) pearl.getWorld().spawnEntity(pearl.getLocation(), EntityType.ENDERMITE);
            mite.setCustomNameVisible(true);
            mite.setCustomName(ChatColor.LIGHT_PURPLE + "Enderborn Minion");
        }
    }

    //Switching pearl mode
    @Override
    public boolean run(Player player) {
        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());

        addToMap(player, PearlAbility.getNext(getFromMap(player).getOrder(), level));
        return true;
    }

    @Override
    public String getName() {
        return "Pearl Power";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.PEARLPOWER;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.ENDER_PEARL;
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
        return 15;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.ENDERBORN);
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
                desc = new String[]{"Use enderpearls to cause a vacuum, sucking in entities.", "hit with an enderpearl to switch mode."};
                break;
            case 2:
                desc = new String[]{"When teleporting using enderpearls you take no damage."};
                break;
            case 3:
                desc = new String[]{"A minion-spawning mode is added to enderpearls."};
                break;
            case 4:
                desc = new String[]{"When throwing an enderpearl there's a 50% chance you wont lose the pearl."};
                break;
            case 5:
                desc = new String[]{"You will now be able to throw exploding enderpearls."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
