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
import com.ivstuart.tmud.state.Spell;

public class CurePoison implements SpellEffect {

	@Override
	public void effect(Mob giver_, Mob reciever_, Spell spell, Item targetItem) {

		reciever_.removeAffect(Poison.POISON);

		reciever_.out("You cured "+reciever_.getName()+" of "+Poison.POISON);
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
