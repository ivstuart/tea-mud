/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.person.statistics.diseases;

import com.ivstuart.tmud.state.Mob;

/**
 * Created by Ivan on 06/09/2016.
 */
public class Vampirism extends Disease {

    public Vampirism() {
        super();
        setVector(true);
        setDuration(500);
        setInfectionRate(80);
        setCureRate(80);
        setInitialDuration(500);
    }

    public Vampirism(Mob mob_, String desc_, int duration_) {
        super(mob_, desc_, duration_);
        setVector(true);
        setDuration(500);
        setInfectionRate(80);
        setCureRate(50);
        setInitialDuration(500);
    }

    public void applyEffect() {
        super.applyEffect();

        if (_mob.isPlayer()) {
            _mob.getPlayer().getAttributes().getSTR().increase(1);
        }
    }

    public void removeEffect() {
        super.removeEffect();

        if (_mob.isPlayer()) {
            _mob.getPlayer().getAttributes().getSTR().decrease(1);
        }
    }

    public boolean tick() {
        super.tick();
        return false;
    }
}
