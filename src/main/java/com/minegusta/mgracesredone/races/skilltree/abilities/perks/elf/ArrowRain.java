package com.minegusta.mgracesredone.races.skilltree.abilities.perks.elf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.Cooldown;
import com.minegusta.mgracesredone.util.RandomUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.List;

public class ArrowRain implements IAbility {
    @Override
    public void run(Event event) {
        ProjectileHitEvent e = (ProjectileHitEvent) event;
        Player p = (Player) e.getEntity().getShooter();
        MGPlayer mgp = Races.getMGPlayer(p);

        if (Cooldown.isCooledDown("arrowrain", p.getUniqueId().toString())) {
            int duration = mgp.getAbilityLevel(getType()) * 5;
            Location l = e.getEntity().getLocation();
            startRain(duration, l);
            Cooldown.newCoolDown("arrowrain", p.getUniqueId().toString(), getCooldown(mgp.getAbilityLevel(getType())));
        }
    }

    private void startRain(int duration, final Location l) {
        for (int i = 0; i <= duration * 4; i++) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), () -> {
                int xAdded = -4 + RandomUtil.randomNumber(8);
                int zAdded = -4 + RandomUtil.randomNumber(8);
                Location dropLocation = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
                for (int p = 0; p < 2; p++) {
                    Arrow arrow = (Arrow) l.getWorld().spawnEntity(dropLocation.add(xAdded, 15, zAdded), EntityType.ARROW);
                    arrow.setCritical(true);
                }
            }, 5 * i);
        }
    }

    @Override
    public boolean run(Player player) {
        return false;
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
        return Material.TIPPED_ARROW;
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
        return 20;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.ELF);
    }

    @Override
    public boolean canBind() {
        return false;
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
                desc = new String[]{"Crouch-shoot a bow to cause a", "5 second arrow rain on that location."};
                break;
            case 2:
                desc = new String[]{"Crouch-shoot a bow to cause a", "10 second arrow rain at that location."};
                break;
            case 3:
                desc = new String[]{"Crouch-shoot a bow to cause a", "15 second arrow rain at that location."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
