package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffectType;

public class DwarfListener implements Listener
{
    @EventHandler
    public void onDwarfAxeHit(EntityDamageByEntityEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity)
        {
            Player p = (Player) e.getDamager();
            if(isDwarf(p) && ItemUtil.isAxe(p.getItemInHand().getType()) && WGUtil.canFightEachother(p, e.getEntity()) && !e.isCancelled())
            {
                e.setDamage(e.getDamage() + 2.0);
            }
        }

        //Arrow weakness

        if(e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE && e.getEntity() instanceof Player)
        {
            Player p = (Player) e.getEntity();
            if(isDwarf(p) && !e.isCancelled() && WGUtil.canGetDamage(p))
            {
                e.setDamage(e.getDamage() + 1.0);
            }
        }
    }

    @EventHandler
    public void onKillStreak(EntityDeathEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity().getKiller() != null)
        {
            Player p = e.getEntity().getKiller();
            if(isDwarf(p))
            {
                PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 0, 8);
                EffectUtil.playParticle(p, Effect.VILLAGER_THUNDERCLOUD);
                EffectUtil.playSound(p, Sound.ANVIL_USE);
            }
        }
    }

    @EventHandler
    public void onBattleCry(PlayerInteractEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;

        Player p = e.getPlayer();

        if((e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) && isDwarf(p) && ItemUtil.isAxe(p.getItemInHand().getType()))
        {
            String uuid = p.getUniqueId().toString();
            String name = "bcry";
            if(Cooldown.isCooledDown(name, uuid))
            {
                Cooldown.newCoolDown(name, uuid, 45);
                EffectUtil.playParticle(p, Effect.VILLAGER_THUNDERCLOUD);
                EffectUtil.playSound(p, Sound.ANVIL_USE);
                p.sendMessage(ChatColor.DARK_GRAY + "You yell and knock back all your enemies while boosting strength!");
                PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 1, 6);
                for(Entity ent : p.getNearbyEntities(3.0, 3.0, 3.0)) {
                    if (!(ent instanceof LivingEntity)) return;
                    LivingEntity le = (LivingEntity) ent;
                    if (le instanceof Player)
                    {
                        ((Player) le).sendMessage(ChatColor.RED + "You were knocked back by an angry dwarf!");
                    }
                    EffectUtil.playSound(le, Sound.ANVIL_USE);
                    EffectUtil.playParticle(le, Effect.CRIT);
                    le.teleport(le.getLocation().add(0,0.1,0));
                    le.setVelocity(le.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(2.0));
                }
            }
            else
            {
                ChatUtil.sendString(p, "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use BattleCry.");
            }
        }
    }

    @EventHandler
    public void onDwarfMine(BlockBreakEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;

        Player p = e.getPlayer();

        if(isDwarf(p) && ItemUtil.isOre(e.getBlock().getType()))
        {
            PotionUtil.updatePotion(p, PotionEffectType.FAST_DIGGING, 3, 0);
        }
    }

    private static boolean isDwarf(Player p)
    {
        return Races.getRace(p) == RaceType.DWARF;
    }
}
