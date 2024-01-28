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

package com.ivstuart.tmud.person.statistics.affects;

import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.state.Mob;

public class DamageOverTime extends Affect {

    protected DiceRoll _damageRoll = null;

    public DamageOverTime(Mob mob_, String desc_, int duration_,
                          DiceRoll damage_) {
        super(mob_, desc_, duration_);
        _damageRoll = damage_;
    }

    @Override
    public boolean tick() {
        super.tick();

        int damage = _damageRoll.roll();

        _mob.getHp().decrease(damage);

        String msg = this._desc + " deals you " + damage + " damage";

        _mob.out(msg);
        return false;

    }

    @Override
    public String toString() {
        return super.toString() + " " + _damageRoll;
    }

}
