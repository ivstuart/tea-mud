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

package com.ivstuart.tmud.poc.mob;

import java.util.BitSet;

public class MobFlags {

    public static final int AWARE = 0;
    public static final int AGGRO = 1;
    public static final int MEMORY = 2;
    public static final int MAGIC_IMMUNE = 3;
    public static final int BASH_IMMUNE = 4;
    public static final int HIDDEN = 5;
    public static final int INVISIBLE = 6;
    public static final int SENSE = 7;
    public static final int GUARD = 8;
    public static final int AGGRO_GOOD = 9;
    public static final int AGGRO_EVIL = 10;
    public static final int AGGRO_NEUTRAL = 11;
    public static final int JANITOR = 12;
    public static final int STAY_ZONE = 13;
    public static final int WIMPY = 14;
    public static final int ASSIST = 15;
    public static final int MOUNT = 16;

    private BitSet flags;

    public MobFlags() {

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
        return "MobFlags{" +
                "flags=" + flags +
                '}';
    }

    public BitSet getBitSet() {
        return flags;
    }

    public void removeFlag(int bit) {
        if (flags != null) {
            flags.clear(bit);
        }
    }

}
