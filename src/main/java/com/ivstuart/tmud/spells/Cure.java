package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Mob;

public class Cure implements SpellEffect {

	@Override
	public void effect(Mob giver_, Mob reciever_, int amount_) {

		reciever_.removeAffect(Poison.POISON);

		reciever_.out("cured");
	}

}
