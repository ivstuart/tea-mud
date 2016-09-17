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

import com.ivstuart.tmud.state.*;
import com.ivstuart.tmud.state.util.EntityProvider;

public class AnimateDead implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

		if (targetItem == null) {
			caster_.out("No corpse selected to animate");
			return;
		}

		if (!targetItem.isCorpse()) {
			caster_.out("Not a corpse selected to animate");
			return;
		}

		Corpse corpse = (Corpse)targetItem;
		Room room = caster_.getRoom();

		// check corpse still there
		if (!room.getProps().remove(corpse)) {
			caster_.out("Corpse targetted has since moved away");
			return;
		}

		// remove corpse object (items get placed on the ground if any).
		room.addAll(corpse.getInventory());
		corpse.getInventory().clear();

		Mob clonedMob = EntityProvider.createMob("zombie-001","zombie");
		room.add(clonedMob);
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
