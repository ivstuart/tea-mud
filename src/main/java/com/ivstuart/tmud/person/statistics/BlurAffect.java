package com.ivstuart.tmud.person.statistics;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.spells.SpellEffect;
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

		// TODO source target Msg text to room here instead.
		caster_.out("Your blurred image save you from this hit");
		target_.out("You miss due to targets blurry image");
		target_.getRoom().out("Blur saves the day for "+target_.getName());

		return 0;

	}
}
