package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.person.statistics.BuffStatsAffect;
import com.ivstuart.tmud.person.statistics.SleepAffect;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Sleep implements SpellEffect {


	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	private String stat;

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell) {

		SleepAffect affect = target_.getMobAffects().getSleepAffect(spell.getId());

		if (affect == null) {
			target_.addAffect(new SleepAffect(target_, spell));
		}
		else {
			affect.setDuration(spell.getDuration().roll());
		}

	}

	public boolean isPositiveEffect() {
		return true;
	}

}
