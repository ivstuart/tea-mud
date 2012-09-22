package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.state.Mob;

public class Damage implements SpellEffect {

	@Override
	public void effect(Mob giver_, Mob reciever, int amount_) {

		// TODO Auto-generated method stub
		DamageManager.deal(giver_, reciever, amount_);
	}

}
