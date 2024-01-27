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

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.MobEnum;
import com.ivstuart.tmud.state.Spell;

/**
 * Created by Ivan on 28/08/2016.
 */
public class Blindness extends BuffStats {

    @Override
    public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

        if (target_.hasMobEnum(MobEnum.MAGIC_IMMUNE)) {
            caster_.out("Your target is immune to magic your spell fizzles uselessly");
            return;
        }

        super.effect(caster_, target_, spell, targetItem);


    }

    @Override
    public boolean isPositiveEffect() {
        return false;
    }
}
