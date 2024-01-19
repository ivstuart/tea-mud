package com.ivstuart.tmud.poc;

import java.util.BitSet;

public class RoomFlags {

    public static final int REGEN_MANA = 0;

    public static final int UNDER_WATER = 1;

    public static final int DARK = 2;

    public static final int PEACEFUL = 3;

    public static final int WATER = 4;

    public static final int DEEP_WATER = 5;

    public static final int FLYING = 6;

    private BitSet flags;

    public RoomFlags() {

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
}
