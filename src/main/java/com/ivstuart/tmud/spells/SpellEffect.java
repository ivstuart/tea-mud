package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Mob;

public interface SpellEffect {

	public void effect(Mob giver_, Mob reciever, int amount_);
}
