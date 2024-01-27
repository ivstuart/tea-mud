package com.ivstuart.tmud.person.config;

public enum FightEnum {

    JOINT_LOCK("You WILL attempt wrist-bends and arm hyper extensions."),
    CHOKE("You WILL attempt to choke your victim."),
    BITE("You WILL attempt to bite your victim."),
    ELBOW("You WILL use your elbows."),
    KNEE( "You WILL use your knees."),
    HEADBUTT("You WILL headbutt."),
    POWER("You will attack to try to inflict as much damage as possible"),
    AGGRESSIVE("You WILL fight aggressively"),
    TARGET("You WILL try to hit weakness in the opponents armour"),
    GROUND("You WILL get involved in ground fighting"),
    BATTLEGROUND("You WILL involve yourself in a battle ground");

    private final String on;

    private final String off;

    FightEnum(String on) {
        this.on = on;
        this.off = on.replaceFirst("WILL","will NOT");
    }

    public String getOn() {
        return on;
    }

    public String getOff() {
        return off;
    }


}
