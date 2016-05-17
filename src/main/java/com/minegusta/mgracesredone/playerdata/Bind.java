package com.minegusta.mgracesredone.playerdata;

import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import org.bukkit.Material;

import java.util.List;

public class Bind {

	private List<AbilityType> abilityTypes;
	private Material item;
	private short data;
	private AbilityType active;

	public Bind(List<AbilityType> abilityTypes, Material item, short data) {
		this.abilityTypes = abilityTypes;
		this.active = abilityTypes.get(0);
		this.item = item;
		this.data = data;
	}


	public List<AbilityType> getAbilityTypes() {
		return abilityTypes;
	}

	public void setAbilityType(List<AbilityType> abilityTypes) {
		this.abilityTypes = abilityTypes;
	}

	public Material getItem() {
		return item;
	}

	public AbilityType getActive() {
		return active;
	}

	public void setAbilityType(AbilityType type) {
		this.active = type;
	}

	public boolean scrollActive() {
		if (abilityTypes.size() < 2) return false;
		int index = abilityTypes.indexOf(active) + 1;
		if (abilityTypes.size() - 1 <= index) {
			active = abilityTypes.get(0);
		} else {
			active = abilityTypes.get(index);
		}
		return true;
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
