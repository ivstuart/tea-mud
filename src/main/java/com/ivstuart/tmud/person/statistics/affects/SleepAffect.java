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

import com.ivstuart.tmud.common.MobState;
import com.ivstuart.tmud.state.Mob;
import com.ivstuart.tmud.state.Spell;

public class SleepAffect extends Affect {

    private final Spell spell;

    public SleepAffect(Mob mob_, String desc_, int duration_) {
        super(mob_, desc_, duration_);
        this.spell = null;
    }

    public SleepAffect(Mob target_, Spell spell) {
        super(target_, spell.getId(), spell.getDuration().roll());
        this.spell = spell;
    }

    @Override
    public void applyEffect() {
        _mob.out("You feel the affects of " + _desc);

        if (_mob.getFight().isEngaged()) {

            if (!spell.getName().equals("improved sleep")) {
                _mob.out("There is no effect from sleep while fighting" + _desc);
            } else {
                _mob.getFight().stopFighting();
                _mob.setState(MobState.SLEEP);
            }
        } else {
            _mob.setState(MobState.SLEEP);
        }

    }

    @Override
    public void removeEffect() {
        _mob.out("The affects of " + _desc + " wear off");
        _mob.setState(MobState.WAKE);

    }

    public void setDuration(int duration) {
        this._duration = duration;
    }
}
