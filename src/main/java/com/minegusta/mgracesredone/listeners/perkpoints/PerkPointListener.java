package com.minegusta.mgracesredone.listeners.perkpoints;

import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.main.Races;
import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.util.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.concurrent.ConcurrentMap;

public class PerkPointListener implements Listener
{
    public static ConcurrentMap<String, KillData> killTracker = Maps.newConcurrentMap();

    @EventHandler
    public void onPerkAward(PlayerDeathEvent e)
    {
        EntityDamageEvent damageEvent = e.getEntity().getLastDamageCause();
        if(damageEvent != null && damageEvent instanceof EntityDamageByEntityEvent)
        {
            EntityDamageByEntityEvent attack = (EntityDamageByEntityEvent) damageEvent;
            if(attack.getDamager() instanceof Player)
            {
                Player damager = (Player) attack.getDamager();
                Player victim = e.getEntity();
                String uuid = damager.getUniqueId().toString();
                String victimUUID = victim.getUniqueId().toString();
                MGPlayer mgp = Races.getMGPlayer(damager);

                if(killTracker.containsKey(uuid))
                {
                    if(!hasKilled(uuid, victimUUID))
                    {
                        addKill(uuid, victimUUID);
                        mgp.addPerkPoints(1);
                        ChatUtil.sendString(damager, "You earned 1 Perk-Point for killing a player!");
                        return;
                    }
                    ChatUtil.sendString(damager, "You earned no Perk-Points because you already killed this player today.");
                    return;
                }
                else
                {
                    killTracker.put(uuid, new KillData(damager));
                    addKill(uuid, victimUUID);
                    mgp.addPerkPoints(1);
                    ChatUtil.sendString(damager, "You earned 1 Perk-Point!");
                    return;
                }
            }
        }
    }

    private void addKill(String attackerUUID, String victimUUID)
    {
        killTracker.get(attackerUUID).addPlayerToKilled(victimUUID);
    }

    private boolean hasKilled(String attackerUUID, String victimUUID)
    {
        return killTracker.get(attackerUUID).hasKilled(victimUUID);
    }

}
