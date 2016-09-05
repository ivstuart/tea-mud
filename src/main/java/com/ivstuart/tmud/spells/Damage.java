package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.fighting.DamageManager;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Damage implements SpellEffect {

	@Override
	public void effect(Mob giver_, Mob reciever, Spell spell, Item targetItem) {


        DamageManager.deal(giver_, reciever, spell.getDamage().roll(), spell);
    }

	public boolean isPositiveEffect() {
		return false;
	}

}
