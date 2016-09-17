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

import java.io.Serializable;

import static com.ivstuart.tmud.constants.UsefulContants.affDuration;

public class Affect implements Serializable {

    protected Mob _mob;
    protected String _desc;
    protected int _duration;
    protected int _amount;

    public Affect() {
    }

    public Affect(Mob mob_, String desc_, int duration_) {
        _mob = mob_;
        _duration = duration_;
        _desc = desc_;
        _amount = 15;
    }

    public void setDuration(int _duration) {
        this._duration = _duration;
    }

    public void applyEffect() {
        _mob.out("You feel the affects of " + _desc);

    }

    public String getDesc() {
        return _desc;
    }

    public boolean isExpired() {
        return (_duration < 1);
    }

    public void removeEffect() {
        _mob.out("The affects of " + _desc + " wear off");

    }

    public boolean tick() {
        _duration--;
        return false;
    }


    public String look() {
        int index = _duration / 10;
        if (index > affDuration.length - 1) {
            index = affDuration.length - 1;
        }

        return _desc + " " + affDuration[index] + " (" + _duration + ")";
    }

    public int getAmount() {
        return _amount;
    }

    public void setAmount(int amount) {
        this._amount = amount;
    }

    // Default no change in damage
    public int onHit(Mob attacker, Mob defender, int damage) {
        return damage;
    }

    public void expire() {
        _duration = 0;
    }

    public void setMob(Mob mob) {
        this._mob = mob;
    }

    public void setDecription(String decription) {
        this._desc = decription;
    }
}
