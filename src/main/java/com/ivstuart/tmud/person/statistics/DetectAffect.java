package com.ivstuart.tmud.person.statistics;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class DetectAffect extends Affect {

	private final Spell spell;

	public DetectAffect(Mob mob_, String desc_, int duration_) {
		super(mob_, desc_, duration_);
		this.spell = null;
	}

	public DetectAffect(Mob target_, Spell spell) {
		super(target_,spell.getId(),spell.getDuration().roll());
		this.spell = spell;
	}

	@Override
	public void applyEffect() {
		_mob.out("You feel the affects of " + _desc);

		if (spell.getId().equalsIgnoreCase("detect invisible")) {
			_mob.getSenseFlags().add("invis");
			_mob.setDetectInvisible(true);
		}
	}

	@Override
	public void removeEffect() {
		_mob.out("The affects of " + _desc + " wear off");

		if (spell.getId().equalsIgnoreCase("detect invisible")) {
			_mob.setDetectInvisible(false);
		}

	}

	public void setDuration(int duration) {
		this._duration = duration;
	}
}
