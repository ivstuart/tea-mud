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

import java.io.Serializable;

public class ManaAttribute implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final String _name;

    private final int _minimum;
    private final ManaType _mana;
    private int _maximum;
    private int _current;
    private int _castlevel;

    public ManaAttribute(ManaAttribute old_) {
        _mana = old_._mana;
        _name = old_._name;
        _maximum = old_._maximum;
        _current = old_._current;
        _minimum = old_._minimum;
        _castlevel = old_._castlevel;
    }

    public ManaAttribute(ManaType mana_) {
        _mana = mana_;
        _name = mana_.name();
        _maximum = 10;
        _current = 10;
        _minimum = 0;
        _castlevel = 18;
    }

    public void addCastLevel(int level_) {
        _castlevel += level_;
    }

    public void addMaximum(int max_) {
        _maximum += max_;
    }

    public void decrease(int value) {
        _current -= value;
        if (_current < _minimum) {
            _current = _minimum;
        }
    }

    public String display() {
        return String.format("%1$s << %2$5s >>  $J %3$3s / %4$3s   %5$3s",
                _mana.getColour().toString(), _name, _current, _maximum,
                _castlevel);
    }

    public int getCastlevel() {
        return _castlevel;
    }

    public void setCastlevel(int castlevel) {
        this._castlevel = castlevel;
    }

    private String getColour(int current_, int max_) {
        if (_current < _maximum) {
            return "$G";
        } else if (_current > _maximum) {
            return "$K";
        }
        return "$J";
    }

    public String getDescription() {
        return String.format("%1%2$3s / $J%3$3s",
                this.getColour(_current, _maximum), _current, _maximum);
    }

    public ManaType getManaType() {
        return _mana;
    }

    public int getMaximum() {
        return _maximum;
    }

    public String getName() {
        return _name;
    }

    public String getPrompt() {
        return String.valueOf(_current);
    }

    public int getValue() {
        return _current;
    }

    public void setValue(int value) {
        _current = value;
        this.decrease(0);
        this.increase(0);
    }

    public void increase(int value) {
        _current += value;
        if (_current > _maximum) {
            _current = _maximum;
        }
    }

    public void removeCastLevel(int level_) {
        _castlevel -= level_;
    }

    public void removeMaximum(int max_) {
        _maximum -= max_;
        this.decrease(max_);
    }

    public void restore() {
        _current = _maximum;
    }

    public void increaseToMaximum() {
        _current = _maximum;
    }

    public void increaseCurrentAndMaximum(int mana) {
        _maximum += mana;
        _current += mana;
    }
}
