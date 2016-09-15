/*
 * Copyright (c) 2016. Ivan Stuart
 *  All Rights Reserved
 */

package com.ivstuart.tmud.common;

public enum MobState {

    SLEEP("sleeping", 3, 3, 2),
    SLEEP_ON("sleeping", 5, 5, 5),
    REST("resting", 2, 2, 2),
    REST_ON("resting", 3, 3, 3),
    MEDITATE("mediating", 1, 1, 3),
    SIT("sitting", 2, 2, 1),
    SIT_ON("sitting", 3, 3, 3),
    STAND("standing", 1, 1, 1, true),
    FLYING("being wide awake", 1, 1, 1, true),
    WAKE("being wide awake", 1, 1, 1, true); // remove this one??

    private final String _desc;
    private short _hpMod;
    private short _mvMod;
    private short _manaMod;
    private boolean canMove;

    MobState(String desc_, int hp_, int mv_, int mana_) {
        _desc = desc_;
        _hpMod = (short) hp_;
        _mvMod = (short) mv_;
        _manaMod = (short) mana_;
        this.canMove = false;
    }

    MobState(String desc_, int hp_, int mv_, int mana_, boolean canMove) {
        this(desc_, hp_, mv_, mana_);
        this.canMove = true;
    }

    public static MobState getMobState(String state_) {
        try {
            return MobState.valueOf(state_);
        } catch (IllegalArgumentException e) {

        }
        return null;
    }

    public boolean canMove() {
        return canMove;
    }

    public boolean isSleeping() {
        return this == SLEEP;
    }

    public String getDesc() {
        return _desc;
    }

    public short getHpMod() {
        return _hpMod;
    }

    public short getManaMod() {
        return _manaMod;
    }

    public short getMoveMod() {
        return _mvMod;
    }

    public boolean isFlying() {
        return this == FLYING;
    }

    public boolean greaterThan(MobState minState) {
        return minState.ordinal() < this.ordinal();
    }

    public boolean lessThan(MobState minState) {
        return minState.ordinal() > this.ordinal();
    }

    public boolean isMeditate() {
        return this == MEDITATE;
    }
}
