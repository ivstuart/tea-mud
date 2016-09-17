/*
 *  Copyright 2016. Ivan Stuart
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ivstuart.tmud.spells;

import com.ivstuart.tmud.person.statistics.affects.Affect;
import com.ivstuart.tmud.person.statistics.affects.ArmourBuff;
import com.ivstuart.tmud.state.Item;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class Protection implements SpellEffect {


	@Override
	public void effect(Mob caster, Mob target, Spell spell, Item targetItem) {

		Affect armorAffect = new ArmourBuff(target, spell.getId(),spell.getDuration().roll());

		armorAffect.setAmount(spell.getAmount());

		target.addAffect(spell.getId(),armorAffect);

		target.out("Armor buff applied!");

	}

	public boolean isPositiveEffect() {
		return true;
	}

}
