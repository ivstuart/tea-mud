/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.person.statistics.diseases;

import com.ivstuart.tmud.state.Mob;

/**
 * Created by Ivan on 06/09/2016.
 */
public class MummyRot extends Disease {

    public MummyRot() {
        super();
        setDroplets(true);
        setDuration(500);
        setInfectionRate(10);
        setCureRate(50);
        setInitialDuration(500);
    }

    public MummyRot(Mob mob_, String desc_, int duration_) {
        super(mob_, desc_, duration_);
        setDroplets(true);
        setDuration(500);
        setInfectionRate(10);
        setCureRate(50);
        setInitialDuration(500);
    }

    public void applyEffect() {
        super.applyEffect();

        if (_mob.isPlayer()) {
            _mob.getPlayer().getAttributes().getSTR().decrease(3);
        }
    }

    public void removeEffect() {
        super.removeEffect();

        if (_mob.isPlayer()) {
            _mob.getPlayer().getAttributes().getSTR().increase(3);
        }
    }

    public boolean tick() {
        return super.tick();
    }
}
