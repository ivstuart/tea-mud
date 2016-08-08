package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Mob;

public class Heal implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, int amount_) {

		target_.getHp().increase(amount_);
	}

}
