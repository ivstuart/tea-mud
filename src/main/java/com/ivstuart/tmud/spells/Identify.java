package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Identify implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell) {

		// TODO target items

		caster_.out("Mob identity:"+target_.toString());
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
