package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public interface SpellEffect {

	public void effect(Mob caster, Mob target, Spell spell, Item targetItem);

	// Maybe this should be in the spell config file.
	public boolean isPositiveEffect();

}
