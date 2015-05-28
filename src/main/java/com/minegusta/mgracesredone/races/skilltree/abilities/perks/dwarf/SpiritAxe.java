package com.minegusta.mgracesredone.races.skilltree.abilities.perks.dwarf;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Main;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.ChatUtil;
import com.minegusta.mgracesredone.util.Cooldown;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.WGUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class SpiritAxe implements IAbility
{
    private static ConcurrentMap<Integer, LivingEntity> axes = Maps.newConcurrentMap();

    @Override
    public void run(Event event)
    {
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        Player player = (Player) e.getDamager();
        LivingEntity target = (LivingEntity) e.getEntity();

        //Standard data needed.
        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());
        String name = "axespirit";
        String uuid = player.getUniqueId().toString();

        //Cooldown?
        if(!Cooldown.isCooledDown(name, uuid))
        {
            ChatUtil.sendString(player, "You have to wait another " + Cooldown.getRemaining(name, uuid) + " seconds to use " + getName() + ".");
            return;
        }

        //Worldguard?
        if(!WGUtil.canBuild(player))
        {
            ChatUtil.sendString(player, "You cannot use " + getName() + " here.");
            return;
        }

        //Run the ability here.

        ChatUtil.sendString(player, "You used " + getName() + "!");
        Cooldown.newCoolDown(name, uuid, getCooldown(level));

        boolean damageResist = level > 4;
        boolean sideAxes = level > 3;
        boolean diamond = level > 2;
        boolean strengt = level > 1;

        //Summon the axe
        final Zombie zombie = (Zombie) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
        PotionUtil.updatePotion(zombie, PotionEffectType.INVISIBILITY, 0, 3600);
        zombie.getEquipment().setItemInHand(new ItemStack(Material.IRON_AXE, 1));
        zombie.getEquipment().setItemInHandDropChance(0);
        if(diamond) zombie.getEquipment().setItemInHand(new ItemStack(Material.DIAMOND_AXE, 1));
        if(strengt) PotionUtil.updatePotion(zombie, PotionEffectType.INCREASE_DAMAGE, 0, 3600);
        if(damageResist) PotionUtil.updatePotion(zombie, PotionEffectType.DAMAGE_RESISTANCE, 0, 3600);

        //Task
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                if(!zombie.isDead())
                {
                    zombie.remove();
                }
            }
        }, 20 * 6);


        ((Creature)zombie).setTarget(target);

        //The two smaller axes
        if(sideAxes)
        {
            for(int i = 0; i < 2; i++)
            {
                Zombie z = (Zombie) player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                PotionUtil.updatePotion(z, PotionEffectType.INVISIBILITY, 0, 3600);
                z.getEquipment().setItemInHand(new ItemStack(Material.IRON_AXE, 1));
                z.getEquipment().setItemInHandDropChance(0);
                if(strengt) PotionUtil.updatePotion(z, PotionEffectType.INCREASE_DAMAGE, 0, 3600);
                if(damageResist) PotionUtil.updatePotion(z, PotionEffectType.DAMAGE_RESISTANCE, 0, 3600);
                ((Creature)z).setTarget(target);

                final int id = axes.size() + 1;

                axes.put(id, z);

                //Despawn task
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                    @Override
                    public void run()
                    {
                        if(!axes.get(id).isDead())
                        {
                            axes.get(id).remove();
                        }
                        axes.remove(id);
                    }
                }, 20 * 6);

            }
        }
    }

    @Override
    public void run(Player player)
    {

    }

    @Override
    public String getName() {
        return "Spirit Axe";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.SPIRITAXE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.GOLD_AXE;
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
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level)
        {
            case 1: desc = new String[]{"Summon a flying iron axe that attacks your target.", "Activate by crouch hitting a target.", "Lasts for 6 seconds."};
                break;
            case 2: desc = new String[]{"Your axe has a strength boost."};
                break;
            case 3: desc = new String[]{"Your axe is now diamond instead of iron."};
                break;
            case 4: desc = new String[]{"Two weaker iron axes will be summoned besides your main axe."};
                break;
            case 5: desc = new String[]{"Your axe will not die as easily."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
