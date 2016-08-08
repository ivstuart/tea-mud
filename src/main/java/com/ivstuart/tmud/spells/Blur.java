package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.person.statistics.Affect;
import com.ivstuart.tmud.person.statistics.BlurAffect;
import com.ivstuart.tmud.state.Mob;

public class Blur implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, int amount_) {

		target_.addAffect(new BlurAffect(caster_,"blur",amount_));
		// No nothing on hit.
	}



}
