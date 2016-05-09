package com.minegusta.mgracesredone.main;

import com.minegusta.mgracesredone.commands.*;
import org.bukkit.command.CommandExecutor;

public enum Command {
    //Name equals command

    RACEADMIN(new AdminCommand()),
    RACE(new RaceCommand()),
    PERK(new PerkCommand()),
    BIND(new BindCommand()),
    UNBIND(new UnbindCommand()),
    SPAWN(new SpawnCommand()),
    MAINSPAWN(new MainSpawnCommand()),
    PERKRESET(new PerkResetCommand());

    private CommandExecutor commandExecutor;

    private Command(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public CommandExecutor getExecutor() {
        return commandExecutor;
    }
}
