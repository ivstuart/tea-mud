package com.ivstuart.tmud.person.statistics;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Msg;
import com.ivstuart.tmud.state.Mob;

public class BlurAffect extends Affect {


	public BlurAffect(Mob mob_, String desc_, int duration_) {
		super(mob_, desc_, duration_);
	}

	@Override
	public void applyEffect() {
		_mob.out("You feel the affects of " + _desc);
	}

	@Override
	public void removeEffect() {
		_mob.out("The affects of " + _desc + " wear off");

	}

	public int onHit(Mob caster_, Mob target_, int amount_) {

		if(DiceRoll.ONE_D100.rollMoreThan(10)) {
			// no effect 90% of the time
			return amount_;
		}

        target_.getRoom().out(new Msg(caster_, target_, "Blur saves the day for <S-NAME> from <T-NAME>"));

		return 0;

	}
}
