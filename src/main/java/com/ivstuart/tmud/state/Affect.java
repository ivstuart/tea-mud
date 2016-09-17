/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
