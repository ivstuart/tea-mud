package com.ivstuart.tmud.behaviour;

import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.state.Mob;

/**
 * Created by Ivan on 15/08/2016.
 */
public class BaseBehaviour implements Tickable {

    protected Mob mob;

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
}
