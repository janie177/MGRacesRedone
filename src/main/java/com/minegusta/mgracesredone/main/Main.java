package com.minegusta.mgracesredone.main;

import com.minegusta.mgracesredone.recipes.Recipe;
import com.minegusta.mgracesredone.tasks.*;
import com.minegusta.mgracesredone.util.*;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main PLUGIN;
    private static boolean WG_ENABLED = false;
    private static WorldGuardPlugin WORLDGUARD = null;
    private Permission permission;

    public static WorldGuardPlugin getWorldGuard() {
        return WORLDGUARD;
    }

    /**
     * A method to getConfig the instance of the plugin.
     *
     * @return The plugin.
     */
    public static Main getPlugin() {
        return PLUGIN;
    }

    public static boolean isWGEnabled() {
        return WG_ENABLED;
    }

    @Override
    public void onEnable() {
        //Initialize the plugin
        PLUGIN = this;

        //Add all players to the map on reload
        OnReload.addToMap();

        //Commands
        for (Command command : Command.values()) {
            getCommand(command.getName()).setExecutor(command.getExecutor());
        }

        //Listeners
        for (Listener listener : Listener.values()) {
            Bukkit.getPluginManager().registerEvents(listener.get(), this);
        }

        //Save default config
        Main.getPlugin().saveDefaultConfig();

        //Load spawn locations
        SpawnLocationUtil.init();

        //Register recipes
        Recipe.registerRecipes();

        //Load all the food amounts per entity for the vampoopers.
        VampireFoodUtil.init();

        WG_ENABLED = Bukkit.getPluginManager().isPluginEnabled("WorldGuard");
        if (WG_ENABLED) WORLDGUARD = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");

        if (!setupPermissions()) {
            getLogger().severe("===========================================================");
            getLogger().severe("==================        WUMBO        ====================");
            getLogger().severe("===========================================================");
            getLogger().severe("ERRRRORRR NO PERMISSIONS PLUGIN RUN WHILE YOU CAN YOU FOOL!");
            getLogger().severe("ERRRRORRR NO PERMISSIONS PLUGIN RUN WHILE YOU CAN YOU FOOL!");
            getLogger().severe("ERRRRORRR NO PERMISSIONS PLUGIN RUN WHILE YOU CAN YOU FOOL!");
            getLogger().severe("ERRRRORRR NO PERMISSIONS PLUGIN RUN WHILE YOU CAN YOU FOOL!");
            getLogger().severe("===========================================================");
        }

        //Static stuff
        ScoreboardUtil.setBoard();
        CountRaces.count();


        //Tasks
        SaveTask.start();
        BleedTask.start();
        BoostTask.start();
        RideTask.start();
        MissileTask.start();
        InvisibleTask.start();
        ShieldTask.start();
        WeaknessTask.start();
        BloodLustTask.start();

    }

    public Permission getPermissions() {
        return permission;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    @Override
    public void onDisable() {
        //Cancel tasks
        SaveTask.stop();
        BoostTask.stop();
        MissileTask.stop();
        BleedTask.stop();
        RideTask.stop();
        InvisibleTask.stop();
        ShieldTask.stop();
        WeaknessTask.stop();
        BloodLustTask.stop();

        //Save
        SaveTask.save();
    }
}
