package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.person.statistics.Affect;
import com.ivstuart.tmud.person.statistics.ArmourBuff;
import com.ivstuart.tmud.state.Mob;

public class ArmourEffect implements SpellEffect {

	public static final String ARMOUR = "armour";

	@Override
	public void effect(Mob giver_, Mob reciever_, int amount_) {

		Affect aff = new ArmourBuff(reciever_, ARMOUR, amount_);

		reciever_.addAffect(aff);

		reciever_.out("Armor buff applied!");

	}

}
