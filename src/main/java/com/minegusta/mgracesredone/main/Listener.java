package com.minegusta.mgracesredone.main;


public enum Listener
{
    L1();

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
