package com.ivstuart.tmud.person.statistics;

import com.ivstuart.tmud.common.MobState;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class SleepAffect extends Affect {

	private final Spell spell;

	public SleepAffect(Mob mob_, String desc_, int duration_) {
		super(mob_, desc_, duration_);
		this.spell = null;
	}

	public SleepAffect(Mob target_, Spell spell) {
		super(target_,spell.getId(),spell.getDuration().roll());
		this.spell = spell;
	}

	@Override
	public void applyEffect() {
		_mob.out("You feel the affects of " + _desc);

		if (_mob.getFight().isEngaged()) {
			_mob.out("There is no effect from sleep while fighting" + _desc);
		}
		else {
			_mob.setState(MobState.SLEEP);
		}

	}

	@Override
	public void removeEffect() {
		_mob.out("The affects of " + _desc + " wear off");
		_mob.setState(MobState.WAKE);

	}

	public void setDuration(int duration) {
		this._duration = duration;
	}
}
