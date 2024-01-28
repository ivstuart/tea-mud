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

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.state.util.EntityProvider;

public class CreateLight implements SpellEffect {

    @Override
    public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

        Item item = EntityProvider.createItem("orb-of-light-001");
        caster_.getInventory().add(item);

        caster_.out("You create an orb of light in your backpack");
    }

    public boolean isPositiveEffect() {
        return true;
    }

}
