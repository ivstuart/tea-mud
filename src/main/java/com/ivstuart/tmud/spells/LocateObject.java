package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class LocateObject implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

		caster_.out("Object is located in room"+targetItem.getRoom().getId());
	}

	public boolean isPositiveEffect() {
		return true;
	}

}