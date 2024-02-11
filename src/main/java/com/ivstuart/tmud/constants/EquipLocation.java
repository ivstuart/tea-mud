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

package com.ivstuart.tmud.constants;

import com.ivstuart.tmud.state.items.Armour;

/**
 * @author Ivan Stuart
 */
public enum EquipLocation {
    AURA(1, "part of your aura", Armour.ALL),
    FLOAT_NEAR(1, "floating nearby", Armour.ALL),
    HEAD(1, "worn on head", Armour.HEAD),
    EAR(2, "worn on the ear", Armour.HEAD),
    EYES(1, "worn on eyes", Armour.HEAD),
    FACE(1, "worn on face", Armour.HEAD),
    NECK(2, "worn on neck", Armour.HEAD),
    SHOULDER(2, "worn on shoulder", Armour.BODY),
    ABOUT_BODY(1, "worn about body", Armour.BODY),
    BODY(1, "worn on body", Armour.BODY),
    ARMS(1, "worn on arms", Armour.ARMS),
    WRIST(2, "worn on wrist", Armour.ARMS),
    HANDS(1, "worn on hands", Armour.ARMS),
    FINGER(3, "worn on finger", Armour.ARMS),
    PRIMARY(1, "primary hand", Armour.ALL),
    SECONDARY(1, "secondary hand", Armour.ALL),
    BOTH(1, "both hands", Armour.ARMS),
    WAIST(1, "worn around waist", Armour.WAIST),
    BELT(2, "thru belt", Armour.WAIST),
    LEGS(1, "worn on legs", Armour.LEGS),
    ANKLE(2, "worn on ankle", Armour.LEGS),
    FEET(1, "worn on feet", Armour.FEET);

    private final int capacity;
    private final String description;

    private final int slot;

    EquipLocation(int capacity, String description, int slot) {
        this.capacity = capacity;
        this.description = description;
        this.slot = slot;
    }

    public static int getCapacity(int ordinal) {
        return values()[ordinal].getCapacity();
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDesc() {
        return description;
    }

    public int getSlot() {
        return slot;
    }
}
