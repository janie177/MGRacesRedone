package com.minegusta.mgracesredone.races.skilltree.abilities.perks.enderborn;


import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.RaceType;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import com.minegusta.mgracesredone.races.skilltree.abilities.IAbility;
import com.minegusta.mgracesredone.util.EffectUtil;
import com.minegusta.mgracesredone.util.PotionUtil;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class ColdBlooded implements IAbility {
    @Override
    public void run(Event event) {

    }

    @Override
    public boolean run(Player player) {

        EffectUtil.playSound(player.getLocation(), Sound.ENTITY_BAT_AMBIENT, 1, 1);
        player.getWorld().getLivingEntities().stream().filter(LivingEntity::isValid)
                .filter(ent -> ent.getLocation().distance(player.getLocation()) < 35)
                .filter(ent -> !ent.getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString()))
                .forEach(ent ->
                {
                    PotionUtil.updatePotion(ent, PotionEffectType.GLOWING, 0, 6);
                    ent.getWorld().spigot().playEffect(ent.getLocation(), Effect.FOOTSTEP, 0, 0, 1, 0, 1, 0, 3, 20);
                    if (ent instanceof Player) ent.sendMessage(ChatColor.RED + "An Enderborn has detected you!");
                });

        return true;
    }

    @Override
    public String getName() {
        return "Cold Blooded";
    }

    @Override
    public AbilityType getType() {
        return AbilityType.COLDBLOODED;
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public Material getDisplayItem() {
        return Material.REDSTONE;
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
    public boolean canBind() {
        return true;
    }

    @Override
    public String getBindDescription() {
        return "Detect nearby beings.";
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
                desc = new String[]{"When in the shadow, you gain a defensive boost."};
                break;
            case 2:
                desc = new String[]{"In dark areas, you obtain nightvision."};
                break;
            case 3:
                desc = new String[]{"Your blood temperature is dropped.", "Endermen and mites will no longer be able to see or attack you."};
                break;
            case 4:
                desc = new String[]{"You are now capable of sensing nearby beings.", "Run once every 20 seconds.", "Bind to an item using /Bind."};
                break;
            default:
                desc = new String[]{"This is an error!"};
                break;

        }
        return desc;
    }
}
