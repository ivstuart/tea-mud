/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.state.util.EntityProvider;

public class CloneMob implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

		// Target mob is cloned into same location
		if (target_.isPlayer()) {
			caster_.out("This spell fizzels on player mobs");
			return;
		}

		Room room = target_.getRoom();

		Mob clonedMob = EntityProvider.createMob(target_.getId(),target_.getId()+"c");

		room.add(clonedMob);
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
