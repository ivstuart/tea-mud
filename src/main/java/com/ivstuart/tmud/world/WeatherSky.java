package com.ivstuart.tmud.world;

public enum WeatherSky {

	CLOUDLESS(990, 1010, "The clouds disappear"), CLOUDY(970, 990,
			"The sky starts to get cloudy"), RAINING(1010, 1030,
			"It starts to rain"), LIGHTNING(990, 1010,
			"Lightning starts to show in the sky");

	/**
	 * TODO consider the transitions "The rain stops"; "The lightning stops";
	 * 
	 * HAIL SNOW DOWNPOUR WINDY HURRICANE BLIZZARD
	 */

	private String desc;
	private int low;
	private int high;

	private WeatherSky(int low, int high, String desc) {
		this.low = low;
		this.high = high;
		this.desc = desc;
	}

	private WeatherSky betterWeather() {
		int ordinal = this.ordinal();

		if (++ordinal > values().length) {
			ordinal--;
		}
		return values()[ordinal];

	}

	public String getDesc() {
		return desc;
	}

	public WeatherSky getNextSky(int pressure) {

		if (pressure < low) {
			return worseWeather();
		} else if (pressure > high) {
			return betterWeather();
		}
		return this;
	}

	private WeatherSky worseWeather() {
		int ordinal = this.ordinal();

		if (--ordinal < 0) {
			ordinal = 0;
		}
		return values()[ordinal];
	}

}
