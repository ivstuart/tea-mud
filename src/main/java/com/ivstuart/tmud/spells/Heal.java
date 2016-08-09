package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Heal implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell) {

		target_.getHp().increase(spell.getDamage().roll());
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
