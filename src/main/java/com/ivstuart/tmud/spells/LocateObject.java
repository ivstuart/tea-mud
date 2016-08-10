package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.state.World;

public class LocateObject implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell) {

//		String itemId = "todo";
//		Item item = World.getItem(itemId);
//
//		item.getRoom();
		// Could walk every room in mud looking for item.
		caster_.out("TODO");
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
