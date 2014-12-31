package com.minegusta.mgracesredone.main;

import com.minegusta.mgracesredone.files.FileManager;
import com.minegusta.mgracesredone.recipes.Recipe;
import com.minegusta.mgracesredone.tasks.BoostTask;
import com.minegusta.mgracesredone.tasks.SaveTask;
import com.minegusta.mgracesredone.util.OnReload;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    private static Plugin PLUGIN;
    private static boolean WG_ENABLED = false;

    @Override
    public void onEnable()
    {
        //Initialize the plugin
        PLUGIN = this;

        //Tasks
        SaveTask.start();
        BoostTask.start();

        //Files
        FileManager.create();

        //Add all players to the map on reload
        OnReload.addToMap();

        //Commands
        for(Command command : Command.values())
        {
            getCommand(command.getName()).setExecutor(command.getExecutor());
        }

        //Listeners
        for(Listener listener : Listener.values())
        {
            Bukkit.getPluginManager().registerEvents(listener.get(), this);
        }

        WG_ENABLED = Bukkit.getPluginManager().isPluginEnabled("WorldGuard");

        Races.countRaces();

        Recipe.registerRecipes();

    }

    @Override
    public void onDisable()
    {
        //Cancel tasks
        SaveTask.stop();
        BoostTask.stop();
    }

    /**
     * A method to getConfig the instance of the plugin.
     * @return The plugin.
     */
    public static Plugin getPlugin()
    {
        return PLUGIN;
    }

    public static boolean isWGEnabled()
    {
        return WG_ENABLED;
    }

}
