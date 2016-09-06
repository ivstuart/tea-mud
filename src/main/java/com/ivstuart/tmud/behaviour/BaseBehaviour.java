/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.state.Mob;

/**
 * Created by Ivan on 15/08/2016.
 */
public class BaseBehaviour implements Tickable {

    protected Mob mob;
    protected int parameter;
    protected int parameter2;
    protected String parameter3;

    public String getParameter3() {
        return parameter3;
    }

    public void setParameter3(String parameter3) {
        this.parameter3 = parameter3;
    }

    public int getParameter2() {
        return parameter2;
    }

    public void setParameter2(int parameter2) {
        this.parameter2 = parameter2;
    }
    @Override
    public String getId() {
        return null;
    }

    @Override
    public boolean tick() {
        return false;
    }

    public void setMob(Mob mob) {
        this.mob = mob;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }
}
