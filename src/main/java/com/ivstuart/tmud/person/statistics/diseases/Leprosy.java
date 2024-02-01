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

package com.ivstuart.tmud.person.statistics.diseases;

import com.ivstuart.tmud.state.mobs.Mob;

/**
 * Created by Ivan on 06/09/2016.
 */
public class Leprosy extends Disease {

    public Leprosy() {
        super();
        setIndirectContact(true);
        setDuration(500);
        setInfectionRate(100);
        setCureRate(80);
        setInitialDuration(500);
    }

    public Leprosy(Mob mob_, String desc_, int duration_) {
        super(mob_, desc_, duration_);
        setIndirectContact(true);
        setDuration(500);
        setInfectionRate(100);
        setCureRate(50);
        setInitialDuration(500);
    }

    public void applyEffect() {
        super.applyEffect();

        if (_mob.isPlayer()) {
            _mob.getPlayer().getAttributes().getDEX().decrease(1);
        }
    }

    public void removeEffect() {
        super.removeEffect();

        if (_mob.isPlayer()) {
            _mob.getPlayer().getAttributes().getDEX().increase(1);
        }
    }

    public boolean tick() {
        super.tick();
        return false;
    }
}
