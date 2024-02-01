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

package com.ivstuart.tmud.state.items;

import com.ivstuart.tmud.state.mobs.Mob;
import com.ivstuart.tmud.state.skills.Spell;
import com.ivstuart.tmud.world.World;

public class Potion extends Item {


    public void drink(Mob mob) {

        Spell spell = World.getSpell(this.getProperties());

        if (spell == null) {
            mob.out("The potion " + this.getProperties() + " has no effect");
            return;
        }

        spell.getSpellEffect().effect(mob, mob, spell, null);

    }
}
