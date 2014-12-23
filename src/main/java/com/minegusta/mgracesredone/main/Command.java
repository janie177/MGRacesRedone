package com.minegusta.mgracesredone.main;

import org.bukkit.command.CommandExecutor;

public enum Command
{
    COMMANDNAME();

    private CommandExecutor commandExecutor;

    private Command(CommandExecutor commandExecutor)
    {
        this.commandExecutor = commandExecutor;
    }

    public String getName()
    {
        return name().toLowerCase();
    }

    public CommandExecutor getExecutor()
    {
        return commandExecutor;
    }
}
