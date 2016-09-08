/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.constants;


public enum SectorType {
    INSIDE(1),
    CITY(1),
    FIELD(2),
    FOREST(3),
    HILLS(4),
    MOUNTAIN(5),
    DESERT(5),
    WATER_SWIM(10),
    WATER_NOSWIM(100),
    UNDERWATER(2),
    FLYING(1);

    private int moveModify;

    SectorType(int moveModify) {
        this.moveModify = moveModify;
    }

    public int getMoveModify() {
        return moveModify;
    }

    public boolean isInside() {
        return this == INSIDE;
    }
}



