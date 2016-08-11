package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class CurePoison implements SpellEffect {

	@Override
	public void effect(Mob giver_, Mob reciever_, Spell spell, Item targetItem) {

		reciever_.removeAffect(Poison.POISON);

		reciever_.out("You cured "+reciever_.getName()+" of "+Poison.POISON);
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
