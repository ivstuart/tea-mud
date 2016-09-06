/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.person.statistics.diseases;

import com.ivstuart.tmud.state.Mob;

/**
 * Created by Ivan on 06/09/2016.
 */
public class Norovirus extends Disease {

    public Norovirus() {
        super();
        setOral(true);
        setDuration(500);
        setInfectionRate(80);
        setCureRate(80);
        setInitialDuration(500);
    }

    public Norovirus(Mob mob_, String desc_, int duration_) {
        super(mob_, desc_, duration_);
        setOral(true);
        setDuration(500);
        setInfectionRate(80);
        setCureRate(50);
        setInitialDuration(500);
    }

    public void applyEffect() {
        super.applyEffect();

        if (_mob.isPlayer()) {
            _mob.getPlayer().getAttributes().getCON().decrease(1);
        }
    }

    public void removeEffect() {
        super.removeEffect();

        if (_mob.isPlayer()) {
            _mob.getPlayer().getAttributes().getCON().increase(1);
        }
    }

    public boolean tick() {
        super.tick();
        _mob.getHp().increasePercentage(-2);
        return false;
    }
}
