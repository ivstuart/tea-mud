package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.person.statistics.Affect;
import com.ivstuart.tmud.person.statistics.ArmourBuff;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class ArmourEffect implements SpellEffect {


	@Override
	public void effect(Mob caster, Mob target, Spell spell) {

		Affect armorAffect = new ArmourBuff(target, spell.getId(),spell.getDuration().roll());

		target.addAffect(spell.getId(),armorAffect);

		target.out("Armor buff applied!");

	}

	public boolean isPositiveEffect() {
		return true;
	}

}
