package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.state.util.EntityProvider;

public class CreateLight implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

		// TODO create an orb of light in casters hands that can be worn as an aura

		Item item = EntityProvider.createItem("orb-of-light-001");
		caster_.getInventory().add(item);

		caster_.out("You create an orb of light in your backpack");
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
