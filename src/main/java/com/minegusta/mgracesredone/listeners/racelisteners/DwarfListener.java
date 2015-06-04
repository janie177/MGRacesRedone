package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.dwarf.SpiritAxe;
import com.minegusta.mgracesredone.races.skilltree.abilities.perks.dwarf.StoneShape;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
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

        //SpiritAxe activation
        if(e.getDamager() instanceof  Player && e.getEntity() instanceof LivingEntity && !e.isCancelled() && ((Player) e.getDamager()).isSneaking() && Races.getMGPlayer((Player) e.getDamager()).hasAbility(AbilityType.SPIRITAXE))
        {
            AbilityType.SPIRITAXE.run(e);
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

    //Axes cannot combust
    @EventHandler
    public void onAxeCombust(EntityCombustEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getEntity() instanceof Skeleton)
        {
            if(SpiritAxe.axes.containsKey(e.getEntity().getUniqueId().toString()))
            {
                e.setCancelled(true);
            }
        }
    }

    //Explode blocks in the wall from StoneShape
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        if(!WorldCheck.isEnabled(e.getBlock().getWorld()))return;

        Block b = e.getBlock();
        Location l = b.getLocation();

        if(StoneShape.wallBlocks.containsKey(l) && StoneShape.wallBlocks.get(l))
        {
            StoneShape.wallBlocks.remove(l);
            l.getWorld().createExplosion(l.getX(), l.getY(), l.getZ(), 2, false, false);
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
        if(e.getAction() == Action.LEFT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.GOLD_BLOCK && Races.getMGPlayer(p).getAbilityLevel(AbilityType.TUNNLER) > 3)
        {
            AbilityType.TUNNLER.run(p);
            return;
        }

        //StoneShape
        if(e.getAction() == Action.LEFT_CLICK_BLOCK && Races.getMGPlayer(p).hasAbility(AbilityType.STONESHAPE) && ItemUtil.isAxe(e.getPlayer().getItemInHand().getType()) && e.getClickedBlock().getLocation().distance(p.getLocation()) < 2 && e.getClickedBlock().getY() < e.getPlayer().getLocation().getY())
        {
            AbilityType.STONESHAPE.run(p);
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
            return;
        }
    }

    //Spirit axes will not attack their caster.
    @EventHandler
    public void onDwarfMonsterTarget(EntityTargetLivingEntityEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(!(e.getEntity() instanceof Skeleton) || !(e.getTarget() instanceof Player)) return;

        String id = e.getEntity().getUniqueId().toString();

        if(SpiritAxe.axes.containsKey(id) && SpiritAxe.axes.get(id).equals(e.getTarget().getUniqueId().toString()))
        {
            e.setCancelled(true);
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
        else if(level > 2 && e.getBlock().getType() == Material.STONE && e.getBlock().getLightLevel() < 3 && RandomUtil.chance(10) && p.getLocation().getBlock().getType() == Material.AIR)
        {
            p.getLocation().getBlock().setType(Material.TORCH);
        }
    }

    private static boolean isDwarf(Player p)
    {
        return Races.getRace(p) == RaceType.DWARF;
    }
}
