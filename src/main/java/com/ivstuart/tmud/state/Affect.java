/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

public class Affect {

	private static String[] durationDescription = { "is about to wear off",
			"will last for a short while", "will last a while",
			"will last for a long time", "will last for hours",
			"will last for days" };

	private String id;
	private long expiryTimeMillis;

	public Affect(String id, int durationInSeconds) {
		this.id = id;
		expiryTimeMillis = System.currentTimeMillis()
				+ (durationInSeconds * 1000);
	}

	public String getDescription() {
		long durationTimeMillis = expiryTimeMillis - System.currentTimeMillis();

		int index = (int) durationTimeMillis / (1000 * 60);

		if (index < 0) {
			index = 0;
		} else if (index > durationDescription.length - 1) {
			index = durationDescription.length - 1;
		}

		return id + "(" + durationDescription[index] + ")";
	}

	public boolean isExpired() {
		return (expiryTimeMillis - System.currentTimeMillis() < 0);
	}
}
