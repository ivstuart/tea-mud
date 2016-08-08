package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.person.statistics.BlurAffect;
import com.ivstuart.tmud.person.statistics.SancAffect;
import com.ivstuart.tmud.state.Mob;

public class Sanctury implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, int amount_) {

		target_.addAffect(new SancAffect(caster_,"sanctury",amount_));
		// No nothing on hit.
	}



}
