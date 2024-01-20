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

import com.ivstuart.tmud.person.movement.MoveManager;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.Spell;

/**
 * Created by Ivan on 09/08/2016.
 */
public class Summon implements SpellEffect {

    public void effect(Mob caster, Mob target, Spell spell, Item targetItem) {

        if (caster.getFight().isEngaged()) {
            caster.out("You summon fizzles uselessly can not be used in combat");
            return;
        }

        // check same alignment
        if (caster.isGood() != target.isGood()) {
            caster.out("You may only summon players of your own alignment");
            return;
        }

        if (target.isNoSummon()) {
            caster.out("Your target is no summon your spell fizzles uselessly");
            return;
        }


        Room toRoom = caster.getRoom();

        if (toRoom != null) {
            caster.out("You summon to " + toRoom.getId());
            MoveManager.move(target, toRoom);
        } else {
            target.out("Room not found!");
        }
    }

    public boolean isPositiveEffect() {
        return true;
    }
}
