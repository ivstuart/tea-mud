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

package com.ivstuart.tmud.person.statistics;

import com.ivstuart.tmud.constants.ManaType;
import com.ivstuart.tmud.state.Spell;

import java.io.Serializable;

public class MobMana implements Serializable {

    private static final long serialVersionUID = 1L;

    private ManaAttribute _mana[] = null;

    public MobMana() {
        // initialize(false);
    }

    public MobMana(boolean isPlayer_) {
        initialize(isPlayer_);
    }

    public MobMana(MobMana oldMana_) {
        _mana = new ManaAttribute[oldMana_._mana.length];
        for (int index = 0; index < _mana.length; index++) {
            _mana[index] = new ManaAttribute(oldMana_._mana[index]);
        }
    }

    /**
     * @param magic
     */
    public void addMaximumAndRestore(int magic) {
        for (ManaAttribute mana : _mana) {
            mana.addMaximum(magic);
            mana.restore();
        }

    }

    public void cast(Spell spell_) {
        ManaType manaType = spell_.getManaType();

        int cost = spell_.getCost();

        if (manaType == ManaType.COMMON) {
            for (int index = 0; index < 4; index++) {
                _mana[index].decrease(cost);
            }
        } else {
            _mana[manaType.ordinal()].decrease(cost);
        }
    }

    public ManaAttribute get(ManaType manaType_) {
        if (_mana == null) {
            return null;
        }
        return _mana[manaType_.ordinal()];
    }

    public String getPrompt() {

        return _mana[0].getPrompt();
    }

    public boolean hasEnoughManaToCast(Spell spell_) {
        if (_mana == null) {
            return false;
        }

        ManaType manaType = spell_.getManaType();

        if (manaType == ManaType.COMMON) {
            for (ManaAttribute mana : _mana) {
                if (mana.getValue() < spell_.getCost()) {
                    return false;
                }
            }
        } else {
            if (_mana[manaType.ordinal()].getValue() < spell_.getCost()) {
                return false;
            }
        }
        return true;
    }

    public boolean hasLevelToCast(Spell spell_) {
        if (_mana == null) {
            return false;
        }

        ManaType manaType = spell_.getManaType();

        if (manaType == ManaType.COMMON) {

            for (ManaAttribute mana : _mana) {
                if (mana.getCastlevel() > spell_.getLevel()) {
                    return true;
                }
            }
        } else {
            if (_mana[manaType.ordinal()].getCastlevel() > spell_.getLevel()) {
                return true;
            }
        }
        return false;
    }

    public void increase(int value_) {
        for (ManaAttribute mana : _mana) {
            mana.increase(value_);
        }
    }

    private void init(ManaType manaType_) {
        _mana[manaType_.ordinal()] = new ManaAttribute(manaType_);
    }

    private void initialize(boolean isPlayer_) {
        if (isPlayer_) {
            _mana = new ManaAttribute[4];
            init(ManaType.FIRE);
            init(ManaType.EARTH);
            init(ManaType.WATER);
            init(ManaType.AIR);
        } else {
            _mana = new ManaAttribute[1];
            init(ManaType.COMMON);
        }
    }

    public void setCastLevel(int castLevel) {
        for (ManaAttribute manaAtt : _mana) {
            manaAtt.setCastlevel(castLevel);
        }
    }

    public void increasePercentage(int i) {
        for (ManaAttribute manaAtt : _mana) {
            manaAtt.increase(i);
        }
    }

    public void restore() {
        for (ManaAttribute manaAtt : _mana) {
            manaAtt.increaseToMaximum();
        }
    }

    public void increaseCurrentAndMaximum(int mana) {
        for (ManaAttribute manaAtt : _mana) {
            manaAtt.increaseCurrentAndMaximum(mana);
        }
    }
}
