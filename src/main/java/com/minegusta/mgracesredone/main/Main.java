package com.minegusta.mgracesredone.main;

import com.minegusta.mgracesredone.recipes.Recipe;
import com.minegusta.mgracesredone.tasks.*;
import com.minegusta.mgracesredone.util.OnReload;
import com.minegusta.mgracesredone.util.ScoreboardUtil;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main PLUGIN;
    private static boolean WG_ENABLED = false;
    private Permission permission;

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

        //Register recipes
        Recipe.registerRecipes();

        WG_ENABLED = Bukkit.getPluginManager().isPluginEnabled("WorldGuard");

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

        //Tasks
        SaveTask.start();
        BleedTask.start();
        BoostTask.start();
        RideTask.start();
        MissileTask.start();
        InvisibleTask.start();
        ShieldTask.start();

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

        //Save
        SaveTask.save();
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
}
