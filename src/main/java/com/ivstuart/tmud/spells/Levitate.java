package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.person.statistics.LevitateAffect;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Levitate implements SpellEffect {


    @Override
    public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

        LevitateAffect affect = (LevitateAffect) target_.getMobAffects().getAffect(spell.getId());

        if (affect == null) {
            target_.addAffect(spell.getId(), new LevitateAffect(target_, spell));
        } else {
            affect.setDuration(spell.getDuration().roll());
        }

    }

    public boolean isPositiveEffect() {
        return true;
    }

}
