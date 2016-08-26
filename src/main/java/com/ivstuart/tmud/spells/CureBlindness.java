package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.person.statistics.Affect;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

import static com.ivstuart.tmud.constants.SpellNames.BLINDNESS;

public class CureBlindness implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

		Affect affect = target_.getMobAffects().getAffect(BLINDNESS);

		if (affect == null) {
			caster_.out("Spell fizzles as target is not blinded");
			return;
		}
		caster_.out("You cure blindness for "+target_.getName());

		affect.removeEffect();
		target_.removeAffect(BLINDNESS);
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
