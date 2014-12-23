package com.minegusta.mgracesredone.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    private static Plugin PLUGIN;

    @Override
    public void onEnable()
    {
        //Initialize the plugin
        PLUGIN = this;

        //

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

    }

    @Override
    public void onDisable()
    {

    }

    /**
     * A method to getExecutor the instance of the plugin.
     * @return The plugin.
     */
    public static Plugin getPlugin()
    {
        return PLUGIN;
    }

}
