package com.ivstuart.tmud.person.statistics;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.state.Mob;

public class SancAffect extends Affect {


	private int percentage = 50;

	public SancAffect(Mob mob_, String desc_, int duration_) {
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

		caster_.out("Sanctury absorbs most of the damage");
		return (amount_ * percentage) / 100;

	}
}
