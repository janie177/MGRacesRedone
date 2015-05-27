package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
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
    public void onDwarfDamage(EntityDamageByEntityEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        //Axe boost in damage
        if(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity)
        {
            Player p = (Player) e.getDamager();
            if(ItemUtil.isAxe(p.getItemInHand().getType()) && WGUtil.canFightEachother(p, e.getEntity()) && !e.isCancelled() && Races.getMGPlayer(p).hasAbility(AbilityType.BATTLEAXE))
            {
                AbilityType.BATTLEAXE.run(e);
            }
        }

        //Arrow weakness
        if(e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE && e.getEntity() instanceof Player)
        {
            Player p = (Player) e.getEntity();
            if(isDwarf(p) && !e.isCancelled() && WGUtil.canGetDamage(p))
            {
                e.setDamage(e.getDamage() + 3.0);
            }
            if(Races.getMGPlayer(p).hasAbility(AbilityType.PROJECTILEPROTECTION))
            {
                AbilityType.PROJECTILEPROTECTION.run(e);
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
            if(Races.getMGPlayer(p).hasAbility(AbilityType.COMBATANT))
            {
                AbilityType.COMBATANT.run(p);
            }
        }
    }

    @EventHandler
    public void onBattleCry(PlayerInteractEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;

        Player p = e.getPlayer();

        //Gold block hit
        if(e.getAction() == Action.LEFT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.GOLD_BLOCK && Races.getMGPlayer(p).getAbilityLevel(AbilityType.TUNNLER) > 4)
        {
            AbilityType.TUNNLER.run(p);
            return;
        }

        //Earthquake
        if((e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) && Races.getMGPlayer(p).hasAbility(AbilityType.EARTQUAKE) && ItemUtil.isPickAxe(p.getItemInHand().getType()))
        {
            AbilityType.EARTQUAKE.run(p);
            return;
        }

        //Battle Cry
        if((e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) && ItemUtil.isAxe(p.getItemInHand().getType()) && Races.getMGPlayer(p).hasAbility(AbilityType.BATTLECRY))
        {
            AbilityType.BATTLECRY.run(p);
        }
    }

    @EventHandler
    public void onDwarfMine(BlockBreakEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;

        Player p = e.getPlayer();
        MGPlayer mgp = Races.getMGPlayer(p);
        int level = mgp.getAbilityLevel(AbilityType.MINER);

        if(level < 2)return;

        if(e.isCancelled())return;

        if(ItemUtil.isOre(e.getBlock().getType()))
        {
            PotionUtil.updatePotion(p, PotionEffectType.FAST_DIGGING, 3, 12);
        }
        else if(e.getBlock().getType() == Material.STONE && e.getBlock().getLightLevel() < 5 && RandomUtil.chance(10) && p.getLocation().getBlock().getType() == Material.AIR)
        {
            p.getLocation().getBlock().setType(Material.TORCH);
        }
    }

    private static boolean isDwarf(Player p)
    {
        return Races.getRace(p) == RaceType.DWARF;
    }
}
