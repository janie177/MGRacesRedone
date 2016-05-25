package com.minegusta.mgracesredone.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class RaceChangeEvent extends PlayerEvent {

	private String to;
	private String from;

	public RaceChangeEvent(Player who, String to, String from) {
		super(who);
		this.to = to;
		this.from = from;
	}

	@Override
	public HandlerList getHandlers() {
		return null;
	}

	public String to() {
		return to;
	}

	public String from() {
		return from;
	}
}
