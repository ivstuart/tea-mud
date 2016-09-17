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

package com.ivstuart.tmud.constants;

/**
 * @author Ivan Stuart
 */
public enum EquipLocation {
    AURA(1, "part of your aura"),
    FLOAT_NEAR(1, "floating nearby"),
    HEAD(1, "worn on head"),
    EAR(2, "worn on the ear"),
    EYES(1, "worn on eyes"),
    FACE(1, "worn on face"),
    NECK(2, "worn on neck"),
    SHOULDER(2, "worn on shoulder"),
    ABOUT_BODY(1, "worn about body"),
    BODY(1, "worn on body"),
    ARMS(1, "worn on arms"),
    WRIST(2, "worn on wrist"),
    HANDS(1, "worn on hands"),
    FINGER(3, "worn on finger"),
    PRIMARY(1, "primary hand"),
    SECONDARY(1, "secondary hand"),
    BOTH(1,"both hands"),
    WAIST(1, "worn around waist"),
    BELT(2, "thru belt"),
    LEGS(1, "worn on legs"),
    ANKLE(2, "worn on ankle"),
    FEET(1, "worn on feet");

    private int _capacity;
    private String _description;

    EquipLocation(int capacity_, String description_) {
        _capacity = capacity_;
        _description = description_;
    }

    public static int getCapacity(int oridinal) {
        return values()[oridinal].getCapacity();
    }

    public int getCapacity() {
        return _capacity;
    }

    public String getDesc() {
        return _description;
    }

}
