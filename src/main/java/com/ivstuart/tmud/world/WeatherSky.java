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

public enum WeatherSky {

    CLOUDLESS(990, 1010, "The clouds disappear"),
    CLOUDY(970, 990, "The sky starts to get cloudy"),
    RAINING(1010, 1030, "It starts to rain"),
    LIGHTNING(990, 1010, "Lightning starts to show in the sky"),
    DOWNPOUR(970, 990, "The heavens open up as there is a down pour of rain"),
    WINDY(970, 990, "It its so windy you are nearly blown to the ground"),
    FOG(970, 990, "A fog descends reducing visibility", true),
    FREEZING_FOG(970, 990, "An icy fog closes in all around", true),
    HAIL(970, 990, "Hail stones fall from the sky, ouch", true),
    SNOW(970, 990, "It is snowing how pretty", true),
    SLEET(970, 990, "It is wet and icy as sleet blows in", true),
    HURRICANE(970, 990, "The high wind is dangerous seek cover", true),
    BLIZZARD(970, 990, "The snow storm reduces visibility", true);


    private String desc;
    private int low;
    private int high;
    private int humidity;
    private int temperature;
    private boolean blocksScan;

    WeatherSky(int low, int high, String desc) {
        this.low = low;
        this.high = high;
        this.desc = desc;
    }

    WeatherSky(int low, int high, String desc, boolean blocksScan) {
        this.low = low;
        this.high = high;
        this.desc = desc;
        this.blocksScan = blocksScan;
    }

    public boolean isBlocksScan() {
        return blocksScan;
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

    public WeatherSky getRandom() {
        return values()[(int) (values().length * Math.random())];
    }

    private WeatherSky worseWeather() {
        int ordinal = this.ordinal();

        if (--ordinal < 0) {
            ordinal = 0;
        }
        return values()[ordinal];
    }

}
