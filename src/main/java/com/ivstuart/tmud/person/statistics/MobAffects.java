package com.ivstuart.tmud.person.statistics;

import java.io.Serializable;
import java.util.*;

import com.ivstuart.tmud.state.Mob;

public class MobAffects implements Serializable {

	private static final long serialVersionUID = 5426953002440290562L;

	protected Map<String,Affect> affects;

	public MobAffects() {
		affects = new HashMap<>();

	}

	public void add(String spellId,Affect affect_) {
		affects.put(spellId,affect_);
		affect_.applyEffect();
	}

	public void clear() {
		affects.clear();
	}

	public void remove(String name_) {
		Affect affect = affects.remove(name_);

		if (affect != null) {
			affect.removeEffect();
		}
	}

	public void tick() {
		if (affects.isEmpty()) {
			return;
		}
		Iterator<Affect> affectsIter = affects.values().iterator();

		for (; affectsIter.hasNext();) {
			Affect aff = affectsIter.next();

			aff.tick();

			if (aff.isExpired()) {
				affectsIter.remove();
				aff.removeEffect();
			}

		}
	}

	@Override
	public String toString() {
		if (affects.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		for (Affect aff : affects.values()) {
			sb.append(aff.toString()).append("\n");
		}

		return sb.toString();
	}

	public Affect getAffect(String spellId) {
		return affects.get(spellId);
	}

	public void removeAll() {
		Iterator<Affect> affIter = affects.values().iterator();

		while (affIter.hasNext()) {
			Affect affect = affIter.next();
			affect.removeEffect();
		}
	}

}
