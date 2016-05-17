package com.minegusta.mgracesredone.playerdata;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minegusta.mgracesredone.races.skilltree.abilities.AbilityType;
import org.bukkit.Material;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class Binds {

	private ConcurrentMap<Bind, Boolean> binds = Maps.newConcurrentMap();

	public Binds(List<Bind> binds) {
		this.binds.clear();
		binds.stream().forEach(bind -> this.binds.put(bind, true));
	}


	public Set<Bind> getBinds() {
		return binds.keySet();
	}

	public void setBinds(List<Bind> binds) {
		this.binds.clear();
		binds.stream().forEach(bind -> this.binds.put(bind, true));
	}

	public boolean isBind(Material item, short data, boolean ignoreData) {
		for (Bind b : binds.keySet()) {
			if (b.getItem() == item && (b.getData() == data || ignoreData)) {
				return true;
			}
		}
		return false;
	}

	public Optional<Bind> getBindForItem(Material item, short data, boolean ignoreData) {
		for (Bind b : binds.keySet()) {
			if (b.getItem() == item && (b.getData() == data || ignoreData)) {
				return Optional.of(b);
			}
		}
		return Optional.empty();
	}

	public List<Bind> getBindForAbility(AbilityType type) {
		List<Bind> bList = Lists.newArrayList();
		for (Bind b : getBinds()) {
			if (b.getAbilityTypes().contains(type)) bList.add(b);
		}
		return bList;
	}

	public void removeBind(Bind b) {
		binds.remove(b);
	}

	public void addBind(Bind b) {
		binds.put(b, true);
	}

	public Optional<List<AbilityType>> getAbilityForItem(Material item, short data, boolean ignoreData) {
		for (Bind b : binds.keySet()) {
			if (b.getItem() == item && (ignoreData || data == b.getData())) {
				return Optional.of(b.getAbilityTypes());
			}
		}
		return Optional.empty();
	}
}
