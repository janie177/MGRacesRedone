package com.minegusta.mgracesredone.main;


import com.minegusta.mgracesredone.listeners.cure.CureListener;
import com.minegusta.mgracesredone.listeners.general.FallDamageManager;
import com.minegusta.mgracesredone.listeners.general.GeneralListener;
import com.minegusta.mgracesredone.listeners.infection.InfectionListener;
import com.minegusta.mgracesredone.listeners.perkpoints.PerkPointListener;
import com.minegusta.mgracesredone.listeners.racelisteners.*;
import com.minegusta.mgracesredone.races.skilltree.racemenu.MenuListener;

public enum Listener {
    FALL_DAMAGE_MANAGER(new FallDamageManager()),
    GENERAL_LISTENER(new GeneralListener()),
    PERKPOINT_LISTENER(new PerkPointListener()),
    INFECTION_LISTENER(new InfectionListener()),
    ELF_LISTENER(new ElfListener()),
    DWARF_LISTENER(new DwarfListener()),
    AURORA_LISTENER(new AuroraListener()),
    CURE_LISTENER(new CureListener()),
    DEMON_LISTENER(new DemonListener()),
    WEREWOLF_LISTENER(new WereWolfListener()),
    ANGEL_LISTENER(new AngelListener()),
    ENDERBORNLISTENER(new EnderBornListener()),
    MENU_LISTENER(new MenuListener());

    private org.bukkit.event.Listener listener;

    private Listener(org.bukkit.event.Listener listener) {
        this.listener = listener;
    }

    public org.bukkit.event.Listener get() {
        return listener;
    }

}
