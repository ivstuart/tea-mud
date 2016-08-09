
package com.ivstuart.tmud.person.statistics;

import com.ivstuart.tmud.state.Mob;

public class DetectBuff extends Affect {

	public DetectBuff(Mob mob_, String desc_, int duration_) {
		super(mob_, desc_, duration_);
	}

	@Override
	public void applyEffect() {
		_mob.out("You feel the affects of " + _desc);
		_mob.getSenseFlags().add("invis");
	}

	@Override
	public void removeEffect() {
		_mob.out("The affects of " + _desc + " wear off");

	}
}
