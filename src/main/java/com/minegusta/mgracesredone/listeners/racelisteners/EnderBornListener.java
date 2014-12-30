package com.minegusta.mgracesredone.listeners.racelisteners;

import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.*;
import org.bukkit.Effect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffectType;

public class EnderBornListener implements Listener
{
    @EventHandler
    public void onEnderBornMeatEat(PlayerItemConsumeEvent e)
    {
        Player p = e.getPlayer();
        if(!WorldCheck.isEnabled(p.getWorld()))return;

        if(ItemUtil.isRawMeat(e.getItem().getType()))
        {
            PotionUtil.updatePotion(p, PotionEffectType.NIGHT_VISION, 0, 15);
            PotionUtil.updatePotion(p, PotionEffectType.INCREASE_DAMAGE, 0, 15);
            PotionUtil.updatePotion(p, PotionEffectType.SPEED, 1, 15);
            EffectUtil.playParticle(p, Effect.PORTAL, 1, 1, 1, 30);
        }
    }

    @EventHandler
    public void onBleed(EntityDamageByEntityEvent e)
    {
        if(!WorldCheck.isEnabled(e.getEntity().getWorld()))return;

        if(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity)
        {
            Player p = (Player) e.getDamager();
            if(isEnderBorn(p) && WGUtil.canFightEachother(p, e.getEntity()))
            {
                if(RandomUtil.chance(15))
                {
                    EntityUtil.bleed((LivingEntity) e.getEntity(), 4);
                }
            }
        }
    }

    private static boolean isEnderBorn(Player p)
    {
        return Races.getRace(p) == RaceType.ENDERBORN;
    }


}
