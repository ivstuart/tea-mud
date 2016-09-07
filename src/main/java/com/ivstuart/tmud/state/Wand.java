/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

/**
 * Created by Ivan on 13/08/2016.
 */
public class Wand extends Item {

    private int charges;

    public int getCharges() {
        return charges;
    }

    public void setCharges(int charges) {
        this.charges = charges;
    }

    @Override
    public String toString() {
        return "Wand{" +
                "charges=" + charges +
                '}';
    }

    public void useCharge() {
        charges--;
    }
}
