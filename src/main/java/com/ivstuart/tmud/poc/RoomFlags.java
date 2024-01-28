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

package com.ivstuart.tmud.poc;

import java.util.BitSet;

public class RoomFlags {
    public static final int NARROW = 0;
    public static final int DARK = 1;

    public static final int NO_MOB = 2;

    public static final int PEACEFUL = 3;

    public static final int WATER = 4;

    public static final int SWIM = 5;
    public static final int DEEP_WATER = 6;
    public static final int UNDER_WATER = 7;
    public static final int AIR = 8;

    public static final int REGEN = 9;
    public static final int TRAP = 10;
    public static final int NO_SOUND = 11;
    public static final int NO_MAGIC = 12;
    public static final int NO_TRACK = 13;
    public static final int CLIMB = 14;
    public static final int NO_DROP = 15;
    public static final int AUCTION = 16;
    public static final int HOUSE = 17;
    public static final int ADMIN = 18;
    public static final int PRIVATE = 19;

    public static final int NUMBER_OF_FLAGS = 20;

    public static String[] bitNames = {
            "0 - Narrow",
            "1 - Dark",
            "2 - No Mob",
            "3 - Peaceful",
            "4 - Water",
            "5 - Swim",
            "6 - Deep Water",
            "7 - Under Water",
            "8 - Air",
            "9 - Regen",
            "10 - Trap",
            "11 - No Sound",
            "12 - No Magic",
            "13 - No track",
            "14 - Climb",
            "15 - No drop",
            "16 - Auction",
            "17 - House",
            "18 - Admin",
            "19 - Private"
    };

    private BitSet flags;

    public RoomFlags() {

    }

    public static String getBitName(int i) {
        return bitNames[i];
    }

    public void setFlag(int bit) {
        if (flags == null) {
            flags = new BitSet(1);
        }
        flags.set(bit);
    }

    public boolean getFlag(int bit) {
        if (flags == null) {
            return false;
        }
        return flags.get(bit);
    }

    public void toggleFlag(int bit) {
        if (flags == null) {
            this.setFlag(bit);
            return;
        }
        flags.flip(bit);
    }

    @Override
    public String toString() {
        return "RoomFlags{" +
                "flags=" + flags +
                '}';
    }

    public BitSet getBitSet() {
        return flags;
    }

    public void removeFlag(int narrow) {
        if (flags != null) {
            flags.clear(narrow);
        }
    }
}
