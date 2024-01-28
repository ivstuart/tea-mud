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

import java.util.Arrays;

/**
 * Created by Ivan on 27/08/2016.
 */
public class Race {

    String id;
    String name;
    String desc;
    boolean alignment;
    int[] stats = new int[5];
    int armour;
    int movement;
    int regenHp;
    int regenMv;
    int regenMn;
    int height;
    boolean undead = false;
    boolean fly = false;
    boolean infravison = false;
    boolean stoneskin = false;
    boolean waterbreath = false;
    private boolean detectHidden;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean getAlignment() {
        return alignment;
    }

    public void setAlignment(boolean alignment) {
        this.alignment = alignment;
    }

    public int[] getStats() {
        return stats;
    }

    public void setStats(String stats) {

        String[] attribute = stats.split(" ");

        for (int i = 0; i < attribute.length; i++) {
            this.stats[i] = Integer.parseInt(attribute[i]);
        }

    }

    public int getArmour() {
        return armour;
    }

    public void setArmour(int armour) {
        this.armour = armour;
    }

    public int getMovement() {
        return movement;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }

    public int getRegenHp() {
        return regenHp;
    }

    public void setRegenHp(int regenHp) {
        this.regenHp = regenHp;
    }

    public int getRegenMv() {
        return regenMv;
    }

    public void setRegenMv(int regenMv) {
        this.regenMv = regenMv;
    }

    public int getRegenMn() {
        return regenMn;
    }

    public void setRegenMn(int regenMn) {
        this.regenMn = regenMn;
    }

    public boolean isUndead() {
        return undead;
    }

    public void setUndead(boolean undead) {
        this.undead = undead;
    }

    public boolean isFly() {
        return fly;
    }

    public void setFly(boolean fly) {
        this.fly = fly;
    }

    public boolean isInfravison() {
        return infravison;
    }

    public void setInfravison(boolean infravison) {
        this.infravison = infravison;
    }

    @Override
    public String toString() {
        return "Race{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", alignment=" + alignment +
                ", stats=" + Arrays.toString(stats) +
                ", armour=" + armour +
                ", movement=" + movement +
                ", regenHp=" + regenHp +
                ", regenMv=" + regenMv +
                ", regenMn=" + regenMn +
                ", undead=" + undead +
                ", fly=" + fly +
                ", infravison=" + infravison +
                ", stoneskin=" + stoneskin +
                ", waterbreath=" + waterbreath +
                '}';
    }

    public boolean isStoneskin() {
        return stoneskin;
    }

    public void setStoneskin(boolean stoneskin) {
        this.stoneskin = stoneskin;
    }

    public boolean isWaterbreath() {
        return waterbreath;
    }

    public void setWaterbreath(boolean waterbreath) {
        this.waterbreath = waterbreath;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isDetectHidden() {
        return detectHidden;
    }

    public void setDetectHidden(boolean detectHidden) {
        this.detectHidden = detectHidden;
    }
}
