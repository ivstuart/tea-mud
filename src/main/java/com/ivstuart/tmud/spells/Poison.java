package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.person.statistics.Affect;
import com.ivstuart.tmud.person.statistics.DamageOverTime;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Poison implements SpellEffect {

	public static final String POISON = "poison";

	@Override
	public void effect(Mob giver_, Mob reciever_, Spell spell) {

		// TODO Auto-generated method stub
		DamageManager.deal(giver_, reciever_, spell.getDamage().roll());

		Affect poisonAffect = new DamageOverTime(reciever_, POISON, 14,
				DiceRoll.ONE_D_SIX);

		reciever_.addAffect(poisonAffect);

		reciever_.out("The effects of poison burn in your blood!");
	}

	public boolean isPositiveEffect() {
		return false;
	}

}
