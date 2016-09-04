/*
 * Created on 09-Sep-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.ivstuart.tmud.person;

import com.ivstuart.tmud.state.Ability;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author stuarti
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Learned implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<String, Ability> abilities = null;

	public Learned() {
		// lazy initialisation of this as most mobs will have no skills.
		abilities = new HashMap<String, Ability>();
	}

	public void add(Ability ability) {
		abilities.put(ability.getId(), ability);

	}

	public Collection<Ability> getAbilities() {
		return abilities.values();
	}

	public Ability getAbility(String name) {

		Ability ability = abilities.get(name);

		if (ability == null) {
			ability = Ability.NULL_ABILITY;
		}

		return ability;

	}

	/**
	 * @param name_
	 * @return
	 */
	public boolean hasLearned(String name) {
		return abilities.containsKey(name);
	}

	public boolean hasPrereq(String prereq) {

		if (prereq == null) {
			return true;
		}

		Ability ab = getAbility(prereq);

		if (null == ab || ab.isNull()) {
			return false;
		}

		if (ab.getSkill() > 50) {
			return true;
		}

		return false;
	}

	public Ability remove(String name) {
		return abilities.remove(name);
	}

	public int getAbilityPoints() {
		int abilityPoints = 0;

		for (Ability ab : abilities.values()) {
			abilityPoints += ab.getSkill();
		}

		return abilityPoints;
	}
}
