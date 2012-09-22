package com.ivstuart.tmud.world;

import com.ivstuart.tmud.state.Attribute;

import static com.ivstuart.tmud.common.DiceRoll.ONE_D_FOUR;
import static com.ivstuart.tmud.common.DiceRoll.TWO_D_SIX;

public class Weather {

	private static Attribute pressure = new Attribute("pressure", 960, 1040,
			1000);

	private static int month;
	private static int monthAdjustment;

	private static WeatherSky sky = WeatherSky.CLOUDLESS;

	public String getSunPosition(int hour) {
		return SunPostion.getMessage(hour);

	}

	public WeatherSky updatePressure() {

		if ((month >= 9) && (month <= 16)) {
			monthAdjustment = (pressure.getValue() > 985 ? -2 : 2);
		} else {
			monthAdjustment = (pressure.getValue() > 1015 ? -2 : 2);
		}

		pressure.increase(ONE_D_FOUR.roll() * monthAdjustment
				+ TWO_D_SIX.roll() - TWO_D_SIX.roll());

		return sky.getNextSky(pressure.getValue());
	}

}
