package com.minegusta.mgracesredone.races.skilltree.abilities.perks.elf;


import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ItemUtil;
import com.minegusta.mgracesredone.util.Missile;
import com.minegusta.mgracesredone.util.RandomUtil;
import com.minegusta.mgracesredone.util.SpecialArrows;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.util.List;

public class PointyShooty implements IAbility
{
    @Override
    public void run(Event event)
    {
        if(event instanceof EntityShootBowEvent) {
            EntityShootBowEvent e = (EntityShootBowEvent) event;
            Player p = (Player) e.getEntity();
            MGPlayer mgp = Races.getMGPlayer(p);
            int level = mgp.getAbilityLevel(getType());
            int chance = 10;

            //Setting the double arrow chance
            if(level > 4)
            {
                chance = 30;
            }
            else if(level > 2)
            {
                chance = 20;
            }

            //Double arrow
            if (RandomUtil.chance(chance))
            {
                final Arrow projectile = (Arrow) e.getProjectile();
                final Vector v = projectile.getVelocity();
                final ProjectileSource shooter = projectile.getShooter();
                final Location l = projectile.getLocation();

                final boolean enchantment = ((Player) e.getEntity()).getItemInHand().containsEnchantment(Enchantment.ARROW_INFINITE);
                if (!enchantment)
                {
                    ItemUtil.removeOne((Player) e.getEntity(), Material.ARROW);
                }

                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run() {

                        Arrow arrow = (Arrow)l.getWorld().spawnEntity(l.add(0, 0.3, 0), EntityType.ARROW);
                        arrow.setVelocity(v);
                        arrow.setShooter(shooter);

                        Missile.createMissile(l, v, new Effect[]{Effect.HAPPY_VILLAGER}, 15);
                    }
                }, 14);
            }
        }
        else if(event instanceof ProjectileHitEvent)
        {
            ProjectileHitEvent e = (ProjectileHitEvent) event;
            SpecialArrows.runEffect(e.getEntity().getLocation(), (Player) e.getEntity().getShooter());
        }


    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName()
    {
        return "Pointy Shooty";
    }

    @Override
    public AbilityType getType()
    {
        return AbilityType.POINTYSHOOTY;
    }

    @Override
    public int getID()
    {
        return 0;
    }

    @Override
    public Material getDisplayItem()
    {
        return Material.ARROW;
    }

    @Override
    public int getPrice(int level)
    {
        int price = 1;
        switch (level)
        {
            case 1: price = 2;
                break;
            case 2: price = 2;
                break;
            case 3: price = 2;
                break;
            case 4: price = 3;
                break;
            case 5: price = 2;
                break;
            default: price = 1;
                break;
        }
        return price;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.PASSIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 0;
    }

    @Override
    public List<RaceType> getRaces()
    {
        return Lists.newArrayList(RaceType.ELF);
    }

    @Override
    public int getMaxLevel()
    {
        return 5;
    }

    @Override
    public String[] getDescription(int level)
    {
        String[] desc;

        switch (level)
        {
            case 1: desc = new String[]{"When shooting a bow, there's a 10% chance to shoot two arrows at once."};
                break;
            case 2: desc = new String[]{"Hit the air with your bow to switch between normal and frozen arrows."};
                break;
            case 3: desc = new String[]{"When shooting a bow, there's a 20% chance to shoot two arrows at once."};
                break;
            case 4: desc = new String[]{"Unlock grappling hook and exploding arrows. Exploding arrows have a 20% explode chance."};
                break;
            case 5: desc = new String[]{"When shooting a bow, there's a 30% chance to shoot two arrows at once."};
                break;
            default: desc = new String[]{"This is an error.", "Report it to Jan!"};
                break;

        }
        return desc;
    }
}
