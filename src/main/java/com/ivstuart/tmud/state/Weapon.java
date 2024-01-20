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

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.common.DiceRoll;

/**
 * @author Ivan Stuart
 */
public class Weapon extends Item {

    private static final long serialVersionUID = -7988147515765705604L;

    private String skill;
    private DiceRoll damage;

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill_) {
        skill = skill_.trim();
    }

    public DiceRoll getDamage() {
        return damage;
    }

    public void setDamage(String damage_) {
        this.damage = new DiceRoll(damage_);
    }
}
