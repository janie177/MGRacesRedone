package com.minegusta.mgracesredone.races.skilltree.abilities.perks.enderborn;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.Cooldown;
import com.minegusta.mgracesredone.util.EnderRiftPortal;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.List;

public class EndRift implements IAbility {
    @Override
    public void run(Event event) {
        PlayerInteractEvent e = (PlayerInteractEvent) event;
        if (e.getHand() != EquipmentSlot.HAND) return;
        Player p = e.getPlayer();
        MGPlayer mgp = Races.getMGPlayer(p);
        int level = mgp.getAbilityLevel(getType());
        String name = "endrift";
        String uuid = p.getUniqueId().toString();

        int duration = 5;
        if (level > 1) duration = 9;
        if (level > 2) duration = 15;

        boolean altEntities = level > 1;

        if (!EnderRiftPortal.contains(uuid)) {
            EnderRiftPortal.create(uuid, p.getLocation(), p.getLocation(), duration, altEntities);
        }

        if (!Cooldown.isCooledDown(name, uuid)) {
            ChatUtil.sendString(p, "You need to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use " + getName() + ".");
            return;
        }

        Action a = e.getAction();

        //Right portal
        if (!p.isSneaking() && (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)) {
            setPortal(p, 2);
        }

        //Left portal
        if (!p.isSneaking() && (a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK)) {
            setPortal(p, 1);
        }

        //Running the ability
        if (p.isSneaking()) {
            if (!EnderRiftPortal.portalsSet(uuid)) {
                ChatUtil.sendString(p, "You do not have your portals set! Bind the ability to an item to set them.");
                return;
            }

            if (!WGUtil.canBuild(p, EnderRiftPortal.getLocation2(uuid)) || !WGUtil.canBuild(p, EnderRiftPortal.getLocation1(uuid))) {
                ChatUtil.sendString(p, "You cannot run EndRift because at least one portal is in a safe zone!");
                return;
            }

            Cooldown.newCoolDown(name, uuid, getCooldown(level));

            EnderRiftPortal.start(uuid);

        }
    }

    private void setPortal(Player p, int portal) {
        String uuid = p.getUniqueId().toString();
        Block target = p.getTargetBlock(Sets.newHashSet(Material.AIR), 28).getRelative(BlockFace.UP);

        if (target.getY() - p.getLocation().getY() > 2) {
            target = target.getRelative(BlockFace.DOWN, 2);
        }
        if (target.getType() != Material.AIR) {
            double distance = p.getLocation().distance(target.getLocation()) - 2;
            target = p.getTargetBlock(Sets.newHashSet(Material.AIR), (int) distance);
        }

        double highestY = target.getWorld().getHighestBlockAt(target.getLocation()).getY();

        if (!WGUtil.canBuild(p, target.getLocation()) || (target.getWorld().getBlockAt(target.getX(), 0, target.getZ()).getType() == Material.AIR && (highestY == 0.0 || highestY > target.getY()))) {
            ChatUtil.sendString(p, "You cannot place a portal there!");
            return;
        }

        if (portal == 1) EnderRiftPortal.setLocation1(uuid, target.getLocation());
        if (portal == 2) EnderRiftPortal.setLocation2(uuid, target.getLocation());
        ChatUtil.sendString(p, "You set a location as portal!");
    }

    @Override
    public boolean run(Player player) {
        return false;
    }

    @Override
    public String getName() {
        return "End Rift";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.ENDRIFT;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.ENDER_PORTAL_FRAME;
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
        if (level > 2) return 45;
        return 60;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.ENDERBORN);
    }

    @Override
    public boolean canBind() {
        return true;
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
                desc = new String[]{"Open two portals that teleport entities to eachother.", "Your portal will transport yourself.", "Place portals by right/left clicking a bound item.", "Crouch click a bound item to activate.", "Will stay open for 5 seconds.", "Portals cannot be placed above the void or in solid blocks.", "Distance between portals may not be larger than 40 blocks.", "You cannot travel between different worlds."};
                break;
            case 2:
                desc = new String[]{"Your portal will transport mobs and itemstacks.", "The portals stay open for 9 seconds."};
                break;
            case 3:
                desc = new String[]{"The cooldown is reduced to 45 seconds.", "The portals stay open for 15 seconds."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
