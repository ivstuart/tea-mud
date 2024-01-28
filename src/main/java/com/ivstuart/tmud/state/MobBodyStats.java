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

import com.ivstuart.tmud.common.Gender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

import static com.ivstuart.tmud.common.Gender.valueOf;

public class MobBodyStats implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger();
    private Gender gender;
    private String race;
    private int raceId;
    private int height; // cms
    private boolean undead;

    public MobBodyStats(MobBodyStats mobBodyStats) {
        this.gender = mobBodyStats.gender;
        this.race = mobBodyStats.race;
        this.raceId = mobBodyStats.raceId;
        this.height = mobBodyStats.height;
        this.undead = mobBodyStats.undead;
    }

    public MobBodyStats() {
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isUndead() {
        return undead;
    }

    public void setUndead(boolean undead) {
        this.undead = undead;
    }

    public void setGender(String gender_) {
        try {
            gender = valueOf(gender_.toUpperCase());
        } catch (IllegalArgumentException iae) {
            LOGGER.warn("Gender not set for:" + gender_);
            gender = Gender.NEUTRAL;
        }
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }
}
