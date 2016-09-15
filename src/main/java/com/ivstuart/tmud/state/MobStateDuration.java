/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

public class MobStateDuration {

	private static final String[] durationDescription = {
			"is about to wear off", "will last for a short while",
			"will last a while", "will last for a long time",
			"will last for hours", "will last for days" };

	private long expiryTimeMillis;

	MobStateDuration(int expirySeconds) {
		expiryTimeMillis = System.currentTimeMillis() + (expirySeconds * 1000);
	}

	public String getDuration() {
		long durationTimeMillis = expiryTimeMillis - System.currentTimeMillis();

		int index = (int) durationTimeMillis / (1000 * 60);

		if (index < 0) {
			index = 0;
		} else if (index > durationDescription.length - 1) {
			index = durationDescription.length - 1;
		}

		return "(" + durationDescription[index] + ")";
	}

	public boolean isExpired() {
		return (expiryTimeMillis - System.currentTimeMillis() < 0);
	}

}
