package com.minegusta.mgracesredone.races.skilltree.manager;

import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import org.bukkit.configuration.ConfigurationSection;

public class AbilityFileManager
{

    private static final String path = "abilities";

    public static void loadAbilities(MGPlayer mgp)
    {
        ConfigurationSection abilityConf = mgp.getConfig().getConfigurationSection(path);
        for(String s : abilityConf.getKeys(false))
        {
            mgp.addAbility(AbilityType.valueOf(s), abilityConf.getInt(s + ".level"));
        }
    }

    public static void saveAbilities(MGPlayer mgp)
    {
        ConfigurationSection abilityConf = mgp.getConfig().getConfigurationSection(path);

        //Clear all old abilities before adding the new ones.
        mgp.getConfig().set(path, null);

        for(AbilityType ability : mgp.getAbilities().keySet())
        {
            abilityConf.set(path, ability.name());
            abilityConf.set(path + ".level", mgp.getAbilityLevel(ability));
        }
        mgp.saveFile();
    }
}
