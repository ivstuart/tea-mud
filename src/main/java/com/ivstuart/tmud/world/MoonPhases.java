/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.world;

import java.time.LocalTime;

/**
 * Created by Ivan on 02/09/2016.
 */
public enum MoonPhases {

    NEW(1),
    WANING_CRESCENT(1),
    THIRD_QUARTER(1),
    WANING_GIBBOUS(1),
    FULL(2),
    WAXING_GIBBOUS(1),
    FIRST_QUARTER(1),
    WAXING_CRESENT(1);

    private static final int offset = 6;
    private final int manaMod;

    MoonPhases(int manaMod) {
        this.manaMod = manaMod;
    }

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

    public static boolean isNightTime() {
        LocalTime now = LocalTime.now();

        return now.getHour() > 19 || now.getHour() < 5;
    }

    public int getManaMod() {
        return manaMod;
    }

}
