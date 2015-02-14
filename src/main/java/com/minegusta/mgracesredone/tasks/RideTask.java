package com.minegusta.mgracesredone.tasks;

import com.minegusta.mgracesredone.listeners.racelisteners.ElfListener;
import com.minegusta.mgracesredone.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RideTask
{
    private static int ID = -1;

    public static void start()
    {
        ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run()
            {
                if(ElfListener.riders.isEmpty())return;
                for(String s : ElfListener.riders.keySet())
                {
                    LivingEntity ent = ElfListener.riders.get(s);
                    UUID uuid = UUID.fromString(s);

                    if(!Bukkit.getOfflinePlayer(uuid).isOnline())
                    {
                        remove(s);
                        continue;
                    }

                    Player rider = Bukkit.getPlayer(uuid);

                    if(ent.isDead() || ent.getPassenger().getUniqueId() != uuid)
                    {
                        remove(s);
                    }
                    Block target = rider.getTargetBlock(null, 4);

                    double y = -0.12;

                    if(target.getType() != null && target.getType() != Material.AIR && ent.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR)
                    {
                        y = 1.6;
                    }

                    ent.setVelocity(rider.getLocation().getDirection().multiply(0.4).setY(y));


                }
            }
        }, 10, 10);
    }

    public static void stop()
    {
        if(ID != -1) Bukkit.getScheduler().cancelTask(ID);
    }

    private static void remove(String s)
    {
        if(ElfListener.riders.containsKey(s))
        {
            ElfListener.riders.remove(s);
        }
    }
}
