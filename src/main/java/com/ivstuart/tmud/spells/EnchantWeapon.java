package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class EnchantWeapon implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell) {

		// TODO target items
		// TODO items have effects
		caster_.out("TODO");
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
