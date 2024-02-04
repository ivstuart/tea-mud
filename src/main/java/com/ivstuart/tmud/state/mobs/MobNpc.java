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

import com.ivstuart.tmud.behaviour.BaseBehaviour;
import com.ivstuart.tmud.behaviour.BehaviourFactory;
import com.ivstuart.tmud.common.DiceRoll;
import com.ivstuart.tmud.common.Tickable;
import com.ivstuart.tmud.constants.DamageType;
import com.ivstuart.tmud.state.places.RoomLocation;
import com.ivstuart.tmud.state.player.Attribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private Map<DamageType, Integer> saves;

    private int weight; // kg base mob
    private String ability; // base mob
    private int align; // base mob
    private boolean alignment;

    private RoomLocation createdIn;

    public MobNpc(Mob mob, MobNpc mobNpc) {
        this.armour = mobNpc.armour;
        this.weight = mobNpc.weight;
        this.ability = mobNpc.ability;
        this.align = mobNpc.align;
        this.alignment = mobNpc.alignment;

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

    @Override
    public String toString() {
        return "MobNpc{" +
                "xp=" + xp +
                ", armour=" + armour +
                ", attackType='" + attackType + '\'' +
                ", copper=" + copper +
                ", damage=" + damage +
                ", maxHp=" + maxHp +
                ", behaviours=" + behaviours +
                ", patrolPath='" + patrolPath + '\'' +
                ", saves=" + saves +
                ", weight=" + weight +
                ", ability='" + ability + '\'' +
                ", align=" + align +
                ", alignment=" + alignment +
                '}';
    }

    public Map<DamageType, Integer> getSaves() {
        return saves;
    }

    public void setSaves(Map<DamageType, Integer> saves) {
        this.saves = saves;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public int getAlign() {
        return align;
    }

    public void setAlign(int align) {
        this.align = align;
    }

    public boolean isAlignment() {
        return alignment;
    }

    public void setAlignment(boolean alignment) {
        this.alignment = alignment;
    }

    public RoomLocation getCreatedIn() {
        return createdIn;
    }

    public void setCreatedIn(RoomLocation createdIn) {
        this.createdIn = createdIn;
    }
}
