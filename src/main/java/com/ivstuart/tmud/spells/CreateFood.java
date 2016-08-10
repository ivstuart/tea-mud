package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.state.util.EntityProvider;

public class CreateFood implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell) {

		Item item = EntityProvider.createItem("waffer-001");
		caster_.getInventory().add(item);

		caster_.out("You create an "+item.getBrief()+" your backpack");
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
