package com.minegusta.mgracesredone.races.skilltree.abilities.perks.enderborn;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.EffectUtil;
import com.minegusta.mgracesredone.util.EntityUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import com.minegusta.mgracesredone.util.RandomUtil;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Predator implements IAbility
{
    //Bleeding for the predator ability
    @Override
    public void run(Event event)
    {
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        Player p = (Player) e.getDamager();
        LivingEntity target = (LivingEntity) e.getEntity();
        MGPlayer mgp = Races.getMGPlayer(p);
        int level = mgp.getAbilityLevel(getType());

        int chance = 8;
        if(level > 3) chance = 16;

        if(RandomUtil.chance(chance))
        {
            if(RandomUtil.chance(15))
            {
                EntityUtil.bleed(target, 4);
            }
        }

    }

    //The food boosts
    @Override
    public void run(Player player)
    {
        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());

        PotionUtil.updatePotion(player, PotionEffectType.REGENERATION, 0, 4);
        if(level > 2)
        {
            PotionUtil.updatePotion(player, PotionEffectType.REGENERATION, 0, 8);
            PotionUtil.updatePotion(player, PotionEffectType.NIGHT_VISION, 0, 15);
            PotionUtil.updatePotion(player, PotionEffectType.INCREASE_DAMAGE, 0, 15);
            PotionUtil.updatePotion(player, PotionEffectType.SPEED, 1, 15);
            EffectUtil.playParticle(player, Effect.PORTAL, 1, 1, 1, 30);
        }
    }

    @Override
    public String getName() {
        return "Predator";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.PREDATOR;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.RAW_BEEF;
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
        return Lists.newArrayList(RaceType.ENDERBORN);
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level) {
            case 1:
                desc = new String[]{"When eating raw food you gain a regeneration effect."};
                break;
            case 2:
                desc = new String[]{"When hitting enemies they have a 8% chance to start bleeding.", "Bleeding lasts 4 seconds."};
                break;
            case 3:
                desc = new String[]{"Your raw food generation lasts twice as long.", "You also get a nightvision, strength and speed boost."};
                break;
            case 4:
                desc = new String[]{"Your chance to make enemies bleed is now 16%."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
