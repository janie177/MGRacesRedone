package com.minegusta.mgracesredone.main;


import com.minegusta.mgracesredone.listeners.cure.CureListener;
import com.minegusta.mgracesredone.listeners.general.GeneralListener;
import com.minegusta.mgracesredone.listeners.infection.InfectionListener;
import com.minegusta.mgracesredone.listeners.racelisteners.*;

public enum Listener
{
    GENERAL_LISTENER(new GeneralListener()),
    INFECTION_LISTENER(new InfectionListener()),
    ELF_LISTENER(new ElfListener()),
    DWARF_LISTENER(new DwarfListener()),
    AURORA_LISTENER(new AuroraListener()),
    CURE_LISTENER(new CureListener()),
    DEMON_LISTENER(new DemonListener()),
    WEREWOLF_LISTENER(new WereWolfListener()),
    ENDERBORNLISTENER(new EnderBornListener());

    private org.bukkit.event.Listener listener;

    private Listener(org.bukkit.event.Listener listener)
    {
        this.listener = listener;
    }

    public org.bukkit.event.Listener get()
    {
        return listener;
    }

}
