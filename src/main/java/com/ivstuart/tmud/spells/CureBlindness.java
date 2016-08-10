package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.person.statistics.Affect;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class CureBlindness implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell) {

		Affect affect = target_.getMobAffects().getAffect("blindness");

		if (affect == null) {
			caster_.out("Spell fizzles as target is not blinded");
			return;
		}

		caster_.out("You cure blindness for "+target_.getName());
		affect.expire(); // too slow a cure
		affect.removeEffect();
		target_.removeAffect("blindness");
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
