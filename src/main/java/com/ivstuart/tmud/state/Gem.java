/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.Equipable;
import com.ivstuart.tmud.constants.ManaType;

public class Gem extends Item implements Equipable {

	private static final long serialVersionUID = -5149906568548224750L;

	protected int manaBonus;
	protected int castBonus;

	protected ManaType _manaType;

	public Gem() {
	}

	@Override
	public void equip(Mob mob) {
		mob.getMana().get(_manaType).addMaximum(manaBonus);
		mob.getMana().get(_manaType).addCastLevel(castBonus);
	}

	public void setLevel(String level_) {
		castBonus = Integer.parseInt(level_);
		manaBonus = 5 * castBonus;
	}

	public void setMana(String mana_) {
		_manaType = ManaType.valueOf(mana_);
	}

	@Override
	public void unequip(Mob mob) {
		mob.getMana().get(_manaType).removeMaximum(manaBonus);
		mob.getMana().get(_manaType).removeCastLevel(castBonus);
	}

}
