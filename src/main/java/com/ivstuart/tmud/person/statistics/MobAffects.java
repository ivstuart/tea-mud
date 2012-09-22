package com.ivstuart.tmud.person.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ivstuart.tmud.state.Mob;

public class MobAffects implements Serializable {

	private static final long serialVersionUID = 5426953002440290562L;

	protected List<Affect> _affects;
	protected Mob _mob;

	public MobAffects(Mob mob_) {
		_mob = mob_;
		_affects = new ArrayList<Affect>();

	}

	public void add(Affect affect_) {
		_affects.add(affect_);
		affect_.applyEffect();
	}

	public void clear() {
		_affects.clear();
	}

	public void remove(String name_) {
		Iterator<Affect> affIter = _affects.iterator();

		while (affIter.hasNext()) {
			Affect affect = affIter.next();
			if (name_.equals(affect.getDesc())) {
				affIter.remove();
			}
		}
	}

	public void tick() {
		Iterator<Affect> affectsIter = _affects.iterator();

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
		if (_affects.size() == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		for (Affect aff : _affects) {
			sb.append(aff.toString()).append("\n");
		}

		return sb.toString();
	}
}
