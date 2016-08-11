package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.*;
import com.ivstuart.tmud.state.util.EntityProvider;

public class AnimateDead implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

		// TODO target items specially a corpse
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
