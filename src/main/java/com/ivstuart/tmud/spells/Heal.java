package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Heal implements SpellEffect {

	@Override
	public void effect(Mob caster_, Mob target_, Spell spell, Item targetItem) {

		int ammount = spell.getAmount();
		if (caster_.getPlayer().getGuilds().isHealers()) {
			ammount *= 2;
		}

		target_.getHp().increase(ammount);
	}

	public boolean isPositiveEffect() {
		return true;
	}

}
