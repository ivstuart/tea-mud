package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.person.statistics.Affect;
import com.ivstuart.tmud.person.statistics.DamageOverTime;
import com.ivstuart.tmud.state.Mob;

public class Poison implements SpellEffect {

	public static final String POISON = "poison";

	@Override
	public void effect(Mob giver_, Mob reciever_, int amount_) {

		// TODO Auto-generated method stub
		DamageManager.deal(giver_, reciever_, amount_);

		Affect poisonAffect = new DamageOverTime(reciever_, POISON, 14,
				DiceRoll.ONE_D_SIX);

		reciever_.addAffect(poisonAffect);

		reciever_.out("The effects of poison burn in your blood!");
	}

}
