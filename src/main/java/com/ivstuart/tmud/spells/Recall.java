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

import com.ivstuart.tmud.state.items.Item;
import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.places.Room;
import com.ivstuart.tmud.state.skills.Spell;
import com.ivstuart.tmud.world.World;

/**
 * Created by Ivan on 09/08/2016.
 */
public class Recall implements SpellEffect {

    public void effect(Mob caster, Mob target, Spell spell, Item targetItem) {

        if (caster.getFight().isEngaged()) {
            caster.out("You word of recall fizzles uselessly can not be used in combat");
            return;
        }

        Room toRoom = World.getPortal(caster);

        if (toRoom != null) {
            caster.out("You word of recall to your portal");
            caster.getRoom().remove(caster);
            toRoom.add(caster);
        } else {
            caster.out("Portal room not found! Please report bug to admin on bug channel");
        }
    }

    public boolean isPositiveEffect() {
        return true;
    }
}
