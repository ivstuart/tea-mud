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
