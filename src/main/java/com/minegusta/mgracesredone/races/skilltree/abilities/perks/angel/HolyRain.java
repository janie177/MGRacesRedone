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
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class HolyRain implements IAbility
{

    private static final List<EntityType> unholy = Lists.newArrayList(EntityType.SKELETON, EntityType.ZOMBIE, EntityType.WITCH, EntityType.BLAZE, EntityType.GHAST, EntityType.ENDERMAN, EntityType.PIG_ZOMBIE, EntityType.CAVE_SPIDER, EntityType.SPIDER, EntityType.CREEPER, EntityType.ENDERMITE, EntityType.GUARDIAN, EntityType.WITHER);
    private static final List<RaceType> unholyRaces = Lists.newArrayList(RaceType.DEMON, RaceType.WEREWOLF, RaceType.ENDERBORN);

    @Override
    public void run(Event event) {
    }

    private void startRain(Location l, final boolean heal, int duration)
    {
        final World w = l.getWorld();
        final Location location = l;

        for(int i = 0; i < duration; i++)
        {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                @Override
                public void run()
                {
                    w.spigot().playEffect(location, Effect.WATERDRIP, 1, 1, 8, 0, 8, 1, 25, 20);
                }
            }, 5 * i);

            if(i % 4 == 0)
            {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run()
                    {
                        runRain(location, w, heal);
                    }
                }, 20 * (i/4));
            }
        }
    }

    private void runRain(Location location, World w, boolean heal)
    {
        if(location == null)return;
        Entity dummy = w.spawnEntity(location, EntityType.SNOWBALL);
        for(Entity ent : dummy.getNearbyEntities(8, 15, 8))
        {
            if(!(ent instanceof LivingEntity))continue;

            LivingEntity le = (LivingEntity) ent;

            if(ent instanceof Player)
            {
                Player p = (Player) ent;
                if(unholyRaces.contains(Races.getRace(p)))
                {
                    damage(p);
                }
                else
                {
                    if(heal)heal(p);
                }
            }
            else
            {
                if(unholy.contains(ent.getType()))
                {
                    damage(le);
                }
                else
                {
                    if(heal)heal(le);
                }
            }
        }
        dummy.remove();
    }

    private void heal(LivingEntity ent)
    {
        if(ent.isDead())return;

        boolean regen = true;
        for(PotionEffect e : ent.getActivePotionEffects())
        {
            if(e.getType().equals(PotionEffectType.REGENERATION))
            {
                regen = false;
                break;
            }
        }
        if(regen) PotionUtil.updatePotion(ent, PotionEffectType.REGENERATION, 0, 5);
        PotionUtil.updatePotion(ent, PotionEffectType.SPEED, 0, 5);
        PotionUtil.updatePotion(ent, PotionEffectType.DAMAGE_RESISTANCE, 0, 5);
    }

    private void damage(LivingEntity ent)
    {

        PotionUtil.updatePotion(ent, PotionEffectType.HUNGER, 0, 5);
        PotionUtil.updatePotion(ent, PotionEffectType.SLOW, 0, 5);
    }

    @Override
    public void run(Player player)
    {
        String uuid = player.getUniqueId().toString();
        String name = "holyrain";
        if (Cooldown.isCooledDown(name, uuid)) {
            Missile.createMissile(player.getLocation(), player.getLocation().getDirection().multiply(1.1), new Effect[]{Effect.MOBSPAWNER_FLAMES, Effect.FLAME}, 30);
            Cooldown.newCoolDown(name, uuid, getCooldown(Races.getMGPlayer(player).getAbilityLevel(getType())));
            EffectUtil.playParticle(player, Effect.MAGIC_CRIT);
            EffectUtil.playSound(player, Sound.AMBIENCE_THUNDER);
            ChatUtil.sendString(player, ChatColor.DARK_RED + "You call a holy rain on your location!");

            MGPlayer mgp = Races.getMGPlayer(player);
            int level = mgp.getAbilityLevel(getType());

            boolean heal = level > 1;
            int duration = 9;
            if(level > 2)duration = 18;

            startRain(player.getLocation().add(0, 9, 0), heal, duration);
        } else {
            ChatUtil.sendString(player, "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use Holy Rain.");
        }
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
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"Call a holy rain on your location that heals holy creatures.", "Duration: 9 seconds."};
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