/*
 *  Copyright 2024. Ivan Stuart
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

import com.ivstuart.tmud.person.statistics.affects.SleepAffect;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.MobEnum;
import com.ivstuart.tmud.state.Spell;

public class Sleep implements SpellEffect {


    @Override
    public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

        if (target_.hasMobEnum(MobEnum.NO_SLEEP)) {
            caster_.out("Your target is no sleep your spell fizzles uselessly");
            return;
        }

        SleepAffect affect = (SleepAffect) target_.getMobAffects().getAffect(spell.getId());

        if (affect == null) {
            target_.addAffect(spell.getId(), new SleepAffect(target_, spell));
        } else {
            affect.setDuration(spell.getDuration().roll());
        }

    }

    @Override
    public boolean isPositiveEffect() {
        return false;
    }


}
