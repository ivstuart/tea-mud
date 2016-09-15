/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.common;

import java.io.Serializable;

/**
 * Immutable DiceRoll class;
 * 
 * Down sized to short rather than int to save memory space.
 * 
 * @author Ivan Stuart
 * 
 */
public class DiceRoll implements Serializable {

	public static final DiceRoll ONE_D_TEN = new DiceRoll((short) 1, (short) 10);
	public static final DiceRoll THREE_D_SIX = new DiceRoll((short) 3, (short) 6);
	public static final DiceRoll TWO_D_SIX = new DiceRoll((short) 2, (short) 6);
    public static final DiceRoll ONE_D_SIX = new DiceRoll((short) 1, (short) 6);
    public static final DiceRoll ONE_D_FOUR = new DiceRoll((short) 1, (short) 4);
	public static final DiceRoll ONE_D100 = new DiceRoll((short) 1, (short) 100);
	public static final DiceRoll DEFAULT_HP = new DiceRoll((short)2,(short)10,(short)50);
	private static final long serialVersionUID = 1L;
	private static short DEFAULT_NUMBER_OF_SIDES = 6;
	private static short DEFAULT_BOUNUS = 0;
	private static short DEFAULT_NUMBER_OF_DICE = 1;
	private final short diceNumber;
	private final short diceSides;
	private final short diceBonus;

	public DiceRoll() {
		diceSides = DEFAULT_NUMBER_OF_SIDES;
		diceNumber = DEFAULT_NUMBER_OF_DICE;
		diceBonus = DEFAULT_BOUNUS;
	}

	public DiceRoll(short sides) {
		diceSides = sides;
		diceNumber = DEFAULT_NUMBER_OF_DICE;
		diceBonus = DEFAULT_BOUNUS;
	}

	public DiceRoll(short number, short sides) {
		diceNumber = number;
		diceSides = sides;
		diceBonus = DEFAULT_BOUNUS;
	}

	public DiceRoll(short number, short sides, short adder) {
		diceNumber = number;
		diceSides = sides;
		diceBonus = adder;
	}

	public DiceRoll(int number, int sides, int adder) {
		this((short)number,(short)sides,(short)adder);
	}

	/**
	 * Must be of the form 0d0+0 with + being optimal.
	 *
	 * @param xdx_
	 */
	public DiceRoll(String xdx_) {

		int dPos = xdx_.indexOf("d");

		if (dPos == -1) {
			// Now assume we are initialising with just a number i.e. zero dice.
			diceNumber = DEFAULT_BOUNUS;

			diceSides = DEFAULT_NUMBER_OF_SIDES;

			diceBonus = Short.parseShort(xdx_);

			return;

		}

		int plusPos = xdx_.indexOf("+");

		String param1 = xdx_.substring(0, dPos);

		if (plusPos == -1) {
			plusPos = xdx_.length();
		}

		String param2 = xdx_.substring(dPos + 1, plusPos);

		String param3 = "0";

		if (plusPos > -1 && plusPos < xdx_.length()) {
			param3 = xdx_.substring(plusPos + 1);
		}

		diceNumber = Short.parseShort(param1);
		/**
		 * if (diceNumber < 0) { diceNumber = -diceNumber; sign = -1; }
		 */

		diceSides = Short.parseShort(param2);
		diceBonus = Short.parseShort(param3);
	}

	public static void main(String args[]) {
		DiceRoll d = new DiceRoll("3d6+1");
		System.out.println("Roll for " + d + "=" + d.roll());
		d = new DiceRoll("3d6+1");
		System.out.println("Roll for " + d + "=" + d.roll());
		d = new DiceRoll("3d8");
		System.out.println("Roll for " + d + "=" + d.roll());
		d = new DiceRoll("0d6+5");
		System.out.println("Roll for " + d + "=" + d.roll());
		d = new DiceRoll("-4d6+50");
		System.out.println("Roll for " + d + "=" + d.roll());
	}

	public static int roll(int number, int sides, int bonus) {
		int result = 0;

		for (int index = 0; index < number; index++) {
			result += 1 + (int) (Math.random() * sides);
		}

		result += bonus;

		return result;

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DiceRoll other = (DiceRoll) obj;
		if (diceBonus != other.diceBonus) {
			return false;
		}
		if (diceNumber != other.diceNumber) {
			return false;
		}
		if (diceSides != other.diceSides) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + diceBonus;
		result = prime * result + diceNumber;
		result = prime * result + diceSides;
		return result;
	}

	public int roll() {
		int result = 0;

		for (int index = 0; index < diceNumber; index++) {
            result += 1 + (int) (Math.random() * diceSides);
        }

		result += diceBonus;

		return result;

	}

	public boolean rollMoreThan(int i) {
		return roll() > i;
	}

	public boolean rollLessThan(int i) {
		return roll() < i;
	}

	public boolean rollLessThanOrEqualTo(int i) {
		return roll() <= i;
	}

	@Override
	public String toString() {
		return String.format("%dd%d+%d", new Object[] { diceNumber, diceSides,
				diceBonus });
	}

    public int getMaxRoll() {
		return (diceNumber * diceSides) + diceBonus;
    }

}
