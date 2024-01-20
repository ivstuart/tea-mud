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
import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.person.statistics.affects.Affect;
import com.ivstuart.tmud.person.statistics.affects.DamageOverTime;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Poison implements SpellEffect {

    public static final String POISON = "poison";

    @Override
    public void effect(Mob giver, Mob receiver, Spell spell, Item targetItem) {


        DamageManager.deal(giver, receiver, spell.getDamage().roll(), spell);

        Affect poisonAffect = new DamageOverTime(receiver, POISON, 14,
                DiceRoll.ONE_D_SIX);

        receiver.addAffect(poisonAffect);

        receiver.out("The effects of poison burn in your blood!");
    }

    public boolean isPositiveEffect() {
        return false;
    }

}
