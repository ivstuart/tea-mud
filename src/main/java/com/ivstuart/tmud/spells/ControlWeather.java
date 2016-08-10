package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class ControlWeather implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell) {

		// TODO make it worse or better incrementally.
		caster_.out("TODO");
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
