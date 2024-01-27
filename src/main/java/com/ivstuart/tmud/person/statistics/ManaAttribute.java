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

package com.ivstuart.tmud.person.statistics;

import com.ivstuart.tmud.constants.ManaType;

import java.io.Serializable;

public class ManaAttribute implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final String name;

    private final int minimum;
    private final ManaType mana;
    private int maximum;
    private int current;
    private int castlevel;

    public ManaAttribute(ManaAttribute old_) {
        mana = old_.mana;
        name = old_.name;
        maximum = old_.maximum;
        current = old_.current;
        minimum = old_.minimum;
        castlevel = old_.castlevel;
    }

    public ManaAttribute(ManaType mana_) {
        mana = mana_;
        name = mana_.name();
        maximum = 10;
        current = 10;
        minimum = 0;
        castlevel = 18;
    }

    public void addCastLevel(int level_) {
        castlevel += level_;
    }

    public void addMaximum(int max_) {
        maximum += max_;
    }

    public void decrease(int value) {
        current -= value;
        if (current < minimum) {
            current = minimum;
        }
    }

    public String display() {
        return String.format("%1$s << %2$5s >>  $J %3$3s / %4$3s   %5$3s",
                mana.getColour().toString(), name, current, maximum,
                castlevel);
    }

    public int getCastlevel() {
        return castlevel;
    }

    public void setCastlevel(int castlevel) {
        this.castlevel = castlevel;
    }

    private String getColour(int current_, int max_) {
        if (current < maximum) {
            return "$G";
        } else if (current > maximum) {
            return "$K";
        }
        return "$J";
    }

    public String getDescription() {
        return String.format("%1%2$3s / $J%3$3s",
                this.getColour(current, maximum), current, maximum);
    }

    public ManaType getManaType() {
        return mana;
    }

    public int getMaximum() {
        return maximum;
    }

    public String getName() {
        return name;
    }

    public String getPrompt() {
        return String.valueOf(current);
    }

    public int getValue() {
        return current;
    }

    public void setValue(int value) {
        current = value;
        this.decrease(0);
        this.increase(0);
    }

    public void increase(int value) {
        current += value;
        if (current > maximum) {
            current = maximum;
        }
    }

    public void removeCastLevel(int level_) {
        castlevel -= level_;
    }

    public void removeMaximum(int max_) {
        maximum -= max_;
        this.decrease(max_);
    }

    public void restore() {
        current = maximum;
    }

    public void increaseToMaximum() {
        current = maximum;
    }

    public void increaseCurrentAndMaximum(int mana) {
        maximum += mana;
        current += mana;
    }

    public void setToMaximum(int mana) {
        maximum = mana;
        current = mana;
    }
}
