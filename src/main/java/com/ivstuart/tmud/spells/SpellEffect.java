package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public interface SpellEffect {

	public void effect(Mob caster, Mob target, Spell spell);

	// Maybe this should be in the spell config file.
	public boolean isPositiveEffect();

}
