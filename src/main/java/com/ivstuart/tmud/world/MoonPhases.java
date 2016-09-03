package com.ivstuart.tmud.world;

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

    private static final int offset = 6;

    public static int getOffset() {
        return offset;
    }

    /**
     * This is not designed to be perfect but is good enough for game purposes.
     * 16th Sep 2016 is a full moon
     * @return
     */
    public static MoonPhases getPhase() {
        int day = (int) (System.currentTimeMillis() / (1000 * 60 * 60 * 24));

        int dayOfYear = day + offset;

        int phaseIndex = (int) (dayOfYear / 3.5) % 8;

        return MoonPhases.values()[phaseIndex];
    }

}
