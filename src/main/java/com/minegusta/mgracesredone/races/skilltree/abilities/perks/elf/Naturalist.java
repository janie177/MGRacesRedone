package com.minegusta.mgracesredone.races.skilltree.abilities.perks.elf;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.EffectUtil;
import com.minegusta.mgracesredone.util.RandomUtil;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.List;

public class Naturalist implements IAbility {
    @Override
    public void run(Event event) {
        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent e = (PlayerInteractEvent) event;
            if (e.getHand() != EquipmentSlot.HAND) return;
            Player p = e.getPlayer();
            MGPlayer mgp = Races.getMGPlayer(p);

            if (mgp.getAbilityLevel(getType()) > 4) {
                Block b = e.getClickedBlock();
                int radius = 3;
                for (int x = -radius; x <= radius; x++) {
                    for (int y = -radius; y <= radius; y++) {
                        Block target = b.getRelative(x, 0, y);
                        if (target.getType() == Material.GRASS && target.getRelative(BlockFace.UP).getType() == Material.AIR) {
                            naturalize(target.getRelative(BlockFace.UP));
                        }
                    }
                }
            }
        }

        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            Player p = (Player) e.getEntity();
            MGPlayer mgp = Races.getMGPlayer(p);

            if (p.getHealth() <= 5 && !p.isDead() && mgp.getAbilityLevel(getType()) > 3) {
                for (Entity ent : p.getNearbyEntities(6, 6, 6)) {
                    if (ent instanceof Animals) {
                        EffectUtil.playSound(p, Sound.ENTITY_FIREWORK_LARGE_BLAST);
                        EffectUtil.playParticle(ent, Effect.CLOUD);
                        EffectUtil.playParticle(p, Effect.HEART);
                        double max = p.getMaxHealth() - p.getHealth();
                        double amount = 8;
                        if (max < 8) amount = max;
                        ((Animals) ent).damage(amount);
                        p.setHealth(p.getHealth() + amount);
                        p.sendMessage(ChatColor.GREEN + "An animal gave you some of it's life force!");
                    }
                }
            }
        }
    }

    private void naturalize(Block b) {

        int chance = RandomUtil.randomNumber(12);
        byte data = 0;
        Material m;

        switch (chance) {
            case 1: {
                m = Material.YELLOW_FLOWER;
            }
            break;
            case 2: {
                m = Material.RED_ROSE;
                data = (byte) (RandomUtil.randomNumber(9) - 1);
            }
            break;
            case 3: {
                m = Material.DOUBLE_PLANT;
                data = (byte) (RandomUtil.randomNumber(6) - 1);
            }
            break;
            default: {
                m = Material.LONG_GRASS;
                data = (byte) (RandomUtil.randomNumber(2));
            }
            break;
        }

        b.setType(m);
        b.setData(data);
    }

    @Override
    public boolean run(Player player) {
        return false;
    }

    @Override
    public String getName() {
        return "Naturalist";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.NATURALIST;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.RED_MUSHROOM;
    }

    @Override
    public int getPrice(int level) {
        return 1;
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
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.ELF);
    }

    @Override
    public boolean canBind() {
        return false;
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
                desc = new String[]{"You gain a speed I and jump I boost permanently."};
                break;
            case 2:
                desc = new String[]{"You regenerate health in water."};
                break;
            case 3:
                desc = new String[]{"You regenerate health in the rain."};
                break;
            case 4:
                desc = new String[]{"When nearly dead, you absorb life from nearby animals."};
                break;
            case 5:
                desc = new String[]{"Right-clicking grass with your hands acts as bone meal."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
