/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;
import com.ivstuart.tmud.state.Weapon;

public class EnchantWeapon implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

		if (!(targetItem instanceof Weapon)) {
			caster_.out("That item " + targetItem.getName() + " is not a weapon and hence can not be enchanted");
			return;
		}
		targetItem.setMagic(true);
		caster_.out("You enchant that item");
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
