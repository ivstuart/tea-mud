package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.person.statistics.BuffStatsAffect;
import com.ivstuart.tmud.person.statistics.SancAffect;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class BuffStats implements SpellEffect {


	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	private String stat;

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell) {

		BuffStatsAffect affect = (BuffStatsAffect)target_.getMobAffects().getAffect(spell.getId());

		if (affect == null) {
			target_.addAffect(spell.getId(),new BuffStatsAffect(target_, spell));
		}
		else {
			affect.setDuration(spell.getDuration().roll());
		}

	}

	public boolean isPositiveEffect() {
		return true;
	}

}