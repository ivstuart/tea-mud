package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.person.statistics.BlurAffect;
import com.ivstuart.tmud.person.statistics.SancAffect;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Sanctury implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell) {

		target_.addAffect(spell.getId(),new SancAffect(caster_,spell.getId(),spell.getDuration().roll()));
		// No nothing on hit.
	}

	public boolean isPositiveEffect() {
		return true;
	}


}
