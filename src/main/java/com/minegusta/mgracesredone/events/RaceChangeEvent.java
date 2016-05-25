package com.minegusta.mgracesredone.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class RaceChangeEvent extends PlayerEvent {
	public RaceChangeEvent(Player who) {
		super(who);
	}

	@Override
	public HandlerList getHandlers() {
		return null;
	}
}
