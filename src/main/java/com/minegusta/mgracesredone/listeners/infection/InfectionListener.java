package com.minegusta.mgracesredone.listeners.infection;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.races.Demon;
import com.minegusta.mgracesredone.races.EnderBorn;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.recipes.Recipe;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ConcurrentMap;

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
                ChatUtil.sendString(p, "You are now a Demon!");

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

    //Dwarf
    @EventHandler
    public void onDwarfInfect(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        if(!WorldCheck.isEnabled(p.getWorld()))return;

        if(Races.getRace(p) != RaceType.HUMAN)return;
        if(e.hasBlock() && e.getAction() == Action.RIGHT_CLICK_BLOCK && p.getItemInHand().equals(Recipe.SHINYGEM.getResult()))
        {
            Block b = e.getClickedBlock();
            if(BlockUtil.radiusCheck(b, 6, Material.DIAMOND_ORE, 5) && BlockUtil.radiusCheck(b, 6, Material.EMERALD_ORE, 5) && BlockUtil.radiusCheck(b, 6, Material.REDSTONE_ORE, 5) && BlockUtil.radiusCheck(b, 6, Material.LAPIS_ORE, 5))
            {
                BlockUtil.poofBlocks(b, 6, Lists.newArrayList(Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE), Material.AIR, Effect.CLOUD);
                Races.setRace(p, RaceType.DWARF);
                ChatUtil.sendString(p, "Diggy diggy hole, you are now a Dwarf!");
                EffectUtil.playSound(p, Sound.ANVIL_USE);
                ItemUtil.removeOne(p, Recipe.SHINYGEM.getResult());
            }
        }
    }

    //EnderBorn
    @EventHandler
    public void onEnderBornInfect(final PlayerInteractEntityEvent e)
    {
        final Player p = e.getPlayer();

        if(!WorldCheck.isEnabled(p.getWorld()))return;

        if(!(e.getRightClicked() instanceof Enderman) || Races.getRace(p) != RaceType.HUMAN)return;

        if(p.getItemInHand().equals(Recipe.ENDEREYE.getResult()))
        {
            for (int i = 0; i < 20 * 10; i++) {
                final int k = i;
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

                    @Override
                    public void run()
                    {
                        if(k % 20 == 0)
                        {
                            if(p.getLocation().distance(e.getRightClicked().getLocation()) > 4)
                            {
                                ChatUtil.sendString(p, "You are too far from your target to complete the soul merge.");
                                return;
                            }
                            ChatUtil.sendString(p, (10 - (k/20)) + " Seconds remaining till soul-transfusion is completed.");
                        }
                        EffectUtil.playParticle(p, Effect.PORTAL, k/80,k/80,k/80, k/2);
                        EffectUtil.playParticle(e.getRightClicked().getLocation(), Effect.PORTAL, k/80,k/80,k/80, k/2);
                        if(k == 199)
                        {
                            p.getWorld().createExplosion(e.getRightClicked().getLocation(), 0, false);
                            p.getWorld().strikeLightning(e.getRightClicked().getLocation());
                            e.getRightClicked().remove();

                            Races.setRace(p, RaceType.ENDERBORN);
                            ChatUtil.sendString(p, "You are now Enderborn!");
                            ItemUtil.removeOne(p, Recipe.ENDEREYE.getResult());
                        }
                    }

                }, i);
            }
        }
    }

    private static ConcurrentMap<String, Integer> elfKills = Maps.newConcurrentMap();

    //Elf
    @EventHandler
    public void onElfInfect(PlayerItemConsumeEvent e)
    {
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();
        ItemStack item = e.getItem();

        if(!WorldCheck.isEnabled(p.getWorld()))return;
        if(Races.getRace(p) != RaceType.HUMAN)return;
        if(!item.equals(Recipe.ELFSTEW.getResult()))return;

        int kills = 0;
        if(elfKills.containsKey(uuid))
        {
            kills = elfKills.get(uuid);
        }

        if(kills > 99)
        {
            ChatUtil.sendString(p, "You are now an Elf!");
            EffectUtil.playParticle(p, Effect.HEART, 1, 1, 1, 40);
            EffectUtil.playSound(p, Sound.ARROW_HIT);
            Races.setRace(p, RaceType.ELF);
            return;
        }
        ChatUtil.sendString(p, "You do not have 100 bow kills yet!");
        ChatUtil.sendString(p, "You have " + kills + " kills.");
    }

    //Elf
    @EventHandler
    public void onElfKill(EntityDeathEvent e)
    {
        LivingEntity victim = e.getEntity();

        if(!WorldCheck.isEnabled(victim.getWorld()))return;

        if(victim.getLastDamageCause() == null || !(victim.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.PROJECTILE))return;

        Player killer = victim.getKiller();

        if(Races.getRace(killer) != RaceType.HUMAN)return;

        String uuid = killer.getUniqueId().toString();

        elfKills.put(uuid, elfKills.get(uuid) + 1);
        if(elfKills.get(uuid) % 5 == 0)
        {
            ChatUtil.sendString(killer, "You now have " + elfKills.get(uuid) + " bow kills.");
        }
    }

    //Werewolf
    @EventHandler
    public void onWerewolfInfect()
    {

    }

}
