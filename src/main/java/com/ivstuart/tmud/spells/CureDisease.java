/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.person.statistics.diseases.Disease;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

import java.util.List;

public class CureDisease implements SpellEffect {

    @Override
    public void effect(Mob giver_, Mob reciever_, Spell spell, Item targetItem) {

        List<Disease> diseaseList = reciever_.getMobAffects().getDiseases();

        for (Disease disease : diseaseList) {

            if (DiceRoll.ONE_D100.rollLessThanOrEqualTo(disease.getCureRate())) {

                reciever_.removeAffect(disease.getId());

                reciever_.out("You cured " + reciever_.getName() + " of " + disease.getId());

            }
        }
    }

    public boolean isPositiveEffect() {
        return true;
    }

}
