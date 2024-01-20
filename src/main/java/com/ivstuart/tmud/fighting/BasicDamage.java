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

package com.ivstuart.tmud.fighting;

import com.ivstuart.tmud.common.DiceRoll;

public class BasicDamage implements Damage {

    private String type;

    private String description = "punch";

    // 1d6+0 as default roll
    private DiceRoll roll;
    private int multiplier = 1;

    // private int damage = 20;

    // private int hitLocation = 0;

    @Override
    public String getDescription() {

        return description;
    }

    @Override
    public String getType() {

        return type;
    }

    @Override
    public int roll() {
        return multiplier * roll.roll();

    }

    @Override
    public void setRoll(int number, int sides, int adder) {
        roll = new DiceRoll((short) number, (short) sides, (short) adder);
    }

    public void setMultiplier(int i) {
        this.multiplier = i;
    }

    public DiceRoll getRoll() {
        return roll;
    }

    public void setRoll(DiceRoll dice) {
        roll = dice;
    }
}
