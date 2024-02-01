/*
 *  Copyright 2024. Ivan Stuart
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

package com.ivstuart.tmud.state.items;

import com.ivstuart.tmud.common.Equipable;
import com.ivstuart.tmud.constants.ManaType;
import com.ivstuart.tmud.state.mobs.Mob;

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
