package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Cure implements SpellEffect {

	@Override
	public void effect(Mob giver_, Mob reciever_, Spell spell) {

		reciever_.removeAffect(Poison.POISON);

		reciever_.out("cured");
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
