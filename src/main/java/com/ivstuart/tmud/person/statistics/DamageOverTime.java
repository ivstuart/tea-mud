/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.person.statistics;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.state.Mob;

public class DamageOverTime extends Affect {

	protected DiceRoll _damageRoll = null;

	public DamageOverTime(Mob mob_, String desc_, int duration_,
			DiceRoll damage_) {
		super(mob_, desc_, duration_);
		_damageRoll = damage_;
	}

	@Override
    public boolean tick() {
        super.tick();

		int damage = _damageRoll.roll();

		_mob.getHp().decrease(damage);

		String msg = this._desc + " deals you " + damage + " damage";

		_mob.out(msg);
        return false;

	}

	@Override
	public String toString() {
		return super.toString() + " " + _damageRoll;
	}

}
