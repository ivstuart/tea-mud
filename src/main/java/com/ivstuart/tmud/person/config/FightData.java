/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.person.config;

import com.ivstuart.tmud.common.Colour;

import java.io.Serializable;
import java.util.Arrays;

/*
 * This class is simple a data class to store the configuration 
 * for the players character
 */
public class FightData implements Serializable {

	public static final int JOINTLOCK = 0;
	public static final int CHOKE = 1;
	public static final int BITE = 2;
	public static final int ELBOW = 3;
	public static final int KNEE = 4;
	public static final int HEADBUTT = 5;
	public static final int POWER = 6;
	public static final int AGGRESSIVE = 7;
	public static final int TARGET = 8;
	public static final int GROUND = 9;
	public static final int BATTLEGROUND = 10;
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final String FLAG_NAME[] = { "JOINTLOCK", "CHOKE", "BITE",
			"ELBOW", "KNEE", "HEADBUTT", "POWER", "AGGRESSIVE", "TARGET",
			"GROUND", "BATTLEGROUND"};

	private static final String TRUE_DESCRIPTION[] = {
			"You WILL attempt wrist-bends and arm hyperextensions.",
			"You WILL attempt to choke your victim.",
			"You WILL attempt to bite your victim.",
			"You WILL use your elbows.", "You WILL use your knees.",
			"You WILL headbutt.",
			"You will attack to try to inflict as much damage as possible",
			"You will fight aggressively",
			"You will try to hit weakness in the opponents armour",
			"You will get involved in ground fighting",
			"You will involve yourself in a battle ground"};

	private static final String FALSE_DESCRIPTION[] = {
			"You WILL NOT attempt wrist-bends and arm hyperextensions.",
			"You WILL NOT attempt to choke your victim.",
			"You WILL NOT attempt to bite your victim.",
			"You WILL NOT use your elbows.", "You WILL NOT use your knees.",
			"You WILL NOT headbutt.", "You will try to get any hits in",
			"You will fight defensively", "You will try to get any hits in",
			"You will avoid ground fighting at all costs",
			"You will opt out from the battle ground"};

	private boolean mData[] = new boolean[FLAG_NAME.length];

	public FightData() {
		for (int index = 0; index < mData.length; index++) {
			mData[index] = true;
		}
	}

	public boolean is(int index) {
		return mData[index];
	}

	@Override
	public String toString() {
		return "FightData{" +
				"mData=" + Arrays.toString(mData) +
				'}';
	}

	public String look() {
		StringBuilder sb = new StringBuilder("");
		sb.append("Groundfighting Options:\n");

		for (int index = 0; index < mData.length; index++) {
			if (index == 6) {
				sb.append("\nNormal Combat Options:\n");
			}

			if (mData[index]) {
				sb.append(String.format("[" + Colour.BLUE + " %1$10s"
						+ Colour.WHITE + " ] %2$s\n", FLAG_NAME[index],
						this.toString(index)));
			} else {
				sb.append(String.format("[" + Colour.GREY + " %1$10s"
						+ Colour.WHITE + " ] %2$s\n",
						FLAG_NAME[index].toLowerCase(), this.toString(index)));
			}

		}

		return sb.toString();
	}

	public void set(int index, boolean state) {
		mData[index] = state;
	}

	private void toggle(int index) {
		mData[index] = !mData[index];
	}

	public String toggle(String flagName) {
		for (int index = 0; index < mData.length; index++) {
			if (FLAG_NAME[index].equalsIgnoreCase(flagName)) {
				this.toggle(index);
				return toString(index);
			}
		}
		return "No such configuration option" + flagName;
	}

	private String toString(int flag) {
		if (mData[flag]) {
			return TRUE_DESCRIPTION[flag];
		}
		return FALSE_DESCRIPTION[flag];
	}

	public int getRandomAttackType() {
		int index = (int) (Math.random() * 6);

		LOOP:
		for (int i = 0; i < 6; i++) {
			if (!this.is(index)) {
				index++;
				index = index % 6;
				continue LOOP;
			}
			return index;
		}

		return index;
	}
}
