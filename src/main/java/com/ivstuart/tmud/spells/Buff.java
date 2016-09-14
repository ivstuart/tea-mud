/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.person.statistics.affects.Affect;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Buff implements SpellEffect {


    @Override
    public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

        Affect affect = (Affect) target_.getMobAffects().getAffect(spell.getId());

        if (affect == null) {
            target_.addAffect(spell.getId(), new Affect(target_, spell.getName(), spell.getDuration().roll()));
        } else {
            affect.setDuration(spell.getDuration().roll());
        }

    }

    public boolean isPositiveEffect() {
        return true;
    }

}
