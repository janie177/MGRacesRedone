package com.minegusta.mgracesredone.playerdata;

import com.google.common.collect.Lists;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import org.bukkit.Material;

import java.util.List;
import java.util.Optional;

public class Binds {

	private List<Bind> binds = Lists.newArrayList();

	public Binds(List<Bind> binds) {
		this.binds = binds;
	}


	public List<Bind> getBinds() {
		return binds;
	}

	public void setBinds(List<Bind> binds) {
		this.binds = binds;
	}

	public boolean isBind(Material item, short data, boolean ignoreData) {
		for (Bind b : binds) {
			if (b.getItem() == item && (b.getData() == data || ignoreData)) {
				return true;
			}
		}
		return false;
	}

	public List<Bind> getBindForAbility(AbilityType type) {
		List<Bind> bList = Lists.newArrayList();
		for (Bind b : getBinds()) {
			if (b.getAbilityType() == type) bList.add(b);
		}
		return bList;
	}

	public void removeBind(Bind b) {
		binds.remove(b);
	}

	public void addBind(Bind b) {
		binds.add(b);
	}

	public Optional<AbilityType> getAbilityForItem(Material item, short data, boolean ignoreData) {
		for (Bind b : binds) {
			if (b.getItem() == item && (ignoreData || data == b.getData())) {
				return Optional.of(b.getAbilityType());
			}
		}
		return Optional.empty();
	}
}
