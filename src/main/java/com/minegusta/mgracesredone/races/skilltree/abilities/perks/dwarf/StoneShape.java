package com.minegusta.mgracesredone.races.skilltree.abilities.perks.dwarf;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class StoneShape implements IAbility {

    public static ConcurrentMap<Location, Boolean> wallBlocks = Maps.newConcurrentMap();

    @Override
    public void run(Event event) {

    }

    @Override
    public boolean run(Player player) {
        //Standard data needed.
        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());


        //Worldguard?
        if (!WGUtil.canBuild(player)) {
            player.sendMessage(ChatColor.RED + "You cannot use " + getName() + " here.");
            return false;
        }


        //Spawn the stone wall.
        boolean explode = level > 2;
        Location l = player.getLocation();
        int duration = 6;
        if (level > 1) duration = 10;

        final List<Location> locations = Lists.newArrayList();

        for (int x = -5; x <= 5; x++) {
            for (int y = -5; y <= 5; y++) {
                for (int z = -5; z <= 5; z++) {
                    Location loc = new Location(l.getWorld(), l.getX() + x, l.getY() + y, l.getZ() + z);
                    double distance = loc.distance(l);
                    if (distance > 4 && distance <= 5 && loc.getBlock().getType() == Material.AIR) {
                        locations.add(loc.getBlock().getLocation());
                        loc.getBlock().setType(Material.STONE);
                        wallBlocks.put(loc.getBlock().getLocation(), explode);
                    }
                }
            }
        }

        //Remove task
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
            for (Location loc : locations) {
                if (wallBlocks.containsKey(loc)) {
                    wallBlocks.remove(loc);
                }
                if (loc.getBlock().getType() == Material.STONE) loc.getBlock().setType(Material.AIR);
            }
        }, duration * 20);

        return true;
    }

    @Override
    public String getName() {
        return "Stone Shape";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.STONESHAPE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.STONE;
    }

    @Override
    public int getPrice(int level) {
        return 2;
    }

    @Override
    public AbilityGroup getGroup() {
        return AbilityGroup.ACTIVE;
    }

    @Override
    public int getCooldown(int level) {
        return 100;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.DWARF);
    }

    @Override
    public boolean canBind() {
        return true;
    }

    @Override
    public String getBindDescription() {
        return "Encase yourself in solid rock.";
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
                desc = new String[]{"Create a stone wall around you, effectively blocking out or trapping enemies.", "Bind to an item using /Bind.", "Lasts for 6 seconds."};
                break;
            case 2:
                desc = new String[]{"Your wall lasts for 10 seconds."};
                break;
            case 3:
                desc = new String[]{"Breaking your wall will cause a small explosion."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
