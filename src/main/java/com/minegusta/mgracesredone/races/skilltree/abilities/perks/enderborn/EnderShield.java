package com.minegusta.mgracesredone.races.skilltree.abilities.perks.enderborn;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.Cooldown;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class EnderShield implements IAbility
{
    public static ConcurrentMap<String, Integer> shields = Maps.newConcurrentMap();

    private void add(String uuid)
    {
        shields.put(uuid, get(uuid) + 1);
    }

    private int get(String uuid)
    {
        if(!contains(uuid)) return 0;
        return shields.get(uuid);
    }

    private void remove(String uuid)
    {
        if(get(uuid) > 0) shields.put(uuid, get(uuid) - 1);
        if(contains(uuid))remove(uuid);
    }

    private boolean contains(String uuid)
    {
        return shields.containsKey(uuid);
    }

    @Override
    public void run(Event event)
    {
        //The dropping of the item and adding to the map
        if(event instanceof PlayerDropItemEvent)
        {
            PlayerDropItemEvent e = (PlayerDropItemEvent) event;
            Player p = e.getPlayer();

            MGPlayer mgp = Races.getMGPlayer(p);
            int level = mgp.getAbilityLevel(getType());
            String uuid = p.getUniqueId().toString();
            String name = "pshield";
            if(!Cooldown.isCooledDown(name, uuid))
            {
                ChatUtil.sendString(p, "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use " + getName() + ".");
                return;
            }

            if((level < 2 && get(uuid) > 0)||(level > 2 && get(uuid) > 1))
            {
                ChatUtil.sendString(p, "You already have your maximum of active pearls.");
                return;
            }

            //new cooldown
            Cooldown.newCoolDown(name, uuid, getCooldown(level));

            //Add the shield.
            add(uuid);
        }

        //Listening for the damage
        else if(event instanceof EntityDamageByEntityEvent)
        {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            Player p = (Player) e.getEntity();
            Entity attacker = e.getDamager();
            MGPlayer mgp = Races.getMGPlayer(p);
            int level = mgp.getAbilityLevel(getType());
            String uuid = p.getUniqueId().toString();

            if(!WGUtil.canFightEachother(p, attacker) || e.isCancelled() || get(uuid) < 1)
            {
                return;
            }

            remove(uuid);

            int reflected = 0;
            if(level > 3) reflected = 20;
            if(level > 4) reflected = 40;
            int absorbed = 50;
            if(level > 1) absorbed = 75;

            //Absorbing damage
            e.setDamage(e.getDamage() * absorbed / 100);
            p.sendMessage(ChatColor.GREEN + "Your Ender Shield aborbed some damage!");


            if(reflected == 0) return;

            //Reflecting damage
            if(attacker instanceof LivingEntity)
            {
                LivingEntity damager = (LivingEntity) e.getDamager();
                EntityDamageByEntityEvent hit = new EntityDamageByEntityEvent(p, damager, EntityDamageEvent.DamageCause.CUSTOM, e.getDamage() * reflected / 100);
                Bukkit.getPluginManager().callEvent(hit);

                if(!hit.isCancelled())
                {
                    damager.damage(e.getDamage() * reflected / 100);
                    damager.setLastDamageCause(hit);
                    p.sendMessage(ChatColor.GREEN + "Your Ender Shield damaged your opponent!");
                    if(damager instanceof Player) damager.sendMessage(ChatColor.RED + "Your opponent's Ender Shield reflects some damage back!");
                }
            }
            else if(attacker instanceof Projectile && ((Projectile)attacker).getShooter() instanceof LivingEntity)
            {
                LivingEntity damager = (LivingEntity) ((Projectile)attacker).getShooter();
                EntityDamageByEntityEvent hit = new EntityDamageByEntityEvent(p, damager, EntityDamageEvent.DamageCause.CUSTOM, e.getDamage() * reflected / 100);
                Bukkit.getPluginManager().callEvent(hit);

                if(!hit.isCancelled())
                {
                    damager.damage(e.getDamage() * reflected / 100);
                    damager.setLastDamageCause(hit);
                    p.sendMessage(ChatColor.GREEN + "Your Ender Shield damaged your opponent!");
                    if(damager instanceof Player) damager.sendMessage(ChatColor.RED + "Your opponent's Ender Shield reflects some damage back!");
                }
            }
        }

    }


    @Override
    public void run(Player player)
    {

    }

    @Override
    public String getName() {
        return "Ender Shield";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.ENDERSHIELD;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.EYE_OF_ENDER;
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
        return 20;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.ENDERBORN);
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
                desc = new String[]{"Summon an endershield that circles around you, absorbing 50% combat damage.", "After receiving combat damage your shield disappears.", "Activate by dropping an enderpearl on the floor."};
                break;
            case 2:
                desc = new String[]{"Your shield absorbs 75% damage now."};
                break;
            case 3:
                desc = new String[]{"You can add a second pearl to your shield now."};
                break;
            case 4:
                desc = new String[]{"When your shield is destroyed, it reflects 20% damage back."};
                break;
            case 5:
                desc = new String[]{"When your shield is destroyed, it reflects 40% damage back."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
