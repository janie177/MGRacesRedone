package com.minegusta.mgracesredone.listeners.racelisteners;


import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.util.EffectUtil;
import com.minegusta.mgracesredone.util.Races;
import com.minegusta.mgracesredone.util.WorldCheck;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class WereWolfListener implements Listener
{
    @EventHandler
    public void onWolfInteract(PlayerInteractEntityEvent e)
    {
        if(!WorldCheck.isEnabled(e.getPlayer().getWorld()))return;
        Player p = e.getPlayer();

        if(isWereWofl(p) && !e.isCancelled())
        {
            if(e.getRightClicked() instanceof Wolf)
            {
                Wolf w = (Wolf) e.getRightClicked();
                if(!w.isTamed())
                {
                    w.setTamed(true);
                    w.setOwner(p);
                    EffectUtil.playParticle(w, Effect.HEART);
                    EffectUtil.playSound(p, Sound.WOLF_HOWL);
                }
                else if(p.isSneaking() && w.getOwner().getUniqueId().equals(p.getUniqueId()))
                {
                    double health = p.getHealth();
                    double maxHealed = p.getMaxHealth() - health;
                    double healed = w.getHealth() / 2;
                    if(healed > maxHealed) healed = maxHealed;

                    p.setHealth(p.getHealth() + healed);

                    EffectUtil.playParticle(p, Effect.HEART);

                    EffectUtil.playSound(p, Sound.WOLF_GROWL);
                    EffectUtil.playParticle(w, Effect.CRIT, 1, 1, 1, 30);
                    w.damage(1000);
                }
            }
        }
    }


    public static boolean isWereWofl(Player p)
    {
        return Races.getRace(p) == RaceType.WEREWOLF;
    }
}
