/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

/**
 * Created by Ivan on 28/08/2016.
 */
public class Blindness extends BuffStats {

    @Override
    public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

        if (target_.isNoBlind()) {
            caster_.out("Your target is no blind your spell fizzles uselessly");
            return;
        }

        super.effect(caster_, target_, spell, targetItem);


    }

    @Override
    public boolean isPositiveEffect() {
        return false;
    }
}
