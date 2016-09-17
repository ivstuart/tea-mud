/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
