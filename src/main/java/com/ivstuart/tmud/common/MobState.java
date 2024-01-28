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

package com.ivstuart.tmud.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum MobState {

    SLEEP("sleeping", 3, 3, 2),
    SLEEP_ON("sleeping", 5, 5, 5),
    REST("resting", 2, 2, 2),
    REST_ON("resting", 3, 3, 3),
    MEDITATE("mediating", 1, 1, 3),
    SIT("sitting", 2, 2, 1),
    SIT_ON("sitting", 3, 3, 3),
    STAND("standing", 1, 1, 1, true),
    FLYING("being wide awake", 1, 1, 1, true),
    WAKE("being wide awake", 1, 1, 1, true); // remove this one??

    private static final Logger LOGGER = LogManager.getLogger();

    private final String _desc;
    private final short _hpMod;
    private final short _mvMod;
    private final short _manaMod;
    private boolean canMove;

    MobState(String desc_, int hp_, int mv_, int mana_) {
        this._desc = desc_;
        this._hpMod = (short) hp_;
        this._mvMod = (short) mv_;
        this._manaMod = (short) mana_;
        this.canMove = false;
    }

    MobState(String desc_, int hp_, int mv_, int mana_, boolean canMove) {
        this(desc_, hp_, mv_, mana_);
        this.canMove = canMove;
    }

    public static MobState getMobState(String state_) {
        try {
            return MobState.valueOf(state_);
        } catch (IllegalArgumentException e) {
            return STAND;
            // LOGGER.error("Problem with getting mob state", e);
        }
    }

    public boolean stuck() {
        return !canMove;
    }

    public boolean isSleeping() {
        return this == SLEEP || this == SLEEP_ON;
    }

    public String getDesc() {
        return _desc;
    }

    public short getHpMod() {
        return _hpMod;
    }

    public short getManaMod() {
        return _manaMod;
    }

    public short getMoveMod() {
        return _mvMod;
    }

    public boolean isFlying() {
        return this == FLYING;
    }

    public boolean greaterThan(MobState minState) {
        return minState.ordinal() < this.ordinal();
    }

    public boolean lessThan(MobState minState) {
        return minState.ordinal() > this.ordinal();
    }

    public boolean isMeditate() {
        return this == MEDITATE;
    }
}
