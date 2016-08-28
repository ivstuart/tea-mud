package com.ivstuart.tmud.constants;

/**
 * Created by Ivan on 28/08/2016.
 */
public enum CarriedEnum {

    UNBURDENED("unburdened.", 1),
    LOADED("loaded", 2),
    TAXED("taxed", 3),
    HEAVY("heavily burden", 4),
    NOMOVE("can not move", 100);

    public String getDesc() {
        return desc;
    }

    public int getMovemod() {
        return movemod;
    }

    private final String desc;
    private final int movemod;

    CarriedEnum(String desc, int i) {
        this.desc = desc;
        this.movemod = i;
    }

    public static CarriedEnum get(int index) {
        return (CarriedEnum.values()[index]);
    }
}
