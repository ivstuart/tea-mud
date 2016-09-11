/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.person.statistics.affects.SleepAffect;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Sleep implements SpellEffect {


	private String stat;

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

		if (target_.isNoSleep()) {
			caster_.out("Your target is no sleep your spell fizzles uselessly");
			return;
		}

		SleepAffect affect = (SleepAffect)target_.getMobAffects().getAffect(spell.getId());

		if (affect == null) {
			target_.addAffect(spell.getId(),new SleepAffect(target_, spell));
		}
		else {
			affect.setDuration(spell.getDuration().roll());
		}

	}

	public boolean isPositiveEffect() {
		return true;
	}



}
