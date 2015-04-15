package com.minegusta.mgracesredone.races.skilltree.abilities.perks.elf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.RandomUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class ArrowRain implements IAbility {
    @Override
    public void run(Event event)
    {
        ProjectileHitEvent e = (ProjectileHitEvent) event;
        Player p = (Player) e.getEntity().getShooter();
        MGPlayer mgp = Races.getMGPlayer(p);

        int duration = mgp.getAbilityLevel(getType()) * 5;
        Location l = e.getEntity().getLocation();

        startRain(duration, l);
    }

    private void startRain(int duration, final Location l)
    {
        for(int i = 0; i <= duration * 4; i++)
        {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                @Override
                public void run()
                {
                    int xAdded = -4 + RandomUtil.randomNumber(8);
                    int zAdded = -4 + RandomUtil.randomNumber(8);
                    Location dropLocation = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
                    l.getWorld().spawnEntity(dropLocation.add(xAdded,15,zAdded), EntityType.ARROW);
                }
            }, 5 * i);
        }
    }

    @Override
    public void run(Player player) {

    }

    @Override
    public String getName() {
        return "Arrow Rain";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.ARROWRAIN;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.LEATHER_HELMET;
    }

    @Override
    public int getPrice(int level) {

        return 4 - level;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.ELF);
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
                desc = new String[]{"Crouch-shoot a bow to cause a 5 second arrow rain on that location."};
                break;
            case 2:
                desc = new String[]{"Crouch-shoot a bow to cause a 10 second arrow rain at that location."};
                break;
            case 3:
                desc = new String[]{"Crouch-shoot a bow to cause a 15 second arrow rain at that location."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
