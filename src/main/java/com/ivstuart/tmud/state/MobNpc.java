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

package com.ivstuart.tmud.state;

import com.ivstuart.tmud.behaviour.BaseBehaviour;
import com.ivstuart.tmud.behaviour.BehaviourFactory;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Tickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MobNpc implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final long serialVersionUID = 1L;
    private int xp;
    private int armour;
    private String attackType;
    private int copper;
    private DiceRoll damage;
    private DiceRoll maxHp;
    private transient List<String> behaviours;
    private String patrolPath;

    public MobNpc(Mob mob, MobNpc mobNpc) {
        this.armour = mobNpc.armour;

        if (mobNpc.behaviours != null) {
            for (String behaviour : mobNpc.behaviours) {
                BaseBehaviour baseBehaviour = BehaviourFactory.create(behaviour);
                if (baseBehaviour != null) {
                    baseBehaviour.setMob(mob);
                    if (mob.getTickers() == null) {
                        mob.setTickers(new ArrayList<Tickable>());
                    }
                    LOGGER.debug("Adding behaviour [" + baseBehaviour.getId() + "] for mob "+mob.getName());
                    mob.getTickers().add(baseBehaviour);
                }
            }
        }

    }


    public MobNpc() {

    }

    public void setDamage(String damage) {
        this.damage = new DiceRoll(damage);
    }

    public void setBehaviour(String behaviours) {
        if (this.behaviours == null) {
            this.behaviours = new ArrayList<>();
        }
        this.behaviours.add(behaviours);
    }


    public void setHp(String hp, Attribute health) {
        maxHp = new DiceRoll(hp);
        health.setToMaximum(maxHp.roll());
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getXp() {
        return xp;
    }

    public void setAttackType(String types) {
        this.attackType = types;
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public int getCopper() {
        return copper;
    }

    public void setCopper(int copper) {
        this.copper = copper;
    }

    public void setPatrolPath(String path) {
        this.patrolPath = path;
    }

    public int getArmour() {
        return armour;
    }

    public DiceRoll getDamage() {
        return damage;
    }
}
