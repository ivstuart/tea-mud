package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.person.statistics.Affect;
import com.ivstuart.tmud.person.statistics.ArmourBuff;
import com.ivstuart.tmud.state.Mob;

public class ArmourEffect implements SpellEffect {

	public static final String ARMOUR = "armour";

	@Override
	public void effect(Mob caster, Mob target, int amount) {

		Affect armorAffect = new ArmourBuff(target, ARMOUR, amount);

		target.addAffect(armorAffect);

		target.out("Armor buff applied!");

	}

}
