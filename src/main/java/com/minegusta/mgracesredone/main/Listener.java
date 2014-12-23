package com.minegusta.mgracesredone.main;


import com.minegusta.mgracesredone.listeners.general.GeneralListener;

public enum Listener
{
    GENERAL_LISTENER(new GeneralListener());

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
