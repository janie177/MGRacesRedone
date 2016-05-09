package com.minegusta.mgracesredone.playerdata;

import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import org.bukkit.Material;

public class Bind {

	private AbilityType abilityType;
	private Material item;
	private short data;

	public Bind(AbilityType abilityType, Material item, short data) {
		this.abilityType = abilityType;
		this.item = item;
		this.data = data;
	}


	public AbilityType getAbilityType() {
		return abilityType;
	}

	public void setAbilityType(AbilityType abilityType) {
		this.abilityType = abilityType;
	}

	public Material getItem() {
		return item;
	}

	public void setItem(Material item) {
		this.item = item;
	}

	public short getData() {
		return data;
	}

	public void setData(short data) {
		this.data = data;
	}
}
