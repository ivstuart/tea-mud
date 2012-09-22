package com.ivstuart.tmud.fighting;

import com.ivstuart.tmud.common.DiceRoll;

public class BasicDamage implements Damage {

	private String type;

	private String description = "punch";

	// 1d6+0 as default roll
	private DiceRoll roll;

	// private int damage = 20;

	// private int hitLocation = 0;

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public int roll() {
		return roll.roll();

	}

	public void setRoll(DiceRoll dice) {
		roll = dice;
	}

	@Override
	public void setRoll(int number, int sides, int adder) {
		roll = new DiceRoll((short) number, (short) sides, (short) adder);
	}

}
