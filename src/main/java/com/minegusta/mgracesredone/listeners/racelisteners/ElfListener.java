package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;

public class ElfListener implements Listener
{

    @EventHandler
    public void onElfFruitEat(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        if (!WorldCheck.isEnabled(p.getWorld())) return;

        if (isElf(p) && ItemUtil.isFruit(e.getItem().getType())) {
            PotionUtil.updatePotion(p, PotionEffectType.REGENERATION, 0, 5);
            EffectUtil.playParticle(p, Effect.HEART);
        }
    }

    @EventHandler
    public void onElfShoot(EntityShootBowEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof Player && isElf((Player) e.getEntity()))
        {
            if(RandomUtil.chance(25))
            {
                Arrow projectile = (Arrow) e.getProjectile();
                Arrow arrow = (Arrow) e.getEntity().getWorld().spawnEntity(e.getProjectile().getLocation().add(0,0.3,0), EntityType.ARROW);
                arrow.setVelocity(projectile.getVelocity());
                arrow.setShooter(projectile.getShooter());
                arrow.setKnockbackStrength(projectile.getKnockbackStrength());
                arrow.setCritical(projectile.isCritical());
                arrow.setBounce(projectile.doesBounce());
                ItemUtil.removeOne((Player) e.getEntity(), Material.ARROW);

                Missile.createMissile(projectile.getLocation(), arrow.getVelocity(), new Effect[]{Effect.HAPPY_VILLAGER}, 15);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBowDamage(EntityDamageByEntityEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity)
        {
            Player p = (Player) e.getDamager();
            if(e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE  && isElf(p) && WGUtil.canFightEachother(p, e.getEntity()) && !e.isCancelled())
            {
                e.setDamage(e.getDamage() + 1.0);
            }
        }

        if(e.getEntity() instanceof Player && e.getDamager() instanceof LivingEntity)
        {
            Player p = (Player) e.getEntity();
            if(isElf(p))
            {
                if(p.getHealth() <= 5 && !p.isDead())
                {
                    for(Entity ent : p.getNearbyEntities(6, 6, 6))
                    {
                        if(ent instanceof Animals)
                        {
                            EffectUtil.playSound(p, Sound.FIREWORK_LARGE_BLAST2);
                            EffectUtil.playParticle(ent, Effect.CLOUD);
                            EffectUtil.playParticle(p, Effect.HEART);
                            double max = p.getMaxHealth() - p.getHealth();
                            double amount = 8;
                            if(max < 8)amount = max;
                            ((Animals) ent).damage(amount);
                            p.setHealth(p.getHealth() + amount);
                            p.sendMessage(ChatColor.GREEN + "An animal gave you some of it's life force!");
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onElfBlow(PlayerInteractEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;

        if(!isElf(e.getPlayer()) || e.getAction() != Action.RIGHT_CLICK_AIR)return;

        Player p = e.getPlayer();

        if(!p.isSneaking())return;

        Material hand = p.getItemInHand().getType();

        if(hand == Material.RED_ROSE)
        {
            Missile.createMissile(p.getLocation(), p.getLocation().getDirection().multiply(1.4), new Effect[]{Effect.HEART}, 30);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFireDamage(EntityDamageEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof Player && (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) && isElf((Player) e.getEntity()) && WGUtil.canGetDamage(e.getEntity()))
        {
            e.setDamage(e.getDamage() + 1.0);
        }
    }

    @EventHandler
    public void onElfRide(PlayerInteractEntityEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()) || Races.getRace(e.getPlayer()) != RaceType.ELF)return;

        if(e.getRightClicked() instanceof LivingEntity && !(e.getRightClicked() instanceof Player))
        {
            e.getRightClicked().setPassenger(e.getPlayer());
        }
    }

    private static boolean isElf(Player p)
    {
        return Races.getRace(p) == RaceType.ELF;
    }

}
