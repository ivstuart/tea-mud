/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.DiceRoll;

public class Armour extends Item {

	private static final long serialVersionUID = 1L;

	private static final int HEAD = 0;
	private static final int BODY = 1;
	private static final int ARMS = 2;
	private static final int WAIST = 3;
	private static final int LEGS = 4;
	private static final int FEET = 5;
	private static final int ALL = 6;

	private int[] slots = new int[6];

	public Armour() {
	}

	public Armour(int armour) {
		setArmourBuff(armour);
	}

	public void add(Armour armour) {
		for (int i = HEAD; i < ALL; i++) {
			slots[i] += armour.slots[i];
		}
	}

	public int getAll() {
		return slots[ALL];
	}

	public int getArms() {
		return slots[ARMS];
	}

	public int getBody() {
		return slots[BODY];
	}

	public int getFeet() {
		return slots[FEET];
	}

	public int getHead() {
		return slots[HEAD];
	}

	public int getLegs() {
		return slots[LEGS];
	}

	public int getWaist() {
		return slots[WAIST];
	}

	public void setArmour(String list) {
		int index = 0;
		for (String value : list.split(" ")) {
			slots[index] = Integer.parseInt(value);
		}
	}
	
	public int getRandomLocation() {
		return DiceRoll.ONE_D_SIX.roll() - 1 ;
	}
	
	public int getRandomSlot() {
		return slots[this.getRandomLocation()];
	}

    public int getAverage() {
    	int total = 0;
		for (int i = HEAD; i < ALL; i++) {
			total += slots[i];
		}
		return total / ALL;
    }

	public void setArmourBuff(int armourBuff) {
		for (int i = HEAD; i < ALL; i++) {
			slots[i] += armourBuff;
		}
	}
}
