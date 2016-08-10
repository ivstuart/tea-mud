package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Room;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.state.util.EntityProvider;

public class AnimateDead implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell) {

		// TODO target items specially a corpse

		// check corpse still there

		// remove corpse object (items get placed on the ground if any).

		Mob clonedMob = EntityProvider.createMob("zombie-001","zombie");
		Room room = caster_.getRoom();
		room.add(clonedMob);
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
