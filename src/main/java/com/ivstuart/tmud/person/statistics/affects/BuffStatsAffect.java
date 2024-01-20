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

package com.ivstuart.tmud.person.statistics.affects;

import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class BuffStatsAffect extends Affect {

    private final Spell spell;

    public BuffStatsAffect(Mob mob_, String desc_, int duration_) {
        super(mob_, desc_, duration_);
        this.spell = null;
    }

    public BuffStatsAffect(Mob target_, Spell spell) {
        super(target_, spell.getId(), spell.getDuration().roll());
        this.spell = spell;
    }

    @Override
    public void applyEffect() {
        _mob.out("You feel the affects of " + _desc);

        String stat = spell.getStat();
        if (stat == null) {
            _mob.out("You feel the affects of zero stat buff " + _desc);
            return;
        }
        int amount = spell.getAmount();
        _mob.getPlayer().getAttributes().get(stat).increaseMaximum(amount);
        _mob.getPlayer().getAttributes().get(stat).increase(amount);
    }

    @Override
    public void removeEffect() {
        _mob.out("The affects of " + _desc + " wear off");
        String stat = spell.getStat();
        if (stat == null) {
            _mob.out("You feel the affects of zero stat buff wear off " + _desc);
            return;
        }
        int amount = spell.getAmount();
        _mob.getPlayer().getAttributes().get(stat).increaseMaximum(-amount);
        _mob.getPlayer().getAttributes().get(stat).decrease(amount);

    }

    public void setDuration(int duration) {
        this._duration = duration;
    }
}
