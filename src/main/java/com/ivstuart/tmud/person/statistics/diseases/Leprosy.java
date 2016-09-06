/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.person.statistics.diseases;

import com.ivstuart.tmud.state.Mob;

/**
 * Created by Ivan on 06/09/2016.
 */
public class Leprosy extends Disease {

    public Leprosy() {
        super();
        setIndirectContact(true);
        setDuration(500);
        setInfectionRate(100);
        setCureRate(80);
        setInitialDuration(500);
    }

    public Leprosy(Mob mob_, String desc_, int duration_) {
        super(mob_, desc_, duration_);
        setIndirectContact(true);
        setDuration(500);
        setInfectionRate(100);
        setCureRate(50);
        setInitialDuration(500);
    }

    public void applyEffect() {
        super.applyEffect();

        if (_mob.isPlayer()) {
            _mob.getPlayer().getAttributes().getDEX().decrease(1);
        }
    }

    public void removeEffect() {
        super.removeEffect();

        if (_mob.isPlayer()) {
            _mob.getPlayer().getAttributes().getDEX().increase(1);
        }
    }

    public boolean tick() {
        super.tick();
        return false;
    }
}
