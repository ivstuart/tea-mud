/*
 *  Copyright 2024. Ivan Stuart
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

import com.ivstuart.tmud.state.player.Attribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

import static com.ivstuart.tmud.common.DiceRoll.ONE_D_FOUR;
import static com.ivstuart.tmud.common.DiceRoll.TWO_D_SIX;

public class Weather implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Attribute pressure = new Attribute("pressure", 960, 1040,
            1000);

    private static int monthAdjustment;

    public Attribute getPressure() {

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);

        if ((month >= 9) || (month <= 4)) {
            monthAdjustment = (pressure.getValue() > 985 ? -2 : 2);
        } else {
            monthAdjustment = (pressure.getValue() > 1015 ? -2 : 2);
        }

        pressure.increase(ONE_D_FOUR.roll() * monthAdjustment
                + TWO_D_SIX.roll() - TWO_D_SIX.roll());

        return pressure;
    }

    public String getSunPosition(int hour) {
        return SunPosition.getMessage(hour);

    }


    @Override
    public void run() {
        try {

            WeatherSky sky = World.getWeather().getNextSky(getPressure().getValue());

            World.out("The weather changes to " + sky + " air pressure " + getPressure().getValue());
            World.out(sky.getDesc());

            World.setWeather(sky);

        } catch (Throwable t) {
            LOGGER.error("Problem in fighting thread", t);
        }
    }

}
