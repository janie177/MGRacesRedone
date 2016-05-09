package com.minegusta.mgracesredone.races.skilltree.abilities.perks.angel;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class HolyRain implements IAbility {


    @Override
    public void run(Event event) {
    }

    private void startRain(Location l, final boolean heal, int duration) {
        final World w = l.getWorld();
        final Location location = l;

        for (int i = 0; i <= duration * 20; i++) {
            if (i % 4 == 0) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> w.spigot().playEffect(location, Effect.WATERDRIP, 1, 1, 8, 0, 8, 1, 25, 20), i);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> runRain(location, heal), i);
            }
        }
    }

    private void runRain(Location location, boolean heal) {
        if (location == null) {
            return;
        }
        location.getWorld().getLivingEntities().stream().
                filter(ent -> ent.getLocation().distance(location) <= 8).forEach(le -> {
            if (le instanceof Player) {
                Player p = (Player) le;
                if (PlayerUtil.isUnholy(p)) {
                    damage(p);
                } else {
                    if (heal) {
                        heal(p);
                    }
                }
            } else {
                if (MonsterUtil.isUnholy(le.getType())) {
                    damage(le);
                } else {
                    if (heal) {
                        heal(le);
                    }
                }
            }
        });
    }

    private void heal(LivingEntity ent) {
        if (ent.isDead()) return;

        boolean regen = true;
        for (PotionEffect e : ent.getActivePotionEffects()) {
            if (e.getType().equals(PotionEffectType.REGENERATION)) {
                regen = false;
                break;
            }
        }
        if (regen) PotionUtil.updatePotion(ent, PotionEffectType.REGENERATION, 0, 5);
        PotionUtil.updatePotion(ent, PotionEffectType.SPEED, 0, 5);
        PotionUtil.updatePotion(ent, PotionEffectType.DAMAGE_RESISTANCE, 0, 5);
    }

    private void damage(LivingEntity ent) {

        PotionUtil.updatePotion(ent, PotionEffectType.HUNGER, 0, 5);
        PotionUtil.updatePotion(ent, PotionEffectType.SLOW, 1, 5);
        PotionUtil.updatePotion(ent, PotionEffectType.POISON, 1, 5);
    }

    @Override
    public boolean run(Player player) {

        if (!WGUtil.canBuild(player)) {
            ChatUtil.sendString(player, "You cannot use this here!");
            return false;
        }

            Missile.createMissile(player.getLocation(), player.getLocation().getDirection().multiply(1.1), new Effect[]{Effect.MOBSPAWNER_FLAMES, Effect.FLAME}, 30);
        EffectUtil.playParticle(player, Effect.CRIT);
            EffectUtil.playSound(player, Sound.ENTITY_LIGHTNING_THUNDER);
            ChatUtil.sendString(player, ChatColor.DARK_RED + "You call a holy rain on your location!");

            MGPlayer mgp = Races.getMGPlayer(player);
            int level = mgp.getAbilityLevel(getType());

            boolean heal = level > 1;
            int duration = 9;
            if (level > 2) {
                duration = 18;
            }

            startRain(player.getLocation().add(0, 9, 0), heal, duration);
        return true;
    }

    @Override
    public String getName() {
        return "Holy Rain";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.HOLYRAIN;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.WATER_BUCKET;
    }

    @Override
    public int getPrice(int level) {
        if (level == 1) {
            return 2;
        }
        return 1;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.ACTIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 90;
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
                desc = new String[]{"Call a holy rain on your location that heals holy creatures.", "Duration: 9 seconds.", "Bind using /Bind."};
                break;
            case 2:
                desc = new String[]{"Your holy rain damages unholy creatures now."};
                break;
            case 3:
                desc = new String[]{"The duration of your holy rain is now 18 seconds."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}