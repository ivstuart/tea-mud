/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.common;

/**
 * Created by Ivan on 20/08/2016.
 */
public enum ExitEnum {

    n("north",0,1,0),
    s("south",0,-1,0),
    e("east",1,0,0),
    w("west",-1,0,0),
    u("up",0,0,1),
    d("down",0,0,-1);

    private final String desc;
    private final int dx;
    private final int dy;
    private final int dz;

    ExitEnum(String s, int dx, int dy, int dz) {
        this.desc = s;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public String getDesc() {
        return desc;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getDz() {
        return dz;
    }
}
