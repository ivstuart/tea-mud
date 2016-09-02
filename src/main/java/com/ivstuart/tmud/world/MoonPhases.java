package com.ivstuart.tmud.world;

import java.util.Calendar;

/**
 * Created by Ivan on 02/09/2016.
 */
public enum MoonPhases {

    NEW,
    WANING_CRESCENT,
    THIRD_QUARTER,
    WANING_GIBBOUS,
    FULL,
    WAXING_GIBBOUS,
    FIRST_QUARTER,
    WAXING_CRESENT;

    /**
     * This is not designed to be perfect but is good enough for game purposes.
     *
     * @return
     */
    public static MoonPhases getPhase() {
        Calendar calendar = Calendar.getInstance();

        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

        int phaseIndex = (int) (dayOfYear / 3.5) % 8;

        return MoonPhases.values()[phaseIndex];
    }

}
