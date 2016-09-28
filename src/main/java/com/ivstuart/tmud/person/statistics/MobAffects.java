/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ivstuart.tmud.person.statistics;

import com.ivstuart.tmud.person.statistics.affects.Affect;
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.*;

public class MobAffects implements Serializable, Cloneable {

	private static final long serialVersionUID = 5426953002440290562L;
	private static final Logger LOGGER = LogManager.getLogger();
	protected Map<String,Affect> affects;

	public MobAffects() {
		affects = new HashMap<>();

	}

	public void add(String id, Affect affect_) {
		// One effect applied only once so that disease and buffs do not stack for same id.
		if (!affects.containsKey(id)) {
			affect_.applyEffect();
		}
		affects.put(id, affect_);
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

	public String look() {
		if (affects.isEmpty()) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		for (Affect aff : affects.values()) {
			sb.append(aff.look()).append("\n");
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

		affects.clear();
	}

    public boolean hasAffect(String levitate) {
    	return affects.containsKey(levitate);
    }

	public List<Disease> getDiseases() {
		List<Disease> diseases = new ArrayList<>();
		for (Affect affect : affects.values()) {
			if (affect instanceof Disease) {
				diseases.add((Disease) affect);
			}
		}
		return diseases;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			LOGGER.error("Problem cloning object", e);
		}
		return null;
	}

	@Override
	public String toString() {
		return "MobAffects{" +
				"affects=" + affects +
				'}';
	}
}
