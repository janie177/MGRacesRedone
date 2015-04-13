package com.minegusta.mgracesredone.main;

import com.minegusta.mgracesredone.commands.AdminCommand;
import com.minegusta.mgracesredone.commands.PerkCommand;
import com.minegusta.mgracesredone.commands.PerkResetCommand;
import com.minegusta.mgracesredone.commands.RaceCommand;
import org.bukkit.command.CommandExecutor;

public enum Command
{
    RACEADMIN(new AdminCommand()),
    RACE(new RaceCommand()),
    PERK(new PerkCommand()),
    PERKRESET(new PerkResetCommand());

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
