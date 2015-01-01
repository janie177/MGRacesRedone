package com.minegusta.mgracesredone.listeners.racelisteners;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.concurrent.ConcurrentMap;

public class EnderBornListener implements Listener
{

    private static ConcurrentMap<String, Boolean> pearlMap = Maps.newConcurrentMap();


    @EventHandler
    public void onEnderBornMeatEat(PlayerItemConsumeEvent e)
    {
        Player p = e.getPlayer();
        if(!WorldCheck.isEnabled(p.getWorld()))return;

        if(ItemUtil.isRawMeat(e.getItem().getType()))
        {
            PotionUtil.updatePotion(p, PotionEffectType.NIGHT_VISION, 0, 15);
            PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 0, 15);
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 1, 15);
            EffectUtil.playParticle(p, Effect.PORTAL, 1, 1, 1, 30);
        }
    }

    @EventHandler
    public void onBleed(EntityDamageByEntityEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity)
        {
            Player p = (Player) e.getDamager();
            if(isEnderBorn(p) && WGUtil.canFightEachother(p, e.getEntity()))
            {
                if(RandomUtil.chance(15))
                {
                    EntityUtil.bleed((LivingEntity) e.getEntity(), 4);
                }
            }
        }
    }

    @EventHandler
    public void onPearlToggle(PlayerInteractEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;
        Player p = e.getPlayer();
        if(isEnderBorn(p) && (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK))
        {
            if(p.getItemInHand().getType() == Material.ENDER_PEARL)
            {
                boolean enabled = true;
                String uuid = p.getUniqueId().toString();

                if(pearlMap.containsKey(uuid)) enabled = !pearlMap.get(uuid);
                pearlMap.put(uuid, enabled);

                if(enabled)
                {
                    p.sendMessage(ChatColor.DARK_PURPLE + "Enderpearls now no longer teleport you, but summon minions.");
                }
                else
                {
                    p.sendMessage(ChatColor.DARK_PURPLE + "Enderpearls will now teleport you again.");
                }

            }
        }
    }

    private static final double[] directions = {0.5, -0.5, 1.0, -1.0};
    private static final Effect[] effects = {Effect.PORTAL, Effect.ENDER_SIGNAL};


    @EventHandler
    public void onPearlThrow(ProjectileHitEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;
        if(e.getEntity() instanceof EnderPearl)
        {
            EnderPearl pearl = (EnderPearl) e.getEntity();
            if(pearl.getShooter() instanceof Player && isEnderBorn((Player) pearl.getShooter()))
            {
                boolean cancelled = false;
                Player p = (Player) pearl.getShooter();
                Location l = pearl.getLocation();
                String uuid = p.getUniqueId().toString();
                if(pearlMap.containsKey(uuid) && pearlMap.get(uuid))
                {
                    if(Cooldown.isCooledDown("pearl", uuid)) {

                        for(double x : directions)
                        {
                            for(double z : directions)
                            {
                                Missile.createMissile(l, x, 0.01, z, effects, 60);
                            }
                        }

                        Enderman man = (Enderman) pearl.getWorld().spawnEntity(pearl.getLocation(), EntityType.ENDERMAN);
                        PotionUtil.updatePotion(man, PotionEffectType.INCREASE_DAMAGE, 2, 60);
                        PotionUtil.updatePotion(man, PotionEffectType.DAMAGE_RESISTANCE, 1, 60);
                        man.setCustomName(ChatColor.DARK_PURPLE + "END OF MAN");
                        man.setCustomNameVisible(true);

                        for (int i = 0; i < 2; i++) {
                            Endermite mite = (Endermite) pearl.getWorld().spawnEntity(pearl.getLocation(), EntityType.ENDERMITE);
                            mite.setCustomNameVisible(true);
                            mite.setCustomName(ChatColor.LIGHT_PURPLE + "U wot mite?");
                        }


                        for (Entity ent : pearl.getNearbyEntities(7, 7, 7)) {
                            if (ent instanceof Player) {
                                ((Creature) man).setTarget((Player) ent);
                                break;
                            }
                        }

                        Cooldown.newCoolDown("pearl", uuid, 9);
                    }
                    else
                    {
                        p.sendMessage(ChatColor.DARK_PURPLE + "You have to wait another " + Cooldown.getRemaining("pearl", uuid) + " seconds to use minion pearls.");
                        p.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 1));
                        cancelled = true;
                    }
                }

                if(!cancelled && RandomUtil.fiftyfifty())
                {
                    ((Player)pearl.getShooter()).getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 1));
                }
            }
        }
    }

    @EventHandler
    public void onEnderMobTarget(EntityTargetLivingEntityEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;
        if(e.getTarget() instanceof Player && (e.getEntity() instanceof Enderman || e.getEntity() instanceof Endermite))
        {
            Player p = (Player) e.getTarget();
            if(!isEnderBorn(p))return;

            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onBlockTeleport(PlayerTeleportEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;

        Player p = e.getPlayer();
        if(!isEnderBorn(p))return;
        if(e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL)
        {
            if(!pearlMap.containsKey(p.getUniqueId().toString()))return;
            e.setCancelled(true);
            if(!pearlMap.get(p.getUniqueId().toString()))
            {
                p.teleport(e.getTo());
            }
        }
    }

    private static boolean isEnderBorn(Player p)
    {
        return Races.getRace(p) == RaceType.ENDERBORN;
    }


}
