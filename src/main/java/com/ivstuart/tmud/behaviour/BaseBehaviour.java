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
    public void tick() {

    }

    public void setMob(Mob mob) {
        this.mob = mob;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }
}
