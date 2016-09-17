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

package com.ivstuart.tmud.world;

public enum SunPostion {

    DAY_TIME("The day has begun", 9),
    NIGHTTIME("The night has begun", 20),
    SUN_RISE("The sun rises in the east", 7),
    SUN_SET("The sun slowly disappears in the west", 18);

    private String desc;
    private int hour;

    private SunPostion(String desc, int hour) {
        this.desc = desc;
        this.hour = hour;
    }

	public static String getMessage(int hour) {
		for (SunPostion pos : values()) {
			if (pos.getHour() == hour) {
				return pos.getDesc();
			}
		}
		return "";
	}

	public String getDesc() {
		return desc;
	}

	public int getHour() {
		return hour;
	}
}
