/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.person.statistics.affects.BlurAffect;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Blur implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

		target_.addAffect(spell.getId(),new BlurAffect(caster_,spell.getId(),spell.getDuration().roll()));
		// No nothing on hit.
	}

	public boolean isPositiveEffect() {
		return true;
	}


}
