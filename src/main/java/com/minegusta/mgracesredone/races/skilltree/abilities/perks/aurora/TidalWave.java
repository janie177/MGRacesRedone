package com.minegusta.mgracesredone.races.skilltree.abilities.perks.aurora;

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
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class TidalWave implements IAbility
{

    @Override
    public void run(Event event) {

    }

    private static ConcurrentMap<String, List<Block>> waveMap = Maps.newConcurrentMap();

    @Override
    public void run(Player player)
    {
        MGPlayer mgp = Races.getMGPlayer(player);
        int level = mgp.getAbilityLevel(getType());
        Location l = player.getLocation();
        String uuid = player.getUniqueId().toString();
        String cooldownName = "wave";

        if(Cooldown.isCooledDown(cooldownName, uuid))
        {
            ChatUtil.sendString(player, "You use tidal wave on your location!");
            Cooldown.newCoolDown(cooldownName, uuid, getCooldown(level));

            int radius = 7;
            boolean damage = level > 1;
            if(level > 2)radius = 14;

            start(l, radius, damage, uuid, player);
        }
        else
        {
            ChatUtil.sendString(player, "You need to wait another " + Cooldown.getRemaining(cooldownName, uuid) + " seconds to use TidalWave.");
        }
    }

    private void start(Location l, int radius, boolean damage, final String uuid, Player p)
    {
        List<Block> blocks = Lists.newArrayList();

        for(int x = -radius; x <= radius; x++)
        {
            for(int y = -radius; y <= radius; y++)
            {

            }
        }

        frozenMap.put(uuid, blocks);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                for(Block block : frozenMap.get(uuid))
                {
                    if(block.getType() == Material.SNOW)
                    {
                        block.setType(Material.AIR);
                    }
                }
                frozenMap.remove(uuid);
            }
        }, time * 20);
    }

    @Override
    public String getName() {
        return "TidalWave";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.TIDALWAVE;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.POTION;
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
        return 60;
    }

    @Override
    public List<RaceType> getRaces() {
        return Lists.newArrayList(RaceType.AURORA);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public String[] getDescription(int level) {
        String[] desc;

        switch (level)
        {
            case 1: desc = new String[]{"Cast a wall of water in any of four directions.", "Activate by left-clicking a snowball."};
                break;
            case 2: desc = new String[]{"Your wave will cause drown damage to anyone in it."};
                break;
            case 3: desc = new String[]{"Your wave will reach twice as far."};
                break;
            default: desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
