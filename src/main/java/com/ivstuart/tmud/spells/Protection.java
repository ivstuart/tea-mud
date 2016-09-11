/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.person.statistics.affects.Affect;
import com.ivstuart.tmud.person.statistics.affects.ArmourBuff;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Protection implements SpellEffect {


	@Override
	public void effect(Mob caster, Mob target, Spell spell, Item targetItem) {

		Affect armorAffect = new ArmourBuff(target, spell.getId(),spell.getDuration().roll());

		armorAffect.setAmount(spell.getAmount());

		target.addAffect(spell.getId(),armorAffect);

		target.out("Armor buff applied!");

	}

	public boolean isPositiveEffect() {
		return true;
	}

}
