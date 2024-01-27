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
import com.ivstuart.tmud.person.statistics.affects.Charmed;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.MobEnum;
import com.ivstuart.tmud.state.Spell;

public class Charm implements SpellEffect {


    private String stat;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @Override
    public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

        if (DiceRoll.ONE_D100.rollLessThanOrEqualTo(50)) {
            caster_.out("Your charm spell fizzles as it does not always work");
            return;
        }

        Charmed affect = (Charmed) target_.getMobAffects().getAffect(spell.getId());

        if (affect == null) {
            target_.addAffect(spell.getId(), new Charmed(target_, spell));
            if (!target_.hasMobEnum(MobEnum.MAGIC_IMMUNE)) {
                target_.setCharmed(caster_);
                caster_.out("You charm a " + target_.getName());
            }
        } else {
            affect.setDuration(spell.getDuration().roll());
        }

    }

    public boolean isPositiveEffect() {
        return true;
    }

}
