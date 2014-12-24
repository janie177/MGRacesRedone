package com.minegusta.mgracesredone.listeners.infection;

import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.races.Demon;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.recipes.Recipe;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class InfectionListener implements Listener
{
    /**
     * It is important to update changes in the documentation by hand!
     */
    //Aurora
    @EventHandler
    public void onAuroraDeath(PlayerDeathEvent e)
    {
        Player p = e.getEntity();
        if(!WorldCheck.isEnabled(p.getWorld()))return;
        if(Races.getRace(p) != RaceType.HUMAN)return;
        if(p.getLocation().getBlock().getTemperature() > 0.15)return;
        if(e.getEntity().getLastDamageCause() != null && e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.DROWNING)
        {
            if(p.getInventory().containsAtLeast(Recipe.ICECRYSTAL.getResult(), 1))
            {
                Races.setRace(p, RaceType.AURORA);
                ChatUtil.sendString(p, "You feel all heat leave your body and are now an Aurora!");
                EffectUtil.playParticle(p, Effect.SNOW_SHOVEL, 1, 1, 1, 15);
                EffectUtil.playParticle(p, Effect.SNOWBALL_BREAK, 1, 1, 1, 15);
                EffectUtil.playSound(p, Sound.SPLASH);
                ItemUtil.removeOne(p, Recipe.ICECRYSTAL.getResult());
            }
        }
    }

    //DEMON
    @EventHandler
    public void onDemonInfect(AsyncPlayerChatEvent e)
    {
        final Player p = e.getPlayer();
        String message = e.getMessage();
        final Block center = p.getLocation().getBlock();
        boolean hasSheep = false;

        if(!WorldCheck.isEnabled(p.getWorld()))return;
        if(Races.getRace(p) != RaceType.HUMAN)return;
        if(message.equalsIgnoreCase(Demon.getChant()))
        {
            for(Entity entity : p.getNearbyEntities(15,15,15))
            {
                if(entity instanceof Sheep && ((Sheep)entity).isAdult())
                {
                    hasSheep = true;
                }
            }

            if(!hasSheep)return;
            if(BlockUtil.radiusCheck(center, 10, Material.OBSIDIAN, 55))
            {
                Races.setRace(p, RaceType.DEMON);
                EffectUtil.playSound(p, Sound.AMBIENCE_THUNDER);

                for(int i = 0; i < 20 * 20; i++)
                {
                    final int k = i;
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            p.getWorld().spigot().playEffect(center.getLocation(), Effect.LAVADRIP, 1, 1, k/80, 1 + k/250, k/80, 1, 30 + k/2, 30);
                            if(k % 20 == 0)
                            {
                                center.getWorld().playSound(center.getLocation(), Sound.GHAST_MOAN, 5, 5);
                            }
                        }
                    }, i);
                }

                for(int le = -5; le < 5; le++)
                {
                    for(int le2 = -5; le2 < 5; le2++)
                    {
                        if(Math.abs(le2) + Math.abs(le) > 3 && Math.abs(le2) + Math.abs(le) < 5)
                        {
                            if(p.getLocation().getBlock().getRelative(le, 0, le2).getType().equals(Material.AIR))
                            {
                                final int loc1 = le2;
                                final int loc2 = le;
                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                                    @Override
                                    public void run() {
                                        p.getLocation().getBlock().getRelative(loc1, 0, loc2).setType(Material.FIRE);
                                    }
                                }, 0);
                            }
                        }
                    }
                }

            }


        }
    }


}
