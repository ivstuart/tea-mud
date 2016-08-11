package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Refresh implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

		target_.getMv().increase(spell.getDamage().roll());
		target_.out("You feel invigorated.");
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
