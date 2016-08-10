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
    SHOLDER(2,"worn on sholder"),
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

    public int getCapacity() {
        return _capacity;
    }

    public String getDesc() {
        return _description;
    }

}
