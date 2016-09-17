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

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.command.ability.Cast;
import com.ivstuart.tmud.constants.SkillNames;
import com.ivstuart.tmud.world.World;

/**
 * Created by Ivan on 13/08/2016.
 */
public class Scroll extends Item {

    @Override
    public boolean isRecitable() {
        return true;
    }

    public void recite(Mob mob, String input_) {

        mob.out("You recite a scroll");

        Spell spell = World.getSpell(this.getProperties());

        if (spell == null) {
            mob.out("The spell " + this.getProperties() + " is gibberish");
            return;
        }

        Ability ability = mob.getLearned().getAbility(SkillNames.SCROLLS);

        if (ability.isNull()) {
            mob.out("You have no skill with scrolls");
            return;
        }

        if (ability.isSuccessful(mob)) {

            new Cast().execute(mob, spell, ability, input_, false);
        } else {
            mob.out("You mumble the critical words of the incantation, the spell fails.");
        }
    }
}
