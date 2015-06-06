package com.minegusta.mgracesredone.races.skilltree.manager;

import com.minegusta.mgracesredone.playerdata.MGPlayer;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import org.bukkit.configuration.file.FileConfiguration;

public class AbilityFileManager {

    private static final String path = "abilities";

    public static void loadAbilities(MGPlayer mgp) {
        FileConfiguration conf = mgp.getConfig();

        if (!conf.isSet(path)) return;

        for (String s : conf.getConfigurationSection(path).getKeys(false)) {
            mgp.addAbility(AbilityType.valueOf(s), conf.getInt(path + "." + s));
        }
    }

    public static void saveAbilities(MGPlayer mgp) {
        FileConfiguration conf = mgp.getConfig();
        //Clear all old abilities before adding the new ones.
        conf.set(path, null);

        for (AbilityType ability : mgp.getAbilities().keySet()) {
            conf.set(path + "." + ability.name(), mgp.getAbilityLevel(ability));
        }
    }
}
