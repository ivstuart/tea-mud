package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Mob;

public interface SpellEffect {

	public void effect(Mob caster, Mob target, int amount);
}
