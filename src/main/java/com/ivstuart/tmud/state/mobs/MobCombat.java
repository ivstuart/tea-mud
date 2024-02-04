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

package com.ivstuart.tmud.state.mobs;

import com.ivstuart.tmud.fighting.Fight;

import java.io.Serializable;

public class MobCombat implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient Fight fight;
    private int attacks = 1;
    private int defensive;
    private int offensive;
    private int wimpy;

    public MobCombat(Mob mob) {
        fight = new Fight(mob);
    }

    public MobCombat(Mob mob, MobCombat mobCombat) {
        this.attacks = mobCombat.attacks;
        this.offensive = mobCombat.offensive;
        this.defensive = mobCombat.defensive;
        this.wimpy = mobCombat.wimpy;
        this.fight = new Fight(mob);
    }

    public Fight getFight() {
        return fight;
    }

    public void setFight(Fight fight) {
        this.fight = fight;
    }

    public int getAttacks() {
        return attacks;
    }

    public void setAttacks(int attacks) {
        this.attacks = attacks;
    }

    public int getDefensive() {
        return defensive;
    }

    public void setDefensive(int defensive) {
        this.defensive = defensive;
    }

    public int getOffensive() {
        return offensive;
    }

    public void setOffensive(int offensive) {
        this.offensive = offensive;
    }

    public int getWimpy() {
        return wimpy;
    }

    public void setWimpy(int wimpy) {
        this.wimpy = wimpy;
    }

    @Override
    public String toString() {
        return "MobCombat{" +
                "fight=" + fight +
                ", attacks=" + attacks +
                ", defensive=" + defensive +
                ", offensive=" + offensive +
                ", wimpy=" + wimpy +
                '}';
    }
}
