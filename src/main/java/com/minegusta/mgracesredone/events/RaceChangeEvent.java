package com.minegusta.mgracesredone.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RaceChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private String to;
    private String from;
    private Player player;

    public RaceChangeEvent(Player who, String to, String from) {
        this.to = to;
        this.player = who;
        this.from = from;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public String to() {
        return to;
    }

    public String from() {
        return from;
    }

    public Player getPlayer() {
        return player;
    }
}
