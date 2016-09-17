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

import com.ivstuart.tmud.constants.DoorState;

import static com.ivstuart.tmud.constants.DoorState.CLOSED;
import static com.ivstuart.tmud.constants.DoorState.OPEN;

public class Chest extends Bag {

    private static final long serialVersionUID = -8765750404257030003L;
    protected String keyId;
    protected boolean isBashable = true;
    protected boolean isPickable = true;
    protected boolean isUnspellable;
    protected int strength;
    protected int difficulty = 50;
    private DoorState state = CLOSED;

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public boolean isBashable() {
        return isBashable;
    }

    public void setBashable(boolean bashable) {
        isBashable = bashable;
    }

    public boolean isPickable() {
        return isPickable;
    }

    public void setPickable(boolean pickable) {
        isPickable = pickable;
    }

    public boolean isUnspellable() {
        return isUnspellable;
    }

    public void setUnspellable(boolean unspellable) {
        isUnspellable = unspellable;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public String look() {
        String look = this.getBrief() + "\n";

        if (state == OPEN) {
            look += getInventory().look();
        } else {
            look += state;
        }
        return look;
    }

    @Override
    public String toString() {
        return "Chest{" +
                "keyId='" + keyId + '\'' +
                ", isBashable=" + isBashable +
                ", isPickable=" + isPickable +
                ", isUnspellable=" + isUnspellable +
                ", strength=" + strength +
                ", difficulty=" + difficulty +
                ", state=" + state +
                '}';
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public DoorState getState() {
        return state;
    }

    public void setState(String state) {
        this.state = DoorState.valueOf(state);
    }

    public void setState(DoorState state) {
        this.state = state;
    }

}
