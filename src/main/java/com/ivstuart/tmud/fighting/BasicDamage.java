/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.fighting;

import com.ivstuart.tmud.common.DiceRoll;

public class BasicDamage implements Damage {

	private String type;

	private String description = "punch";

	// 1d6+0 as default roll
	private DiceRoll roll;
	private int multiplier=1;

	// private int damage = 20;

	// private int hitLocation = 0;

	@Override
	public String getDescription() {

		return description;
	}

	@Override
	public String getType() {

        return type;
	}

	@Override
	public int roll() {
		return multiplier * roll.roll();

	}

	@Override
	public void setRoll(int number, int sides, int adder) {
		roll = new DiceRoll((short) number, (short) sides, (short) adder);
	}

	public void setMultiplier(int i) {
		this.multiplier = i;
	}

	public DiceRoll getRoll() {
		return roll;
    }

    public void setRoll(DiceRoll dice) {
        roll = dice;
    }
}
